import java.io.BufferedReader
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.io.UnsupportedEncodingException
import java.net.Inet6Address
import java.net.NetworkInterface
import java.net.SocketException
import java.net.URL
import java.util.*
import javax.net.ssl.HttpsURLConnection

internal class DynamicDNS
{
    private val filename = "DuckDNS.cfg"
    private var domain: String? = null
    private var token: String? = null

    /**
     * This method sets the value for the string subdomain.
     * @param domain New value for domain String.
     */
    fun setDomain(domain: String)
    {
        this.domain = domain.toLowerCase()
    }

    /**
     * This method sets the value for the String token.
     * @param token New value for token String.
     */
    fun setToken(token: String?)
    {
        this.token = token
    }

    /**
     * This method updates Duck DNS Domain booth IPv4 and IPv6 addresses.
     * @return Byte that indicates whether it was successful to update IPv6 & IPv4 (10), IPv6 only (6), IPv4 only (4) or none (0).
     */
    fun update(enableIPv6: Boolean, enableIPv4: Boolean): Byte
    {
        var success: Byte = 0

        if (enableIPv4)
        {
            if (updateIPv4()) success = (success + 4).toByte()
        }

        if (enableIPv6)
        {
            if (updateIPv6()) success = (success + 6).toByte()
        }

        return success
    }

    /**
     * This method updates the Duck DNS Domain IPv6 address.
     * @return Boolean value whether it was successful to update IPv6.
     */
    private fun updateIPv6(): Boolean
    {
        var success = false
        //Updating IPv6 address
        try
        {
            val addressUrlIPv6 = "https://www.duckdns.org/update?domains=$domain&token=$token&ipv6=$enderecoIPv6"
            val url = URL(addressUrlIPv6)
            val uc = url.openConnection() as HttpsURLConnection
            val br = BufferedReader(InputStreamReader(uc.inputStream))
            val sb = StringBuilder()
            sb.append(br.readLine())
            if (sb.toString() == "OK") success = true
        }
        catch (e: IOException)
        {
            e.printStackTrace()
        }
        return success
    }

    /**
     * This method updates the Duck DNS Domain IPv4 address.
     * @return Boolean value whether it was successful to update IPv4.
     */
    private fun updateIPv4(): Boolean
    {
        var success = false
        //Updating IPv4 address
        try
        {
            val addressUrlIPv4 = "https://www.duckdns.org/update?domains=$domain&token=$token&ip="
            val url = URL(addressUrlIPv4)
            val uc = url.openConnection() as HttpsURLConnection
            val br = BufferedReader(InputStreamReader(uc.inputStream))
            val sb = StringBuilder()
            sb.append(br.readLine())
            if (sb.toString() == "OK") success = true
        }
        catch (e: IOException)
        {
            e.printStackTrace()
        }
        return success
    }

    /**
     * This method retrieves and returns the IPv6 Address of the machine.
     * @return String containing the IPv6 address of the machine
     * @throws SocketException if an I/O error occurs in the method getNetworkInterfaces() from the class NetworkInterface, or if the platform does not have at least one configured network interface.
     */
    @get:Throws(SocketException::class)
    private val enderecoIPv6: String?
        get()
        {
            var enderecoIPv6: String? = null
            val networkInterfaces = NetworkInterface.getNetworkInterfaces()
            while (networkInterfaces.hasMoreElements() && enderecoIPv6 == null)
            {
                val inetAddresses = networkInterfaces.nextElement().inetAddresses
                while (inetAddresses.hasMoreElements() && enderecoIPv6 == null)
                {
                    val inetAddress = inetAddresses.nextElement()
                    if (inetAddress.javaClass == Inet6Address::class.java)
                    {
                        if (!inetAddress.isMulticastAddress && !inetAddress.isLinkLocalAddress && !inetAddress.isSiteLocalAddress && !inetAddress.isLoopbackAddress)
                        {
                            enderecoIPv6 = inetAddress.canonicalHostName
                            try
                            {
                                if (enderecoIPv6 != null && enderecoIPv6.contains("%")) enderecoIPv6 =
                                    enderecoIPv6.substring(0, enderecoIPv6.indexOf("%"))
                            }
                            catch (e: IndexOutOfBoundsException)
                            {
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
    fun load(): Boolean
    {
        var success = false
        val configurationFile = File(filename)
        if (configurationFile.exists() && configurationFile.canRead())
        {
            try
            {
                val dataFile = Scanner(configurationFile)
                domain = dataFile.nextLine()
                token = dataFile.nextLine()
                dataFile.close()
                success = true
            }
            catch (e: FileNotFoundException)
            {
                e.printStackTrace()
            }
        }
        return success
    }

    /**
     * This method saves the configuration to the configuration file.
     */
    fun save()
    {
        val configurationFile = File(filename)
        if (!configurationFile.exists()) try
        {
            configurationFile.createNewFile()
        }
        catch (e: IOException)
        {
            e.printStackTrace()
        }
        if (configurationFile.canWrite())
        {
            try
            {
                val dataFile = Formatter(configurationFile, "UTF-8")
                dataFile.format("%s\n", domain)
                dataFile.format("%s\n", token)
                dataFile.close()
            }
            catch (e: FileNotFoundException)
            {
                e.printStackTrace()
            }
            catch (e: UnsupportedEncodingException)
            {
                e.printStackTrace()
            }
        }
    }
}