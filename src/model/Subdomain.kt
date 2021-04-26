package model

import exception.IPv6NotFoundException
import network.getIPv6Address
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.Serializable
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection

/**
 * Store subdomain data
 */
class Subdomain(
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
        private val token: String
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
     * Update Duck DNS Subdomain IP addresses
     */
    fun update() {
        if (enableIPv4) {
            for (i in 0..5)
                try {
                    updateIPv4()
                    break
                } catch (e: IOException) {
                    e.printStackTrace()
                }
        }

        if (enableIPv6) {
            for (i in 0..5)
                try {
                    updateIPv6()
                    break
                } catch (e: IOException) {
                    e.printStackTrace()
                } catch (e: IPv6NotFoundException) {
                    e.printStackTrace()
                }
        }
    }

    /**
     * Updates the Duck DNS Subdomain IPv6 address.
     * @throws IPv6NotFoundException If no IPv6 has been found on the machine.
     */
    private fun updateIPv6() {
        val iPv6Addresses = getIPv6Address()

        if (iPv6Addresses.isEmpty())
            throw IPv6NotFoundException()

        val iPv6Address = iPv6Addresses[0]
        val addressUrlIPv6 = "https://www.duckdns.org/update?domains=$subdomainName&token=$token&ipv6=$iPv6Address"
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
    private fun updateIPv4() {
        val addressUrlIPv4 = "https://www.duckdns.org/update?domains=$subdomainName&token=$token&ip="
        val url = URL(addressUrlIPv4)
        val uc = url.openConnection() as HttpsURLConnection
        val br = BufferedReader(InputStreamReader(uc.inputStream))
        val msg = br.readLine()
        if (!msg.contains("OK"))
            throw IOException("Failed to update IPv4 address: $msg")
    }

    override fun toString(): String {
        val string = StringBuilder()
        string.append(super.toString())
        string.append("(")
        string.append("subdomainName = \"${subdomainName.toLowerCase()}\"")
        string.append(", ")
        string.append("enableIPv4 = $enableIPv4")
        string.append(", ")
        string.append("enableIPv6 = $enableIPv6")
        string.append(")")
        return string.toString()
    }

    /**
     * Creates a new Subdomain object with it's attributes modified.
     * @param subdomainName Duck DNS Subdomain Name.
     * @param enableIPv4 Whether is to enable IPv4 for this subdomain.
     * @param enableIPv6 Whether is to enable IPv6 for this subdomain.
     * @param token Duck DNS Account Token.
     * @return Subdomain with it's attributes modified.
     */
    fun modify(subdomainName: String = this.subdomainName, enableIPv4: Boolean = this.enableIPv4, enableIPv6: Boolean = this.enableIPv6, token: String = this.token): Subdomain {
        return Subdomain(subdomainName, enableIPv4, enableIPv6, token)
    }
}