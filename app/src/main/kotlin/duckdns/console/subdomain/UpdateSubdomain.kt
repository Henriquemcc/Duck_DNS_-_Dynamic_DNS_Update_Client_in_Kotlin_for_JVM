package duckdns.console.subdomain

import duckdns.console.printHeader
import duckdns.console.readInteger
import duckdns.console.readString
import duckdns.controller.getSubdomains
import duckdns.international.resourceBundle
import duckdns.model.Subdomain

private val headerTitleMainUpdateMenu = resourceBundle.getString("subdomain.update.menu").capitalize()
private val headerTitleUpdateByPositionMenu = resourceBundle.getString("subdomain.update.by.position.menu").capitalize()
private val optionsSubtitle = "${resourceBundle.getString("options").capitalize()}:"
private val exitOption = "0 - ${resourceBundle.getString("exit").capitalize()}"
private val listSubdomainsOption = "1 - ${resourceBundle.getString("list.subdomains").capitalize()}"
private val updateSubdomainByPosition = "2 - ${resourceBundle.getString("update.subdomain.by.position").capitalize()}"
private val updateSubdomainByName = "3 - ${resourceBundle.getString("update.subdomain.by.name").capitalize()}"
private val exitingMessage = "${resourceBundle.getString("exiting").capitalize()}..."
private val savingAndExitingMessage = "${resourceBundle.getString("saving.and.exiting").capitalize()}..."
private val cancelAndExitOption = "0 - ${resourceBundle.getString("cancel.and.exit").capitalize()}"
private val saveAndExitOption = "1 - ${resourceBundle.getString("save.and.exit").capitalize()}"
private val subdomainNameOption = "2 - ${resourceBundle.getString("subdomain.name").capitalize()} = "
private val tokenOption = "3 - ${resourceBundle.getString("token").capitalize()} = "
private val enableIPv4Option = "4 - ${resourceBundle.getString("enable.IPv4").capitalize()} = "
private val enableIPv6Option = "5 - ${resourceBundle.getString("enable.IPv6").capitalize()} = "
private val subdomainNameMessage = "${resourceBundle.getString("subdomain.name").capitalize()}: "
private val tokenMessage = "${resourceBundle.getString("token").capitalize()}: "

/**
 * Menu that helps console user to update a Duck DNS Subdomain Object.
 */
fun updateSubdomain() {

    var command: Int? = null
    val instructionMessage = StringBuilder()
    instructionMessage.appendLine(optionsSubtitle)
    instructionMessage.appendLine(exitOption)
    instructionMessage.appendLine(listSubdomainsOption)
    instructionMessage.appendLine(updateSubdomainByPosition)
    instructionMessage.appendLine(updateSubdomainByName)
    instructionMessage.append("> ")

    while (command != 0) {
        printHeader(headerTitleMainUpdateMenu)
        command = readInteger(instructionMessage.toString(), 0..3)
        println()

        when (command) {
            0 -> println(exitingMessage)
            1 -> printAllSubdomains()
            2 -> updateByPosition()
            3 -> updateByName()
        }
    }
}

/**
 * Menu that helps console user to update a Duck DNS Subdomain Object by it's name.
 */
private fun updateByName() {
    val subdomainName = getSubDomainName()
    val oldSubdomain = getSubdomains().find {
        it.subdomainName.equals(
            subdomainName,
            ignoreCase = true
        )
    }
    update(oldSubdomain ?: return)
}

/**
 * Menu that helps console user to update a Duck DNS Subdomain Object by it's position.
 */
private fun updateByPosition() {
    val position = getSubDomainPosition()
    val oldSubdomain = getSubdomains()[position]
    update(oldSubdomain)
}

/**
 * Menu that helps console user to update a Duck DNS Subdomain Object changing it's attributes.
 * @param oldSubdomain Duck DNS Subdomain that will generate a updated one.
 */
private fun update(oldSubdomain: Subdomain) {
    var newSubdomainName = oldSubdomain.subdomainName
    var newSubdomainToken = oldSubdomain.token
    var newSubdomainEnableIPv4 = oldSubdomain.enableIPv4
    var newSubdomainEnableIPv6 = oldSubdomain.enableIPv6

    var command: Int? = null
    while (command != 0 && command != 1) {
        printHeader(headerTitleUpdateByPositionMenu)
        val updateInstructionMessage = StringBuilder()
        updateInstructionMessage.appendLine(optionsSubtitle)
        updateInstructionMessage.appendLine(cancelAndExitOption)
        updateInstructionMessage.appendLine(saveAndExitOption)

        updateInstructionMessage.append(subdomainNameOption)
        updateInstructionMessage.append(oldSubdomain.subdomainName)
        if (oldSubdomain.subdomainName != newSubdomainName) {
            updateInstructionMessage.append(" -> ")
            updateInstructionMessage.append(newSubdomainName)
        }
        updateInstructionMessage.appendLine()

        updateInstructionMessage.append(tokenOption)
        updateInstructionMessage.append(oldSubdomain.token)
        if (oldSubdomain.token != newSubdomainToken) {
            updateInstructionMessage.append(" -> ")
            updateInstructionMessage.append(newSubdomainToken)
        }
        updateInstructionMessage.appendLine()

        updateInstructionMessage.append(enableIPv4Option)
        updateInstructionMessage.append(oldSubdomain.enableIPv4)
        if (oldSubdomain.enableIPv4 != newSubdomainEnableIPv4) {
            updateInstructionMessage.append(" -> ")
            updateInstructionMessage.append(newSubdomainEnableIPv4)
        }
        updateInstructionMessage.appendLine()

        updateInstructionMessage.append(enableIPv6Option)
        updateInstructionMessage.append(oldSubdomain.enableIPv6)
        if (oldSubdomain.enableIPv6 != newSubdomainEnableIPv6) {
            updateInstructionMessage.append(" -> ")
            updateInstructionMessage.append(newSubdomainEnableIPv6)
        }
        updateInstructionMessage.appendLine()

        updateInstructionMessage.append("> ")
        command = readInteger(updateInstructionMessage.toString(), 0..5)
        println()

        when (command) {
            0 -> println(exitingMessage)
            1 -> {
                println(savingAndExitingMessage)


                duckdns.controller.update(oldSubdomain, Subdomain(newSubdomainName, newSubdomainEnableIPv4, newSubdomainEnableIPv6, newSubdomainToken))

            }
            2 -> newSubdomainName = readString(subdomainNameMessage).toLowerCase()
            3 -> newSubdomainToken = readString(tokenMessage)
            4 -> newSubdomainEnableIPv4 = !oldSubdomain.enableIPv4
            5 -> newSubdomainEnableIPv6 = !oldSubdomain.enableIPv6
        }
    }
}