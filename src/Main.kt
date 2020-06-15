import kotlin.test.assertEquals

fun main() {
    testPart1LawnMowing()
}

fun testPart1LawnMowing() {
    val input = "5 5\n" +
            "1 2 N\n" +
            "LMLMLMLMM\n" +
            "3 3 E\n" +
            "MMRMMRMRRM"
    val expectedOutput = "1 3 N\n" +
            "5 1 E"

    val manager = MowerManager(input)
    val actualOutput = manager.doMowing()
    assertEquals(expectedOutput, actualOutput)
    println("Actual Output: $actualOutput")
}

