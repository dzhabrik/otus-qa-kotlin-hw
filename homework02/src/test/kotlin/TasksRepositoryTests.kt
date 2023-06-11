import data.Priority
import data.Task
import data.TasksRepository
import data.TasksRepositoryMemory
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.assertAll
import kotlin.test.*

class TasksRepositoryTests {

    private lateinit var tasksRepository: TasksRepository

    @BeforeTest
    fun initTasksRepository() {
        tasksRepository = TasksRepositoryMemory()
        assertTrue("Tasks were not added to list. Can't run test") {
            (tasksRepository as TasksRepositoryMemory).tasks.run {
                add(Task(2, "task2", Priority.MEDIUM))
                add(Task(1, "task1", Priority.LOW))
                add(Task(3, "task3", Priority.MEDIUM))
                add(Task(4, "task4", Priority.HIGH, true))
                add(Task(5, "task5", Priority.LOW, true))
            }
        }
    }

    @AfterTest
    fun clearRepository() {
        (tasksRepository as TasksRepositoryMemory).tasks.clear()
    }

    @DisplayName("Check task adding to repo")
    @Test
    fun addTaskToListTest() {
        val nextTaskId = (tasksRepository as TasksRepositoryMemory).tasks.size + 1
        val task = Task(nextTaskId, "task${nextTaskId}", Priority.LOW)
        tasksRepository.addTask(task)
        assertContains(tasksRepository.getTasks(task.completed), task)
    }

    @DisplayName("Check completed tasks filter")
    @Test
    fun completedTasksFilterTest() {
        val undoneTask = tasksRepository.getTasks().random()
        undoneTask.id?.let { tasksRepository.completeTask(it) }
        val uncompletedTasks = tasksRepository.getTasks(true).filter { !it.completed }

        assertAll(
            { assertTrue(undoneTask.completed) },
            { assertEquals(0, uncompletedTasks.size, "Filter does not exclude uncompleted tasks") }
        )
    }

    @DisplayName("Check filter by name and priority")
    @Test
    fun nameAndPriorityFilterTest() {
        val undoneTasks = tasksRepository.getTasks()
        val completedTasks = tasksRepository.getTasks(true)

        assertAll(
            {
                assertContentEquals(
                    undoneTasks.sortedBy { it.name },
                    undoneTasks,
                    "Uncompleted tasks are not sorted by name"
                )
            },
            {
                assertContentEquals(
                    undoneTasks.sortedBy { it.priority },
                    undoneTasks,
                    "Uncompleted tasks are not sorted by priority"
                )
            },

            {
                assertContentEquals(
                    completedTasks.sortedBy { it.name },
                    completedTasks,
                    "Completed tasks are not sorted by name"
                )
            },
            {
                assertContentEquals(
                    completedTasks.sortedBy { it.priority },
                    completedTasks,
                    "Completed tasks are not sorted by priority"
                )
            }
        )
    }
}