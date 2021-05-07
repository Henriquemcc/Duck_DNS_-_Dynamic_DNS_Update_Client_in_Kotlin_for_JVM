package console.subdomain

/**
 * Retrieve and prints all Duck DNS Subdomain Objects.
 */
fun printAllSubdomains() {
    console.printHeader("Subdomains:")
    controller.getSubdomains().forEach { println(it) }
    println()
}