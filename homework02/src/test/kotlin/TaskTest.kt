import data.Priority
import data.Task
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Проверка перегруженного метода .toString() у data class Task
 * для активной и завершенной задачи
 */
internal class TaskTest {

    @Test
    fun `Uncompleted task toString method should be correct`() {
        val task = Task(89, "SomeTask", Priority.LOW).toString()
        val taskS = Task(89, "SomeTask", Priority.LOW)
        assertEquals("89. [ ] SomeTask : LOW", task)
        assertEquals("89. [ ] SomeTask : LOW", taskS.toString())
    }

    @Test
    fun `Completed task toString method should be correct`() {
        val task = Task(12, "SomeTask", Priority.LOW, true).toString()
        assertEquals("12. [x] SomeTask : LOW", task)
    }
}