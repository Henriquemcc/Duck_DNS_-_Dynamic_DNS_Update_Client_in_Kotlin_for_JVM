package console

import java.util.*

/**
 * Get from user the confirmation of a procedure.
 * @param instructionMessage Instruction message to print.
 * @return Boolean whether the user has confirm the procedure.
 */
fun getConfirmation(instructionMessage: String): Boolean {
    var confirmation: Char? = null
    while (confirmation != 'Y' && confirmation != 'N' && confirmation != 'n') {
        try {
            confirmation = readString(instructionMessage).trim()[0]
            if (confirmation != 'Y' && confirmation != 'N')
                throw InputMismatchException()
        } catch (e: InputMismatchException) {
            e.printStackTrace()
        }

    }

    return confirmation == 'Y'
}