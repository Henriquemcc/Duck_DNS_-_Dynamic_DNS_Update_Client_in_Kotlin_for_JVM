private val DuckDNS = DynamicDNS()

/**
 * This is the main method.
 * @param args Command line parameter.
 */
fun main(args: Array<String>) {
    var enableIPv6 = true
    var enableIPv4 = true

    when {
        "--IPv6-only" in args -> {
            enableIPv6 = true
            enableIPv4 = false
        }

        "--IPv4-only" in args -> {
            enableIPv6 = false
            enableIPv4 = true
        }
    }

    val loaded = DuckDNS.load()
    if (!loaded) {
        println("File Not Loaded")
        println("Please, type:")
        print("Subdomain: ")
        DuckDNS.setDomain((readLine() ?: return).trim())
        print("Token: ")
        DuckDNS.setToken((readLine() ?: return).trim())
        DuckDNS.save()
    }
    DuckDNS.update(enableIPv6, enableIPv4)

}