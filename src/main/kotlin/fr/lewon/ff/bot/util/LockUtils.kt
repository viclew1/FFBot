package fr.lewon.ff.bot.util

import java.util.concurrent.locks.ReentrantLock

object LockUtils {

    inline fun <T> ReentrantLock.executeSyncOperation(operation: () -> T): T {
        try {
            lockInterruptibly()
            return operation()
        } finally {
            unlock()
        }
    }

}