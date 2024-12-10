package utils

import java.io.BufferedReader

class InputReader {
    companion object {
        fun readInputAsText(fileName: String): String = fromResources(fileName).readText()

        fun readLines(fileName: String): List<String> = fromResources(fileName).readLines()

        fun readFirstLine(fileName: String): String = fromResources(fileName).readLine()

        fun readSequence(fileName: String): Sequence<String> = readLines(fileName).asSequence()

        fun readMatrix(fileName: String): Matrix<Char> = readLines(fileName).map { it.toList() }.toMatrix()

        fun readMatrixOfInt(fileName: String): Matrix<Int> = readLines(fileName).map { value -> value.map { it.digitToInt() } }.toMatrix()

        private fun fromResources(fileName: String): BufferedReader =
            InputReader::class.java.classLoader
                .getResourceAsStream(fileName)!!
                .bufferedReader()
    }
}
