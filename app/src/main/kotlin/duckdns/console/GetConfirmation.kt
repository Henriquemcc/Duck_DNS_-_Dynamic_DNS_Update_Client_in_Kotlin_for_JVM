package duckdns.console

import duckdns.international.resourceBundle
import java.util.*

private val yesLetter = resourceBundle.getString("y").toLowerCase()[0]
private val noLetter = resourceBundle.getString("n").toLowerCase()[0]

/**
 * Get from user the confirmation of a procedure.
 * @param instructionMessage Instruction message to print.
 * @return Boolean whether the user has confirm the procedure.
 */
fun getConfirmation(instructionMessage: String): Boolean {
    var confirmation: Char? = null
    while (confirmation != yesLetter.toUpperCase() && confirmation != yesLetter.toLowerCase() && confirmation != noLetter.toUpperCase() && confirmation != noLetter.toLowerCase()) {
        try {
            confirmation = readString(instructionMessage).trim()[0]
            if (confirmation != yesLetter.toUpperCase() && confirmation != yesLetter.toLowerCase() && confirmation != noLetter.toUpperCase() && confirmation != noLetter.toLowerCase())
                throw InputMismatchException()
        } catch (e: InputMismatchException) {
            e.printStackTrace()
        }

    }

    return confirmation == yesLetter.toLowerCase() || confirmation == yesLetter.toUpperCase()
}