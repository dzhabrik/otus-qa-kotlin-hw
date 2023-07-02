import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Тесты проверки построения меню на экране
 * функция построения должна возвращать введенный номер пункта
 * при вводе не числа 1-7 должна возвращать 0
 */
internal class MainKtTest {

    @Test
    fun `renderMenu chosen 3 when System InputStream contains 3`() {
        // Этот статический метод System.setIn() из класса java.lang.System
        // используется для переназначения стандартного входного потока.
        // byteInputStream создает новый поток ввода байта для строки.
        System.setIn("3".byteInputStream())
        assertEquals(3, renderMenu())
    }

    @Test
    fun `renderMenu chosen 4 when System InputStream contains 4`() {
        System.setIn("4".byteInputStream())
        assertEquals(4, renderMenu())
    }
    @Test
    fun `renderMenu chosen 0 when System InputStream contains A`() {
        System.setIn("A".byteInputStream())
        assertEquals(0, renderMenu())
    }

}