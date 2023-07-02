import data.Priority
import data.Task
import data.TasksRepository
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import menu.*
import kotlin.test.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * Подключен MockK репозитория
 */

@ExtendWith(MockKExtension::class)  // встаю на эхо Мосл
class ActionsKtTest {
    // чтобы функции, Unit возвращающие данные, были упрощены https://mockk.io/#spy
    @MockK(relaxed = true)  // Эхо
    lateinit var repository: TasksRepository
    // Mokk поддельный репозиторий вешается на abstract class TasksRepository {

    @Test
    fun addTaskFromMenuTest() {
        // Этот статический метод System.setIn() из класса java.lang.System
        // используется для переназначения стандартного входного потока.
        // byteInputStream создает новый поток ввода байта для строки.
        System.setIn("SomeName\n2".byteInputStream())
        addTaskFromMenu(repository)
        // аналог assert для Mock
        verify { repository.addTask(Task(name = "SomeName", priority = Priority.MEDIUM)) }
    }

    @Test
    fun `should execute repository get tasks without parameters`() {
        listTasksFromMenu(repository)
        verify(exactly = 1) { repository.getTasks() }
    }

    @Test
    fun `should execute repository get tasks with false parameter`() {
        listNonCompletedTasksFromMenu(repository)
        verify { repository.getTasks(false) }
    }

    @Test
    fun `repository should delete task id that will return from system input`() {
        System.setIn("3".byteInputStream())
        deleteTasksFromMenu(repository)
        verify { repository.deleteTask(3) }
    }

    @Test
    fun `repository should complete task id that will return from system input`() {
        System.setIn("3".byteInputStream())
        complete(repository)
        verify { repository.completeTask(3) }
    }

    @Test
    fun `repository should uncomplete task id that will return from system input`() {
        System.setIn("2".byteInputStream())
        uncomplete(repository)
        verify { repository.uncompleteTask(2) }
    }

}