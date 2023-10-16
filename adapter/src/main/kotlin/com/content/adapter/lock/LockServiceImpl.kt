package com.content.adapter.lock

import com.content.util.lock.LockService
import com.content.util.share.Logger
import org.redisson.api.RedissonClient
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class LockServiceImpl<R>(
    private val redissonClient: RedissonClient,
    private val redisTemplate: RedisTemplate<String, String>
) : LockService<R> {
    override fun lockRetry(key: String, lockTime: Long, exception: Exception, action: () -> R): R {
        val lock = redissonClient.getLock(key)

        try {
            val isLocked = lock.tryLock(lockTime, 3_000, TimeUnit.MILLISECONDS)
            log.info("lock try = $key, ${Thread.currentThread()}")


            if (!isLocked) {
                log.info("lock try = $key")
                throw exception
            }

            return action()
        } finally {
            if (lock.isHeldByCurrentThread) {
                log.info("lock unlock = $key, ${Thread.currentThread()}")
                lock.unlock()
            }
        }
    }

    override fun lockUnitRetry(key: String, lockTime: Long, exception: Exception, action: () -> Unit) {
        val lock = redissonClient.getLock(key)

        try {
            val isLocked = lock.tryLock(lockTime, 3_000, TimeUnit.MILLISECONDS)
            log.info("lock try = $key, ${Thread.currentThread()}")

            if (!isLocked) {
                throw exception
            }

            action()

        } finally {
            if (lock.isHeldByCurrentThread) {
                log.info("lock unlock = $key, ${Thread.currentThread()}")
                lock.unlock()
            }
        }
    }

    override fun lockUnitAtomic(key: String, lockTime: Long, exception: Exception, action: () -> Unit) {
        val isSet = redisTemplate.opsForValue().setIfAbsent(key, key, lockTime, TimeUnit.MILLISECONDS) ?: false
        if (isSet) {
            log.info("lock try = $key, ${Thread.currentThread()}")
            action()
        } else {
            throw exception
        }
    }

    override fun lockAtomic(key: String, lockTime: Long, exception: Exception, action: () -> R): R {
        val isSet = redisTemplate.opsForValue().setIfAbsent(key, key, lockTime, TimeUnit.MILLISECONDS) ?: false
        if (isSet) {
            log.info("lock try = $key, ${Thread.currentThread()}")
            return action()
        }
        throw exception
    }

    companion object : Logger()
}