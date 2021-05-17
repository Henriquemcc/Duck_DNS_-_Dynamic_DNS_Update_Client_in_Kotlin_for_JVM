package duckdns.gui.subdomain

import duckdns.controller.create
import duckdns.gui.LowerCaseDocumentFilter
import duckdns.gui.defaultFont
import duckdns.gui.defaultLocation
import duckdns.model.Subdomain
import java.awt.GridLayout
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.*
import javax.swing.text.AbstractDocument

/**
 * Graphical User Interface menu that allows GUI user to create a new subdomain.
 * @param subdomainMenu Subdomain menu to refresh JTable when a new Subdomain object is add to subdomains objects array list.
 */
class CreateSubdomain(private val subdomainMenu: SubdomainMenu) {

    /**
     * Text field that will obtain from GUI user the Duck DNS subdomain's address.
     */
    private val jTextFieldSubdomain = JTextField("Subdomain")

    /**
     * Text field that will obtain from GUI user the Duck DNS subdomain's token.
     */
    private val jTextFieldToken = JTextField("Token")

    /**
     * Checkbox that will obtain from GUI user the Duck DNS subdomain's enable IPv6 attribute.
     */
    private val jCheckBoxEnableIPv6 = JCheckBox("Enable IPv6", true)

    /**
     * Checkbox that will obtain from GUI user the Duck DNS subdomain's enable IPv4 attribute.
     */
    private val jCheckBoxEnableIPv4 = JCheckBox("Enable IPv4", true)

    /**
     * JFrame which will store jPanel with other GUI components.
     */
    private val jFrame = JFrame("Subdomain creation menu")

    /**
     * Initializes CreateSubdomain class creating all GUI components recursively.
     */
    init {
        jFrameInit()
    }

    /**
     * Initializes jFrame and it's subcomponents recursively.
     * @return A JFrame object with it's subcomponents initialized.
     */
    private fun jFrameInit(): JFrame {
        val jPanel = jPanelInit()
        jFrame.add(jPanel)
        jFrame.isVisible = true
        jFrame.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        jFrame.location = jFrame.defaultLocation
        jFrame.addWindowListener(
            object : WindowAdapter() {
                override fun windowClosed(e: WindowEvent?) {
                    subdomainMenu.makeVisible()
                    super.windowClosed(e)
                }
            }
        )
        jFrame.pack()

        return jFrame

    }

    /**
     * Initializes jPanel and it's subcomponents recursively.
     * @return A JPanel object with it's subcomponents initialized.
     */
    private fun jPanelInit(): JPanel {
        val jPanel = JPanel(GridLayout(5, 1))
        val jTextFieldSubdomain = jTextFieldSubdomainInit()
        jPanel.add(jTextFieldSubdomain)
        val jTextFieldToken = jTextFieldTokenInit()
        jPanel.add(jTextFieldToken)
        val jCheckBoxEnableIPv4 = jCheckBoxEnableIPv4Init()
        jPanel.add(jCheckBoxEnableIPv4)
        val jCheckBoxEnableIPv6 = jCheckBoxEnableIPv6Init()
        jPanel.add(jCheckBoxEnableIPv6)
        val jButtonAddSubdomain = jButtonAddSubdomainInit()
        jPanel.add(jButtonAddSubdomain)


        return jPanel
    }

    /**
     * Initializes jButtonAddSubdomain.
     * @return A JButton object initialized.
     */
    private fun jButtonAddSubdomainInit(): JButton {
        val jButtonAddSubdomain = JButton("Add Subdomain")
        jButtonAddSubdomain.font = defaultFont
        jButtonAddSubdomain.addActionListener {
            val subdomain = Subdomain(jTextFieldSubdomain.text.toLowerCase(), jCheckBoxEnableIPv4.isSelected, jCheckBoxEnableIPv6.isSelected, jTextFieldToken.text)
            create(subdomain)
            jFrame.dispatchEvent(WindowEvent(jFrame, WindowEvent.WINDOW_CLOSING))
            subdomainMenu.refreshTableSubdomains()
        }
        return jButtonAddSubdomain
    }

    /**
     * Initializes jCheckBoxEnableIPv6.
     * @return JCheckBox object initialized.
     */
    private fun jCheckBoxEnableIPv6Init(): JCheckBox {
        jCheckBoxEnableIPv6.font = defaultFont
        return jCheckBoxEnableIPv6
    }

    /**
     * Initializes jCheckBoxEnableIPv4.
     * @return JCheckbox object initialized.
     */
    private fun jCheckBoxEnableIPv4Init(): JCheckBox {
        jCheckBoxEnableIPv4.font = defaultFont
        return jCheckBoxEnableIPv4
    }

    /**
     * Initializes jTextFieldToken.
     * @return JTextField object initialized.
     */
    private fun jTextFieldTokenInit(): JTextField {
        jTextFieldToken.font = defaultFont
        jTextFieldToken.isEditable = true
        return jTextFieldToken
    }

    /**
     * Initializes jTextFieldSubdomain.
     * @return JTextField object initialized.
     */
    private fun jTextFieldSubdomainInit(): JTextField {
        jTextFieldSubdomain.font = defaultFont
        jTextFieldSubdomain.isEditable = true
        (jTextFieldSubdomain.document as AbstractDocument).documentFilter = LowerCaseDocumentFilter()
        return jTextFieldSubdomain
    }
}