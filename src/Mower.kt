class Mower(
    var x: Int = 0,
    var y: Int = 0,
    var heading: Int = 0,
    val boundaryX: Int = 0,
    val boundaryY: Int = 0,
    val instructions: Array<Char> = emptyArray()
) {

    companion object {
        const val NORTH = 0
        const val EAST = 90
        const val SOUTH = 180
        const val WEST = 270
        const val ROTATE_LEFT = 'L'
        const val ROTATE_RIGHT = 'R'
        const val FORWARD = 'M'
    }

    fun move(instruction: Char) {
        when (instruction) {
            ROTATE_LEFT, ROTATE_RIGHT -> rotate(instruction)
            FORWARD -> forward()
        }
    }

    private fun rotate(direction: Char) {
        when (direction) {
            ROTATE_LEFT -> heading -= 90
            ROTATE_RIGHT -> heading += 90
        }
        when {
            heading == 360 -> heading = NORTH
            heading < 0 -> heading += 360
        }
    }

    private fun forward() {
        when (heading) {
            NORTH -> y += 1
            EAST -> x += 1
            SOUTH -> y -= 1
            WEST -> x -= 1
        }

        if (boundaryX > 0 && boundaryY > 0) {
            when {
                x < 0 -> x = 0
                x > boundaryX -> x = boundaryX
                y < 0 -> y = 0
                y > boundaryY -> y = boundaryY
            }
        }
    }

}