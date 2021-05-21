package duckdns.console.subdomain

import duckdns.console.getConfirmation
import duckdns.console.readInteger
import duckdns.console.readString
import duckdns.controller.getSubdomains
import duckdns.international.resourceBundle
import java.text.MessageFormat

private val subdomainNameInstructionMessage = "${resourceBundle.getString("subdomain.name").capitalize()}:\n> "
private val subdomainNotFoundErrorMessage = resourceBundle.getString("subdomain.0.not.founded").capitalize()
private val searchAgainMessage = "${resourceBundle.getString("would.you.like.to.search.again").capitalize()}?"
private val yesOption = resourceBundle.getString("y.yes").capitalize()
private val noOption = resourceBundle.getString("n.no").capitalize()

/**
 * Get subdomain name, checking if it's subdomain name is in the subdomains array.
 * @param instructionMessage Instruction message to print.
 */
fun getSubDomainName(instructionMessage: String? = subdomainNameInstructionMessage): String {
    var subdomainName: String
    var searchAgain = false

    do {
        subdomainName = readString(instructionMessage)
        val subdomainFounded = getSubdomains().any { it.subdomainName.equals(subdomainName, ignoreCase = true) }

        if (!subdomainFounded) {
            val subdomainNotFoundedInstructionMessage = StringBuilder()
            subdomainNotFoundedInstructionMessage.appendLine(
                MessageFormat.format(
                    subdomainNotFoundErrorMessage,
                    subdomainName
                )
            )
            subdomainNotFoundedInstructionMessage.appendLine(searchAgainMessage)
            subdomainNotFoundedInstructionMessage.appendLine(yesOption)
            subdomainNotFoundedInstructionMessage.appendLine(noOption)
            subdomainNotFoundedInstructionMessage.append("> ")
            searchAgain = getConfirmation(subdomainNotFoundedInstructionMessage.toString())
            subdomainName = ""
        }
    } while (searchAgain)

    return subdomainName
}

/**
 * Get position, checking if it's position is in range.
 * @param instructionMessage Instruction message to print.
 * @return Valid integer number.
 */
fun getSubDomainPosition(instructionMessage: String? = "Subdomain position [0, ${getSubdomains().size - 1}]:\n> "): Int {
    return readInteger(instructionMessage.toString(), IntRange(0, getSubdomains().size))
}