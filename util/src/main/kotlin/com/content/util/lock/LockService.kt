package com.content.util.lock

import org.springframework.stereotype.Component

@Component
interface LockService<R> {
    fun lockRetry(key: String, lockTime: Long, exception: Exception, action: () -> R): R

    fun lockUnitRetry(key: String, lockTime: Long, exception: Exception, action: () -> Unit)

    fun lockAtomic(key: String, lockTime: Long, exception: Exception, action: () -> R): R

    fun lockUnitAtomic(key: String, lockTime: Long, exception: Exception, action: () -> Unit)
}