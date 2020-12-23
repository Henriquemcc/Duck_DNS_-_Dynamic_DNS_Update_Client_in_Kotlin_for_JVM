private val DuckDNS = DynamicDNS()

/**
 * This is the main method.
 * @param args Command line parameter.
 */
fun main(args: Array<String>)
{
    var enableIPv6 = true
    var enableIPv4 = true

    when
    {
        "--IPv6-only" in args ->
        {
            enableIPv6 = true
            enableIPv4 = false
        }

        "--IPv4-only" in args ->
        {
            enableIPv6 = false
            enableIPv4 = true
        }
    }

    val loaded = DuckDNS.load()
    if (!loaded)
    {
        println("File Not Loaded")
        println("Please, type:")
        print("Subdomain: ")
        DuckDNS.setDomain(readLine()!!.trim())
        print("Token: ")
        DuckDNS.setToken(readLine()!!.trim())
        DuckDNS.save()
    }
    val success = DuckDNS.update(enableIPv6, enableIPv4)
    when
    {
        success.toInt() == 10 ->
        {
            println("IPv4: Success")
            println("IPv6: Success")
        }
        success.toInt() == 6  ->
        {
            println("IPv6: Success")
            println("IPv4: Failure")
        }
        success.toInt() == 4  ->
        {
            println("IPv6: Failure")
            println("IPv4: Success")
        }
        success.toInt() == 0  ->
        {
            println("IPv6: Failure")
            println("IPv4: Failure")
        }
    }
}