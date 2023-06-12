import data.Priority
import data.Task
import data.TasksRepository
import data.TasksRepositoryMemory
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.assertContains
import kotlin.test.assertContentEquals
// используйте максимум или все из JUNIT5
//import kotlin.test.*


class TasksRepositoryTests {

    private lateinit var tasksRepository: TasksRepository

    //@BeforeTest
    @BeforeEach
    fun initTasksRepository() {
        tasksRepository = TasksRepositoryMemory()
        // это assert мало что проверяет и лучше не иметь assert в @BeforeTest
       // assertTrue("Tasks were not added to list. Can't run test") {
            with ((tasksRepository as TasksRepositoryMemory).tasks) {
            // (tasksRepository as TasksRepositoryMemory).tasks.run {
                add(Task(2, "task2", Priority.MEDIUM))
                add(Task(1, "task1", Priority.LOW))
                add(Task(3, "task3", Priority.MEDIUM))
                add(Task(4, "task4", Priority.HIGH, true))
                add(Task(5, "task5", Priority.LOW, true))
            }
    }

    //@AfterTest
    @AfterEach
    fun clearRepository() {
        // @BeforeTest срабатывает перед КАЖДЫМ тестом и организует "свежий" репозиторий с пустой "базой"
        // нет смысла чистить использованную базу после каждого теста и стек ее задач тоже
       //(tasksRepository as TasksRepositoryMemory).tasks.clear()
    }

    @DisplayName("Check task adding to repo")
    @Test
    fun addTaskToListTest() {
        val nextTaskId = (tasksRepository as TasksRepositoryMemory).nextId()
        //val nextTaskId = (tasksRepository as TasksRepositoryMemory).tasks.size + 1
        val task = Task(nextTaskId, "task$nextTaskId", Priority.LOW)
        tasksRepository.addTask(task)
        //assertContains(tasksRepository.getTasks(task.completed), task)
        assertContains(tasksRepository.getTasks(), task)
    }

    @DisplayName("Check completed tasks filter")
    @RepeatedTest(7)
    fun completedTasksFilterTest() {
        val undoneTask = tasksRepository.getTasks().filter { !it.completed }.random()
        undoneTask.id?.let { tasksRepository.completeTask(it) }
        //val uncompletedTasks = tasksRepository.getTasks(true).filter { !it.completed }
        val uncompletedTasks = tasksRepository.getTasks(false)
        assertAll(
            { assertTrue(undoneTask.completed) },
            { assertEquals(2, uncompletedTasks.size, "Filter does not exclude uncompleted tasks") }
        )
    }

    @DisplayName("Check filter by name and priority")
    @Disabled // см CHECKME.md
    @Test
    fun nameAndPriorityFilterTest() {
        // undoneTasks и completedTasks будут одинаковые - там true параметр по умолчанию
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