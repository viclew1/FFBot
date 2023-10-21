package fr.lewon.ff.bot.util

import fr.lewon.ff.bot.util.LockUtils.executeSyncOperation
import java.util.concurrent.locks.ReentrantLock

abstract class Listenable<T> {

    private val listeners = HashSet<T>()
    private val lock = ReentrantLock()

    protected fun getListeners(): List<T> {
        return listeners.toList()
    }

    fun addListener(listener: T) {
        lock.executeSyncOperation {
            listeners.add(listener)
        }
    }

    fun removeListeners() {
        lock.executeSyncOperation {
            listeners.clear()
        }
    }

    fun removeListener(listener: T) {
        lock.executeSyncOperation {
            listeners.remove(listener)
        }
    }

}