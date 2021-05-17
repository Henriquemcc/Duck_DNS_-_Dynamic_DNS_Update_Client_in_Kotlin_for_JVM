package duckdns.console.subdomain

import duckdns.console.printHeader
import duckdns.console.readInteger
import duckdns.console.readString
import duckdns.controller.getSubdomains
import duckdns.model.Subdomain

/**
 * Menu that helps console user to update a Duck DNS Subdomain Object.
 */
fun updateSubdomain() {

    var command: Int? = null
    val instructionMessage = StringBuilder()
    instructionMessage.appendLine("Options:")
    instructionMessage.appendLine("0 - Exit")
    instructionMessage.appendLine("1 - List Subdomains")
    instructionMessage.appendLine("2 - Select Subdomain by it's position and update it")
    instructionMessage.appendLine("3 - Select Subdomain by it's name and update it")
    instructionMessage.append("> ")

    while (command != 0) {
        printHeader("Subdomain update menu")
        command = readInteger(instructionMessage.toString(), 0..3)
        println()

        when (command) {
            0 -> println("Exiting...")
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
        printHeader("Subdomain update by position menu")
        val updateInstructionMessage = StringBuilder()
        updateInstructionMessage.appendLine("Options:")
        updateInstructionMessage.appendLine("0 - Cancel and exit")
        updateInstructionMessage.appendLine("1 - Save and exit")

        updateInstructionMessage.append("2 - Subdomain Name = ")
        updateInstructionMessage.append(oldSubdomain.subdomainName)
        if (oldSubdomain.subdomainName != newSubdomainName) {
            updateInstructionMessage.append(" -> ")
            updateInstructionMessage.append(newSubdomainName)
        }
        updateInstructionMessage.appendLine()

        updateInstructionMessage.append("3 - Token = ")
        updateInstructionMessage.append(oldSubdomain.token)
        if (oldSubdomain.token != newSubdomainToken) {
            updateInstructionMessage.append(" -> ")
            updateInstructionMessage.append(newSubdomainToken)
        }
        updateInstructionMessage.appendLine()

        updateInstructionMessage.append("4 - Enable IPv4 = ")
        updateInstructionMessage.append(oldSubdomain.enableIPv4)
        if (oldSubdomain.enableIPv4 != newSubdomainEnableIPv4) {
            updateInstructionMessage.append(" -> ")
            updateInstructionMessage.append(newSubdomainEnableIPv4)
        }
        updateInstructionMessage.appendLine()

        updateInstructionMessage.append("5 - Enable IPv6 = ")
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
            0 -> println("Exiting...")
            1 -> {
                println("Saving and exiting...")


                duckdns.controller.update(oldSubdomain, Subdomain(newSubdomainName, newSubdomainEnableIPv4, newSubdomainEnableIPv6, newSubdomainToken))

            }
            2 -> newSubdomainName = readString("Subdomain address:\n> ").toLowerCase()
            3 -> newSubdomainToken = readString("Token:\n> ")
            4 -> newSubdomainEnableIPv4 = !oldSubdomain.enableIPv4
            5 -> newSubdomainEnableIPv6 = !oldSubdomain.enableIPv6
        }
    }
}