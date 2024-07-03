package com.content.util.lock

import org.springframework.stereotype.Component

@Component
interface DistributeLock<R> {
    fun withLockRetry(
        distributeLockType: DistributeLockType,
        key: String,
        lockTime: Long,
        exception: Exception,
        action: () -> R,
    ): R

    fun withLockUnitRetry(
        distributeLockType: DistributeLockType,
        key: String,
        lockTime: Long,
        exception: Exception,
        action: () -> Unit,
    )

    fun withLockAtomic(
        distributeLockType: DistributeLockType,
        key: String,
        lockTime: Long,
        exception: Exception,
        action: () -> R,
    ): R

    fun withLockUnitAtomic(
        distributeLockType: DistributeLockType,
        key: String,
        lockTime: Long,
        exception: Exception,
        action: () -> Unit,
    )
}
