/**
 * @param input assumes correct format and not blank
 */
class MowerManager(private val input: String) {

    private val mowers: List<Mower>

    init {
        val lines = input.lines()
        val maxCoordinates = lines[0].split(" ")
        val updatedLines = lines.toMutableList()
        updatedLines.removeAt(0)

        val startCoordinates = mutableListOf<String>()
        val instructions = mutableListOf<String>()
        updatedLines.forEachIndexed { index, line ->
            when (index % 2) {
                0 -> startCoordinates.add(line.trim())
                1 -> instructions.add(line.trim())
            }
        }

        mowers = createMowers(maxCoordinates, startCoordinates.size, startCoordinates, instructions)
    }

    private fun createMowers(
        maxCoordinates: List<String>,
        numOfMowers: Int,
        startCoordinates: List<String>,
        instructions: List<String>
    ): List<Mower> {
        val mowers = mutableListOf<Mower>()
        for (i in 0 until numOfMowers) {
            val currentMowerStartCoordinates = startCoordinates[i].split(" ")
            mowers.add(
                Mower(
                    x = currentMowerStartCoordinates[0].toInt(),
                    y = currentMowerStartCoordinates[1].toInt(),
                    boundaryX = maxCoordinates[0].toInt(),
                    boundaryY = maxCoordinates[1].toInt(),
                    heading = headingStrToDegrees(currentMowerStartCoordinates[2]),
                    instructions = (instructions[i].trim().toCharArray().toTypedArray())
                )
            )
        }
        return mowers
    }

    private fun headingStrToDegrees(heading: String): Int = when (heading) {
        "N" -> Mower.NORTH
        "E" -> Mower.EAST
        "S" -> Mower.SOUTH
        "W" -> Mower.WEST
        else -> throw IllegalArgumentException("unknown heading: $heading")
    }

    private fun degreesToHeadingStr(degree: Int): String = when (degree) {
        Mower.NORTH -> "N"
        Mower.EAST -> "E"
        Mower.SOUTH -> "S"
        Mower.WEST -> "W"
        else -> throw IllegalArgumentException("unknown degree heading: $degree")
    }

    fun doMowing(): String {
        var instructionTally = 0
        val maxNumInstructions = mowers.map { it.instructions.count() }.max() ?: 0
        val mowers = this.mowers.toMutableList()
        for (x in 0 until maxNumInstructions) {
            mowers.forEachIndexed { index, mower ->
                val currentInstructions = mower.instructions
                currentInstructions.getOrNull(instructionTally)?.let {
                    mower.move(it)
                    mowers[index] = mower

                    if (mowerHasCollisions(mowers)) {
                        return getOutputDisplay(mowers)
                    }
                }

            }
            instructionTally += 1
        }
        return getOutputDisplay(mowers)
    }

    private fun mowerHasCollisions(mowers: List<Mower>): Boolean {
        mowers.forEachIndexed { x, mower ->
            mowers.forEachIndexed { y, otherMower ->
                if (x != y) {
                    if (mower.x == otherMower.x && mower.y == otherMower.y) {
                        return true
                    }
                }
            }
        }
        return false
    }

    private fun getOutputDisplay(mowers: List<Mower>): String = mowers.joinToString("\n") {
        "${it.x} ${it.y} ${degreesToHeadingStr(it.heading)}"
    }

}