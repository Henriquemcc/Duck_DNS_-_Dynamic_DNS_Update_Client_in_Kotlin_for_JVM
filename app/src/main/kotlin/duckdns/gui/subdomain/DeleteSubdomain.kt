package duckdns.gui.subdomain

import duckdns.controller.remove
import duckdns.gui.getConfirmation
import duckdns.international.resourceBundle
import duckdns.model.Subdomain
import java.text.MessageFormat

/**
 * Graphical User Interface menu that allows GUI user to delete a subdomain.
 * @param subdomainMenu Subdomain menu to refresh JTable when a new Subdomain object is removed from subdomains objects array list.
 * @param oldSubdomain Old subdomain object to remove from Subdomains object array list.
 */
class DeleteSubdomain(private val subdomainMenu: SubdomainMenu, private val oldSubdomain: Subdomain) {

    /**
     * Initializes DeleteSubdomain class and all it's components.
     */
    init {
        val confirmation = getConfirmation(
            "${
                MessageFormat.format(
                    resourceBundle.getString("are.you.sure.you.want.to.delete.0.subdomain").capitalize(),
                    oldSubdomain
                )
            }?"
        )//"Are you sure you want to delete $oldSubdomain subdomain?"
        if (confirmation) {
            remove(oldSubdomain)
            subdomainMenu.refreshTableSubdomains()
        }
    }
}