package utils

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT,
    UP_LEFT,
    UP_RIGHT,
    DOWN_LEFT,
    DOWN_RIGHT,
    ;

    companion object {
        fun udlrEntries(): List<Direction> = listOf(UP, DOWN, LEFT, RIGHT)

        fun fromUDLR(udlr: Char): Direction =
            when (udlr) {
                'U' -> UP
                'D' -> DOWN
                'L' -> LEFT
                'R' -> RIGHT
                else -> throw UnsupportedOperationException("Can't transform $udlr to Direction")
            }

        fun fromCarets(caret: Char): Direction =
            when (caret) {
                '^' -> UP
                'v' -> DOWN
                '<' -> LEFT
                '>' -> RIGHT
                else -> throw UnsupportedOperationException("Can't transform $caret to Direction")
            }
    }

    fun isDiagonal(): Boolean = this in setOf(UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT)

    fun turn(turn: Turn): Direction =
        when (this) {
            UP ->
                when (turn) {
                    Turn.RIGHT -> RIGHT
                    Turn.LEFT -> LEFT
                    Turn.AHEAD -> UP
                }

            DOWN ->
                when (turn) {
                    Turn.RIGHT -> LEFT
                    Turn.LEFT -> RIGHT
                    Turn.AHEAD -> DOWN
                }

            LEFT ->
                when (turn) {
                    Turn.RIGHT -> UP
                    Turn.LEFT -> DOWN
                    Turn.AHEAD -> LEFT
                }

            RIGHT ->
                when (turn) {
                    Turn.RIGHT -> DOWN
                    Turn.LEFT -> UP
                    Turn.AHEAD -> RIGHT
                }

            UP_LEFT ->
                when (turn) {
                    Turn.RIGHT -> UP_RIGHT
                    Turn.LEFT -> DOWN_LEFT
                    Turn.AHEAD -> UP_LEFT
                }

            UP_RIGHT ->
                when (turn) {
                    Turn.RIGHT -> DOWN_RIGHT
                    Turn.LEFT -> UP_LEFT
                    Turn.AHEAD -> UP_RIGHT
                }

            DOWN_LEFT ->
                when (turn) {
                    Turn.RIGHT -> UP_LEFT
                    Turn.LEFT -> DOWN_RIGHT
                    Turn.AHEAD -> DOWN_LEFT
                }

            DOWN_RIGHT ->
                when (turn) {
                    Turn.RIGHT -> DOWN_LEFT
                    Turn.LEFT -> UP_RIGHT
                    Turn.AHEAD -> DOWN_RIGHT
                }
        }

    fun invert(): Direction =
        when (this) {
            UP -> DOWN
            DOWN -> UP
            LEFT -> RIGHT
            RIGHT -> LEFT
            UP_LEFT -> DOWN_RIGHT
            UP_RIGHT -> DOWN_LEFT
            DOWN_LEFT -> UP_RIGHT
            DOWN_RIGHT -> UP_LEFT
        }
}

enum class Turn {
    RIGHT,
    LEFT,
    AHEAD,
}
