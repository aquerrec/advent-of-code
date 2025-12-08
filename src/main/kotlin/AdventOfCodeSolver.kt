interface AdventOfCodeSolver<IN, OUT> {
    fun solvePart1(input: IN): OUT

    fun solvePart2(input: IN): OUT
}

interface AdventOfCodeSolverWithAdditionalOptions<IN, OUT, OPTION> {
    fun solvePart1(
        input: IN,
        additionalOption: OPTION,
    ): OUT

    fun solvePart2(
        input: IN,
        additionalOption: OPTION,
    ): OUT
}
