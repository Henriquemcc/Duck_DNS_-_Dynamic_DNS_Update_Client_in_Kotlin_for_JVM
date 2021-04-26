package console.subdomain

import console.getConfirmation
import console.readInteger
import console.readString
import serializableSubdomainController

/**
 * Get subdomain name, checking if it's subdomain name is in the subdomains array
 * @param instructionMessage Instruction message to print.
 */
fun getSubDomainName(instructionMessage: String? = "Subdomain name:\n> "): String {
    var subdomainName: String
    var searchAgain = false

    do {
        subdomainName = readString(instructionMessage)
        val subdomainFounded = serializableSubdomainController?.subdomains?.any { it.subdomainName.equals(subdomainName, ignoreCase = true) } == true

        if (!subdomainFounded) {
            val subdomainNotFoundedInstructionMessage = StringBuilder()
            subdomainNotFoundedInstructionMessage.appendLine("Subdomain $subdomainName not founded")
            subdomainNotFoundedInstructionMessage.appendLine("Would you like to search again?")
            subdomainNotFoundedInstructionMessage.appendLine("Y - Yes")
            subdomainNotFoundedInstructionMessage.appendLine("N - No")
            subdomainNotFoundedInstructionMessage.append("> ")
            searchAgain = getConfirmation(subdomainNotFoundedInstructionMessage.toString())
        }
    } while (searchAgain)

    return subdomainName
}

/**
 * Get position, checking if it's position is in range.
 * @param instructionMessage Instruction message to print.
 * @return Valid integer number.
 */
fun getSubDomainPosition(instructionMessage: String? = "Subdomain position [0, ${serializableSubdomainController?.subdomains?.size?.minus(1) ?: -1}]:\n> "): Int {
    return readInteger(instructionMessage.toString(), serializableSubdomainController?.subdomains?.size?.let { IntRange(0, it) })
}