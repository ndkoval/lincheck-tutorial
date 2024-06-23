import java.util.concurrent.*
import java.util.concurrent.atomic.*

class BoundedQueueGPT<T>(private val capacity: Int) {
    private val queue = ConcurrentLinkedQueue<T>()
    private val size = AtomicInteger()

    fun add(item: T): Boolean {
        // Check if there is space
        // available in the queue.
        if (size.get() == capacity) return false
        // Add the element.
        queue.offer(item)
        // After the element was added
        // to the queue, increment the size.
        size.incrementAndGet()
        return true
    }

    fun poll(): T? {
        // Retrieve and remove
        // the head of the queue.
        val item = queue.poll() ?: return null
        // Decrement the size of the queue.
        size.decrementAndGet()
        return item
    }
}
