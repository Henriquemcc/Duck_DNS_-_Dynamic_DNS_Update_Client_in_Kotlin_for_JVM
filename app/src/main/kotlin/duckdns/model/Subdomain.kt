package duckdns.model

import duckdns.exception.IPv6NotFoundException
import duckdns.network.getIPv6Address
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.Serializable
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection

/**
 * Stores subdomain data
 */
data class Subdomain(
    /**
     * Duck DNS Subdomain Name
     */
    val subdomainName: String,
    /**
     * Whether is to enable IPv4 for this subdomain
     */
    val enableIPv4: Boolean = true,
    /**
     * Whether is to enable IPv6 for this subdomain
     */
    val enableIPv6: Boolean = true,
    /**
     * Duck DNS Account Token
     */
    val token: String
) : Serializable {

    /**
     * Checking whether the constructor parameters are correct and throwing exception if they are not
     */
    init {
        if (subdomainName.isBlank()) throw InputMismatchException("subdomainName is blank")
        if (subdomainName.isEmpty()) throw InputMismatchException("subdomainName is empty")
        if (token.isBlank()) throw InputMismatchException("token is blank")
        if (token.isEmpty()) throw InputMismatchException("token is empty")
    }

    /**
     * Updates Duck DNS Subdomain IP addresses
     */
    fun updateIPAddress() {

        for (i in 0..5)
            try {
                cleanIPAddresses()
                break
            } catch (e: IOException) {
                e.printStackTrace()
            }

        if (enableIPv4)
            for (i in 0..5)
                try {
                    updateIPv4Address()
                    break
                } catch (e: IOException) {
                    e.printStackTrace()
                }

        if (enableIPv6)
            for (i in 0..5)
                try {
                    updateIPv6Address()
                    break
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: IPv6NotFoundException) {
                    e.printStackTrace()
                }

    }

    /**
     * Updates the Duck DNS Subdomain IPv6 address.
     * @throws IPv6NotFoundException If no IPv6 has been found on the machine.
     */
    private fun updateIPv6Address() {
        val iPv6Addresses = getIPv6Address()

        if (iPv6Addresses.isEmpty())
            throw IPv6NotFoundException()

        val iPv6Address = iPv6Addresses[0]
        val addressUrlIPv6 =
            "https://www.duckdns.org/update?domains=$subdomainName&token=$token&ipv6=$iPv6Address"
        val url = URL(addressUrlIPv6)
        val uc = url.openConnection() as HttpsURLConnection
        val br = BufferedReader(InputStreamReader(uc.inputStream))
        val msg = br.readLine()
        if (!msg.contains("OK"))
            throw IOException("Failed to update IPv6 address: $msg")
    }

    /**
     * Updates the Duck DNS Subdomain IPv4 address.
     */
    private fun updateIPv4Address() {
        val addressUrlIPv4 =
            "https://www.duckdns.org/update?domains=$subdomainName&token=$token&ip="
        val url = URL(addressUrlIPv4)
        val uc = url.openConnection() as HttpsURLConnection
        val br = BufferedReader(InputStreamReader(uc.inputStream))
        val msg = br.readLine()
        if (!msg.contains("OK"))
            throw IOException("Failed to update IPv4 address: $msg")
    }

    /**
     * Clears Duck DNS Subdomain IP addresses.
     */
    fun cleanIPAddresses() {
        val clearUrl =
            "https://www.duckdns.org/update?domains=$subdomainName&token=$token&clear=true"
        val url = URL(clearUrl)
        val uc = url.openConnection() as HttpsURLConnection
        val br = BufferedReader(InputStreamReader(uc.inputStream))
        val msg = br.readLine()
        if (!msg.contains("OK"))
            throw IOException("Failed to clear IP addresses: $msg")
    }
}