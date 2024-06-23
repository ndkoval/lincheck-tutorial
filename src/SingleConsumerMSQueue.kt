import java.util.concurrent.atomic.*

class SingleConsumerMSQueue<T> {
    private val head: AtomicReference<Node<T>>
    private val tail: AtomicReference<Node<T>>

    init {
        val dummy = Node<T>(null)
        head = AtomicReference(dummy)
        tail = AtomicReference(dummy)
    }

    fun add(item: T) {
        val newNode = Node(item)
        while (true) {
            val curTail = tail.get()
            if (curTail.next.compareAndSet(null, newNode)) {
                tail.compareAndSet(curTail, newNode)
                return
            } else {
                tail.compareAndSet(curTail, curTail.next.get())
            }
        }
    }

    fun poll(): T? {
        while (true) {
            val curHead = head.get()
            val curHeadNext = curHead.next.get() ?: return null
            if (head.compareAndSet(curHead, curHeadNext)) {
                return curHeadNext.element
            }
        }
    }

    fun validateState() {
        check(head.get().element == null) {
            "Reference to a retrieved element has been detected, `head.element` should be `null`"
        }
        check(tail.get().next.get() == null) {
            "`tail.next` should be `null`"
        }
    }
}

private class Node<T>(
    var element: T?
) {
    val next = AtomicReference<Node<T>?>(null)
}
