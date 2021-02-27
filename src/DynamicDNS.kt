import java.io.*
import java.net.Inet6Address
import java.net.NetworkInterface
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection

internal class DynamicDNS {
    private val filename = "DuckDNS.cfg"
    private var domain: String? = null
    private var token: String? = null

    /**
     * This method sets the value for the string subdomain.
     * @param domain New value for domain String.
     */
    fun setDomain(domain: String) {
        this.domain = domain.toLowerCase()
    }

    /**
     * This method sets the value for the String token.
     * @param token New value for token String.
     */
    fun setToken(token: String?) {
        this.token = token
    }

    /**
     * This method updates Duck DNS Domain booth IPv4 and IPv6 addresses.
     */
    fun update(enableIPv6: Boolean, enableIPv4: Boolean) {

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
                }
        }
    }

    /**
     * This method updates the Duck DNS Domain IPv6 address.
     */
    private fun updateIPv6() {
        val addressUrlIPv6 = "https://www.duckdns.org/update?domains=$domain&token=$token&ipv6=$enderecoIPv6"
        val url = URL(addressUrlIPv6)
        val uc = url.openConnection() as HttpsURLConnection
        val br = BufferedReader(InputStreamReader(uc.inputStream))
        val msg = br.readLine()
        if (!msg.contains("OK"))
            throw IOException("Falha ao atualizar o IPv6:\nEndereço: $addressUrlIPv6\nMensagem de erro: $msg")
    }

    /**
     * This method updates the Duck DNS Domain IPv4 address.
     */
    private fun updateIPv4() {
        for (i in 0..5) {
            val addressUrlIPv4 = "https://www.duckdns.org/update?domains=$domain&token=$token&ip="
            val url = URL(addressUrlIPv4)
            val uc = url.openConnection() as HttpsURLConnection
            val br = BufferedReader(InputStreamReader(uc.inputStream))
            val msg = br.readLine()
            if (!msg.contains("OK"))
                throw IOException("Falha ao atualizar o IPv4:\nMensagem de erro: $msg")
        }
    }

    /**
     * This method retrieves and returns the IPv6 Address of the machine.
     * @return String containing the IPv6 address of the machine.
     */
    private val enderecoIPv6: String?
        get() {
            var enderecoIPv6: String? = null
            val networkInterfaces = NetworkInterface.getNetworkInterfaces()
            while (networkInterfaces.hasMoreElements() && enderecoIPv6 == null) {
                val inetAddresses = networkInterfaces.nextElement().inetAddresses
                while (inetAddresses.hasMoreElements() && enderecoIPv6 == null) {
                    val inetAddress = inetAddresses.nextElement()
                    if (inetAddress is Inet6Address) {
                        if (!inetAddress.isMulticastAddress && !inetAddress.isLinkLocalAddress && !inetAddress.isSiteLocalAddress && !inetAddress.isLoopbackAddress) {
                            enderecoIPv6 = inetAddress.hostAddress
                            try {
                                if (enderecoIPv6 != null && enderecoIPv6.contains("%")) enderecoIPv6 =
                                        enderecoIPv6.substring(0, enderecoIPv6.indexOf("%"))
                            } catch (e: IndexOutOfBoundsException) {
                                e.printStackTrace()
                            }
                        }
                    }
                }
            }

            return enderecoIPv6
        }

    /**
     * This method loads the configuration file.
     * @return Boolean value whether it was successful to load configuration file.
     */
    fun load(): Boolean {
        var success = false
        val configurationFile = File(filename)
        if (configurationFile.exists() && configurationFile.canRead()) {
            try {
                val dataFile = Scanner(configurationFile)
                domain = dataFile.nextLine()
                token = dataFile.nextLine()
                dataFile.close()
                success = true
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
        return success
    }

    /**
     * This method saves the configuration to the configuration file.
     */
    fun save() {
        val configurationFile = File(filename)
        if (!configurationFile.exists()) try {
            configurationFile.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        if (configurationFile.canWrite()) {
            try {
                val dataFile = Formatter(configurationFile, "UTF-8")
                dataFile.format("%s\n", domain)
                dataFile.format("%s\n", token)
                dataFile.close()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
            }
        }
    }
}