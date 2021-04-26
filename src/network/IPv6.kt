package network

import java.net.Inet6Address
import java.net.NetworkInterface


/**
 * Get the global Internet Protocol Version 6 address of the machine.
 * @return ArrayList with all IPv6 addresses.
 */
fun getIPv6Address(): ArrayList<String> {
    val iPv6Addresses = ArrayList<String>()
    for (networkInterface in NetworkInterface.getNetworkInterfaces())
        for (inetAddress in networkInterface.inetAddresses)
            if (inetAddress is Inet6Address && !(inetAddress.isMulticastAddress || inetAddress.isLinkLocalAddress || inetAddress.isSiteLocalAddress || inetAddress.isLoopbackAddress)) {
                var address = inetAddress.hostAddress
                if (address?.contains("%") == true) address = address.substring(0, address.indexOf("%"))
                iPv6Addresses.add(address)
            }

    return iPv6Addresses
}