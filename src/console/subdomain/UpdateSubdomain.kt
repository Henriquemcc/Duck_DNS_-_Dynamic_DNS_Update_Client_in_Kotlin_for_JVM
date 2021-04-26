package console.subdomain

import console.printHeader
import console.readInteger
import console.readString
import model.Subdomain
import serializableSubdomainController

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
    val oldSubdomain = serializableSubdomainController?.subdomains?.find { it.subdomainName.equals(subdomainName, ignoreCase = true) }
    update(oldSubdomain)
}

/**
 * Menu that helps console user to update a Duck DNS Subdomain Object by it's position.
 */
private fun updateByPosition() {
    val position = getSubDomainPosition()
    val oldSubdomain = serializableSubdomainController?.subdomains?.get(position)
    update(oldSubdomain)
}

/**
 * Menu that helps console user to update a Duck DNS Subdomain Object changing it's attributes.
 * @param oldSubdomain Duck DNS Subdomain that will generate a updated one.
 */
private fun update(oldSubdomain: Subdomain?) {
    val duckDNSSubdomainChangedData = object {
        var subdomainName: String? = null
        var token: String? = null
        var enableIPv4: Boolean? = null
        var enableIPv6: Boolean? = null
    }

    var command: Int? = null
    while (command != 0 && command != 1) {
        printHeader("Subdomain update by position menu")
        val updateInstructionMessage = StringBuilder()
        updateInstructionMessage.appendLine("Options:")
        updateInstructionMessage.appendLine("0 - Cancel and exit")
        updateInstructionMessage.appendLine("1 - Save and exit")

        updateInstructionMessage.append("2 - Subdomain Name = ")
        updateInstructionMessage.append(oldSubdomain?.subdomainName)
        if (duckDNSSubdomainChangedData.subdomainName != null) {
            updateInstructionMessage.append(" -> ")
            updateInstructionMessage.append(duckDNSSubdomainChangedData.subdomainName)
        }
        updateInstructionMessage.appendLine()

        updateInstructionMessage.append("3 - Token = ")
        updateInstructionMessage.append("************")
        if (duckDNSSubdomainChangedData.token != null) {
            updateInstructionMessage.append(" -> ")
            updateInstructionMessage.append(duckDNSSubdomainChangedData.token)
        }
        updateInstructionMessage.appendLine()

        updateInstructionMessage.append("4 - Enable IPv4 = ")
        updateInstructionMessage.append(oldSubdomain?.enableIPv4)
        if (duckDNSSubdomainChangedData.enableIPv4 != null) {
            updateInstructionMessage.append(" -> ")
            updateInstructionMessage.append(duckDNSSubdomainChangedData.enableIPv4)
        }
        updateInstructionMessage.appendLine()

        updateInstructionMessage.append("5 - Enable IPv6 = ")
        updateInstructionMessage.append(oldSubdomain?.enableIPv6)
        if (duckDNSSubdomainChangedData.enableIPv6 != null) {
            updateInstructionMessage.append(" -> ")
            updateInstructionMessage.append(duckDNSSubdomainChangedData.enableIPv6)
        }
        updateInstructionMessage.appendLine()

        updateInstructionMessage.append("> ")
        command = readInteger(updateInstructionMessage.toString(), 0..5)
        println()

        when (command) {
            0 -> println("Exiting...")
            1 -> {
                println("Saving and exiting...")
                serializableSubdomainController?.subdomains?.remove(oldSubdomain)

                if (oldSubdomain != null) {
                    var newSubdomain = oldSubdomain

                    if (duckDNSSubdomainChangedData.subdomainName != null)
                        newSubdomain = newSubdomain.modify(subdomainName = duckDNSSubdomainChangedData.subdomainName
                                ?: return)
                    if (duckDNSSubdomainChangedData.enableIPv4 != null)
                        newSubdomain = newSubdomain.modify(enableIPv4 = duckDNSSubdomainChangedData.enableIPv4
                                ?: return)
                    if (duckDNSSubdomainChangedData.enableIPv6 != null)
                        newSubdomain = newSubdomain.modify(enableIPv6 = duckDNSSubdomainChangedData.enableIPv6
                                ?: return)
                    if (duckDNSSubdomainChangedData.token != null)
                        newSubdomain = newSubdomain.modify(token = duckDNSSubdomainChangedData.token ?: return)

                    serializableSubdomainController?.subdomains?.add(newSubdomain)
                }
            }
            2 -> duckDNSSubdomainChangedData.subdomainName = readString("Subdomain address:\n> ").toLowerCase()
            3 -> duckDNSSubdomainChangedData.token = readString("Token:\n> ")
            4 -> duckDNSSubdomainChangedData.enableIPv4 = oldSubdomain?.enableIPv4 == false
            5 -> duckDNSSubdomainChangedData.enableIPv6 = oldSubdomain?.enableIPv6 == false
        }
    }
}