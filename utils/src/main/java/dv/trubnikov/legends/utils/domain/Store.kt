package dv.trubnikov.legends.utils.domain

import java.util.concurrent.locks.ReentrantReadWriteLock
import javax.annotation.concurrent.ThreadSafe
import kotlin.concurrent.read
import kotlin.concurrent.write

@ThreadSafe
abstract class Store<T> {

    private val lock = ReentrantReadWriteLock()
    private var isCachedInitialized = false
    private var cachedValue: T? = null

    var value: T?
        get() {
            lock.read {
                if (isCachedInitialized) return cachedValue
                cachedValue = readFromPersistence()
                isCachedInitialized = true
                return cachedValue
            }
        }
        set(value) {
            lock.read {
                if (value == cachedValue && isCachedInitialized) return
                lock.write {
                    writeToPersistence(value)
                    onValueChanged(cachedValue, value)
                    cachedValue = value
                    isCachedInitialized = true
                }
            }
        }

    protected abstract fun readFromPersistence(): T?

    protected abstract fun writeToPersistence(value: T?)

    protected open fun onValueChanged(oldValue: T?, newValue: T?): Unit = Unit
}