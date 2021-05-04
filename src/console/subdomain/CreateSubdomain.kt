package console.subdomain

import exception.ObjectAlreadyExistsException
import model.Subdomain
import java.util.*

/**
 * Menu that helps the console user to create a Duck DNS Subdomain Object.
 */
fun createSubdomain() {

    var command: Int? = null

    val duckDNSNewSubdomainData = object {
        var subdomainName: String? = null
        var token: String? = null
        var enableIPv4 = true
        var enableIPv6 = true
    }

    while (command != 0) {
        console.printHeader("Subdomain creation menu")
        val instructionMessage = StringBuilder()
        instructionMessage.appendLine("Options:")
        instructionMessage.appendLine("0 - Exit")
        instructionMessage.appendLine("1 - Save and exit")

        instructionMessage.append("2 - Subdomain Name = ")
        if (duckDNSNewSubdomainData.subdomainName == null) instructionMessage.appendLine(
            "${duckDNSNewSubdomainData.subdomainName}"
        )
        else instructionMessage.appendLine("\"${duckDNSNewSubdomainData.subdomainName}\"")

        instructionMessage.append("3 - Token = ")
        if (duckDNSNewSubdomainData.token == null) instructionMessage.appendLine("${duckDNSNewSubdomainData.token}")
        else instructionMessage.appendLine("\"${duckDNSNewSubdomainData.token}\"")

        instructionMessage.appendLine("4 - Enable IPv4 = ${duckDNSNewSubdomainData.enableIPv4}")
        instructionMessage.appendLine("5 - Enable IPv6 = ${duckDNSNewSubdomainData.enableIPv6}")
        instructionMessage.append("> ")
        command = console.readInteger(instructionMessage.toString(), 0..5)
        println()

        when (command) {
            0 -> println("Exiting...")
            1 -> try {
                if (duckDNSNewSubdomainData.subdomainName == null)
                    throw InputMismatchException("Duck DNS Subdomain address is ${duckDNSNewSubdomainData.subdomainName}")
                if (duckDNSNewSubdomainData.token == null)
                    throw InputMismatchException("Duck DNS Account token is ${duckDNSNewSubdomainData.token}")

                println("Saving and exiting...")
                controller.create(
                    Subdomain(
                        duckDNSNewSubdomainData.subdomainName ?: return,
                        duckDNSNewSubdomainData.enableIPv4,
                        duckDNSNewSubdomainData.enableIPv6,
                        duckDNSNewSubdomainData.token ?: return
                    )
                )



                command = 0
            } catch (e: InputMismatchException) {
                e.printStackTrace()
            } catch (e: ObjectAlreadyExistsException) {
                e.printStackTrace()
            }
            2 -> duckDNSNewSubdomainData.subdomainName =
                console.readString("Subdomain address: ").toLowerCase()
            3 -> duckDNSNewSubdomainData.token = console.readString("Token: ")
            4 -> duckDNSNewSubdomainData.enableIPv4 =
                !duckDNSNewSubdomainData.enableIPv4
            5 -> duckDNSNewSubdomainData.enableIPv6 =
                !duckDNSNewSubdomainData.enableIPv6
        }
    }
}