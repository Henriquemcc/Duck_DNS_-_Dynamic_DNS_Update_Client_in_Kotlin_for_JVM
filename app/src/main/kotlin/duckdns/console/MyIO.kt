package duckdns.console

import duckdns.international.resourceBundle
import java.text.MessageFormat

/**
 * Reads an integer number from standard input.
 * @param instructionMessage Instruction message to print.
 * @param range Range which integer must be inside it.
 * @return Integer number read from standard input.
 */
fun readInteger(instructionMessage: String? = null, range: IntRange? = null): Int {
    var integerNumberRead: Int? = null

    while (integerNumberRead == null || range?.contains(integerNumberRead) == false) {
        val stringRead = readString(instructionMessage)
        try {
            integerNumberRead = stringRead.toInt()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        }
        if (range?.contains(integerNumberRead) == false)
            println(
                MessageFormat.format(
                    resourceBundle.getString("0.is.not.in"),
                    integerNumberRead,
                    range
                )
            )
    }

    return integerNumberRead

}

/**
 * Reads a String from standard input.
 * @param instructionMessage Instruction message to print.
 */
fun readString(instructionMessage: String? = null): String {
    instructionMessage?.let { print(it) }
    return readLine()?.trim() ?: ""
}

/**
 * Prints a header to standard input.
 * @param title Header title.
 */
fun printHeader(title: String) {
    println("#".repeat(80))
    println(title)
    println("#".repeat(80))
    println()
}