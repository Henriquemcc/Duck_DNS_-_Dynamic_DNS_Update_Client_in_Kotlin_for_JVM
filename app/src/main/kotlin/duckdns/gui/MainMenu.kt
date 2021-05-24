package duckdns.gui

import duckdns.controller.cleanIPAddresses
import duckdns.controller.updateIPAddress
import duckdns.gui.subdomain.SubdomainMenu
import duckdns.international.resourceBundle
import java.awt.GridLayout
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.JPanel

/**
 * Graphical User Interface main menu that allows GUI user to choose what to do.
 */
class MainMenu {

    /**
     * Subdomains menu's window.
     */
    private val jFrame = JFrame(resourceBundle.getString("duck.dns.ip.address.updater").capitalize())

    /**
     * Array list with all child components created by this class.
     */
    private val childComponents = ArrayList<Any>()

    /**
     * Initializes Main Menu class creating all GUI components recursively.
     */
    init {
        jFrameInit()
    }

    /**
     * Initializes button updateSubdomainsIPAddressesOnce.
     * @return A JButton object initialized.
     */
    private fun buttonUpdateSubdomainsIPAddressesOnceInit(): JButton {
        val buttonUpdateSubdomainsIPAddressesOnce =
            JButton(resourceBundle.getString("update.subdomain.s.IP.addresses").capitalize())
        buttonUpdateSubdomainsIPAddressesOnce.font = defaultFont
        buttonUpdateSubdomainsIPAddressesOnce.addActionListener {
            jFrame.isVisible = false
            updateIPAddress()
            jFrame.isVisible = true
        }
        return buttonUpdateSubdomainsIPAddressesOnce
    }

    /**
     * Initializes buttonSubdomainMenu.
     * @return A JButton initialized.
     */
    private fun buttonSubdomainMenuInit(): JButton {
        val buttonSubdomainMenu = JButton(resourceBundle.getString("subdomain.menu").capitalize())
        buttonSubdomainMenu.font = defaultFont
        buttonSubdomainMenu.addActionListener {
            jFrame.isVisible = false
            childComponents.add(SubdomainMenu(this))
        }
        return buttonSubdomainMenu
    }

    /**
     * Initializes JFrame and it's subcomponents recursively.
     * @return A JFrame object with it's subcomponents initialized.
     */
    private fun jFrameInit(): JFrame {
        jFrame.font = defaultFont
        val jPanel = jPanelInit()
        jFrame.add(jPanel)
        jFrame.pack()
        jFrame.isVisible = true
        jFrame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        jFrame.location = jFrame.defaultLocation
        return jFrame

    }

    /**
     * Initializes JPanel and it's subcomponents recursively.
     * @return A JPanel object with it's subcomponents initialized.
     */
    private fun jPanelInit(): JPanel {
        val jPanel = JPanel(GridLayout(4, 1))
        jPanel.font = defaultFont
        val buttonRunDuckDNSIPAddressUpdaterOnce = buttonUpdateSubdomainsIPAddressesOnceInit()
        jPanel.add(buttonRunDuckDNSIPAddressUpdaterOnce)
        val buttonRunDuckDNSIPAddressUpdaterInInfiniteLoop = buttonUpdateSubdomainsIPAddressesInLoopingInit()
        jPanel.add(buttonRunDuckDNSIPAddressUpdaterInInfiniteLoop)
        val buttonSubdomainMenu = buttonSubdomainMenuInit()
        jPanel.add(buttonSubdomainMenu)
        val buttonCleanSubdomainsIPAddresses = buttonCleanSubdomainsIPAddressesInit()
        jPanel.add(buttonCleanSubdomainsIPAddresses)
        return jPanel

    }

    private fun buttonUpdateSubdomainsIPAddressesInLoopingInit(): JButton {
        val buttonUpdateSubdomainsIPAddressesInLooping =
            JButton(resourceBundle.getString("update.subdomain.s.IP.addresses.in.infinite.looping").capitalize())
        buttonUpdateSubdomainsIPAddressesInLooping.font = defaultFont
        buttonUpdateSubdomainsIPAddressesInLooping.addActionListener {
            jFrame.isVisible = false
            updateIPAddress(true)
        }
        return buttonUpdateSubdomainsIPAddressesInLooping
    }

    private fun buttonCleanSubdomainsIPAddressesInit(): JButton {
        val buttonCleanSubdomainsIPAddresses =
            JButton(resourceBundle.getString("clean.subdomain.s.IP.addresses").capitalize())
        buttonCleanSubdomainsIPAddresses.font = defaultFont
        buttonCleanSubdomainsIPAddresses.addActionListener {
            jFrame.isVisible = false
            cleanIPAddresses()
            jFrame.isVisible = true
        }
        return buttonCleanSubdomainsIPAddresses
    }

    /**
     * Makes this menu jFrame visible.
     */
    fun makeVisible() {
        jFrame.isVisible = true
    }

    /**
     * Adds a closing operation to this menu JFrame.
     * @param runnable Action that will run when this menu JFrame window closes.
     */
    fun addCloseOperation(runnable: Runnable) {
        jFrame.addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                runnable.run()
                super.windowClosing(e)
            }
        })
    }


}