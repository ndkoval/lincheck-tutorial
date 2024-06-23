import org.jetbrains.kotlinx.lincheck.annotations.*
import org.jetbrains.kotlinx.lincheck.check
import org.jetbrains.kotlinx.lincheck.strategy.managed.modelchecking.ModelCheckingOptions
import org.junit.*

class SingleConsumerMSQueueLincheckTest {
    private val queue = SingleConsumerMSQueue<Int>()

    @Operation
    fun poll() = queue.poll()

    @Operation
    fun add(item: Int) = queue.add(item)

    @Validate
    fun validateState() = queue.validateState()

    @Test
    fun modelCheckingTest() = ModelCheckingOptions().check(this::class)
}
