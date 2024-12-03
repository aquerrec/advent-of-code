package utils

import java.io.BufferedReader

class InputReader {
    companion object {
        fun readInputAsText(fileName: String): String = fromResources(fileName).readText()

        fun readLines(fileName: String): List<String> = fromResources(fileName).readLines()

        fun readSequence(fileName: String): Sequence<String> = readLines(fileName).asSequence()

        private fun fromResources(fileName: String): BufferedReader =
            InputReader::class.java.classLoader
                .getResourceAsStream(fileName)!!
                .bufferedReader()
    }
}
