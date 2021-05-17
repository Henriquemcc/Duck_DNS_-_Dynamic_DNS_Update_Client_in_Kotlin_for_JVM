package duckdns.gui.subdomain

import duckdns.controller.getSubdomains
import duckdns.gui.MainMenu
import duckdns.gui.defaultFont
import duckdns.gui.defaultLocation
import duckdns.model.Subdomain
import java.awt.FlowLayout
import java.awt.GridLayout
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.*
import javax.swing.table.AbstractTableModel
import javax.swing.table.TableModel

/**
 * Graphical User Interface menu that allows GUI user to retrieve, create, update and delete Duck DNS Subdomains.
 */
class SubdomainMenu(private val mainMenu: MainMenu) {

    /**
     * Subdomain menu's window.
     */
    private val jFrame = JFrame("Duck DNS Subdomain Menu")

    /**
     * JTable that will show for GUI user Duck DNS Subdomains objects stored on Subdomain object array list.
     */
    private val jTableSubdomains = JTable()

    /**
     * Object from class SubdomainTableModel which will setup jTableSubdomains to retrieve Duck DNS Subdomains from subdomains object array list and build the table.
     */
    private val tableModel = SubdomainTableModel(getSubdomains())

    /**
     * Array list with all child components created by this class.
     */
    private val childComponents = ArrayList<Any>()

    /**
     * Initializes SubdomainMenu class creating all GUI components recursively.
     */
    init {
        jFrameInit()
    }

    /**
     * Refreshes table subdomains when a subdomain is added, updated or removed from Duck DNS subdomain objects array list.
     */
    fun refreshTableSubdomains() {
        tableModel.fireTableDataChanged()
    }

    /**
     * Initializes jFrame and it's subcomponents recursively.
     * @return A JFrame object with it's subcomponents initialized.
     */
    private fun jFrameInit(): JFrame {
        val jPanel = jPanelInit()
        jFrame.add(jPanel)
        jFrame.isVisible = true
        jFrame.font = defaultFont
        jFrame.defaultCloseOperation = JFrame.DISPOSE_ON_CLOSE
        jFrame.location = jFrame.defaultLocation
        jFrame.addWindowListener(
            object : WindowAdapter() {
                override fun windowClosed(e: WindowEvent?) {
                    mainMenu.makeVisible()
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
        val jPanel = JPanel()
        jPanel.font = defaultFont
        val jPanelButtonsAndTable = jPanelButtonsAndTableInit()
        jPanel.add(jPanelButtonsAndTable)

        return jPanel
    }

    /**
     * Initializes jPanelButtonsAndTable and it's subcomponents recursively.
     * @return A JPanel object with it's subcomponents initialized.
     */
    private fun jPanelButtonsAndTableInit(): JPanel {
        val jPanelButtonsAndTable = JPanel(FlowLayout())
        jPanelButtonsAndTable.font = defaultFont
        val jPanelButtons = jPanelButtonsInit()
        jPanelButtonsAndTable.add(jPanelButtons)
        val jScrollPaneTableSubdomains = jScrollPaneTableSubdomainsInit()
        jPanelButtonsAndTable.add(jScrollPaneTableSubdomains)

        return jPanelButtonsAndTable
    }

    /**
     * Initializes jPanelButtons and it's subcomponents recursively.
     * @return A JPanel object with it's subcomponents initialized.
     */
    private fun jPanelButtonsInit(): JPanel {
        val jPanelButtons = JPanel(GridLayout(4, 1))
        jPanelButtons.font = defaultFont
        val buttonCreate = buttonCreateInit()
        jPanelButtons.add(buttonCreate)
        val buttonRetrieve = buttonRetrieveInit()
        jPanelButtons.add(buttonRetrieve)
        val buttonUpdate = buttonUpdateInit()
        jPanelButtons.add(buttonUpdate)
        val buttonDelete = buttonDeleteInit()
        jPanelButtons.add(buttonDelete)

        return jPanelButtons
    }

    /**
     * Initializes buttonDelete.
     * @return A JButton object initialized.
     */
    private fun buttonDeleteInit(): JButton {
        val buttonDelete = JButton("Delete")
        buttonDelete.font = defaultFont
        buttonDelete.addActionListener {
            val row = jTableSubdomains.selectedRow
            val subdomainName = jTableSubdomains.getValueAt(row, 0) as String
            val oldSubdomain = getSubdomains().find {
                it.subdomainName.equals(subdomainName, true)
            }
            oldSubdomain?.let {
                childComponents.add(DeleteSubdomain(this, it))
            }
        }

        return buttonDelete
    }

    /**
     * Initializes buttonUpdate.
     * @return A JButton object initialized.
     */
    private fun buttonUpdateInit(): JButton {
        val buttonUpdate = JButton("Update")
        buttonUpdate.font = defaultFont
        buttonUpdate.addActionListener {
            val row = jTableSubdomains.selectedRow
            val subdomainName = jTableSubdomains.getValueAt(row, 0) as String
            val oldSubdomain = getSubdomains().find {
                it.subdomainName.equals(subdomainName, true)
            }
            oldSubdomain?.let {
                jFrame.isVisible = false
                childComponents.add(UpdateSubdomain(this, it))
            }
        }

        return buttonUpdate
    }

    /**
     * Initializes buttonRetrieve.
     * @return A JButton object initialized.
     */
    private fun buttonRetrieveInit(): JButton {
        val buttonRetrieve = JButton("Retrieve")
        buttonRetrieve.font = defaultFont
        buttonRetrieve.addActionListener {
            refreshTableSubdomains()
        }

        return buttonRetrieve
    }

    /**
     * Initializes buttonCreate.
     * @return A JButton object initialized.
     */
    private fun buttonCreateInit(): JButton {
        val buttonCreate = JButton("Create")
        buttonCreate.font = defaultFont
        buttonCreate.addActionListener {
            jFrame.isVisible = false
            CreateSubdomain(this)
        }

        return buttonCreate
    }

    /**
     * Initializes a JScrollPane with jTableSubdomains object.
     * @return A JScrollPane with it's subcomponents initialized.
     */
    private fun jScrollPaneTableSubdomainsInit(): JScrollPane {
        val jTableSubdomains = jTableSubdomainsInit()
        jTableSubdomains.font = defaultFont
        return JScrollPane(jTableSubdomains)
    }

    /**
     * Initializes jTableSubdomains.
     * @return A JTable with it's subcomponents initialized.
     */
    private fun jTableSubdomainsInit(): JTable {
        jTableSubdomains.font = defaultFont
        jTableSubdomains.model = tableModelInit()
        jTableSubdomains.autoResizeMode = JTable.AUTO_RESIZE_ALL_COLUMNS

        return jTableSubdomains
    }

    /**
     * Initializes jTableModel.
     * @return A TableModel object initialized.
     */
    private fun tableModelInit(): TableModel {
        return tableModel
    }

    /**
     * Table model class to obtain subdomains from subdomains object array list.
     * @param subdomains Duck DNS Subdomain object array list.
     */
    private class SubdomainTableModel(
        private val subdomains: ArrayList<Subdomain>
    ) : AbstractTableModel() {

        /**
         * Gets the number of rows in the table.
         * @return Number of rows in the table.
         */
        override fun getRowCount(): Int {
            return subdomains.size
        }

        /**
         * Gets the number of columns in the table.
         * @return Number of columns in the table.
         */
        override fun getColumnCount(): Int {
            return 4
        }

        /**
         * Gets Duck DNS Subdomain attributes.
         * @param p0 Row number.
         * @param p1 Column number.
         */
        override fun getValueAt(p0: Int, p1: Int): Any {
            val subdomain = subdomains[p0]
            return when (p1) {
                0 -> subdomain.subdomainName
                1 -> subdomain.enableIPv4
                2 -> subdomain.enableIPv6
                3 -> subdomain.token
                else -> ""
            }
        }

        /**
         * Indicates whether a cell is editable.
         * @param rowIndex Row number.
         * @param columnIndex Column number.
         * @return Boolean value that indicates whether a cell is editable.
         */
        override fun isCellEditable(rowIndex: Int, columnIndex: Int): Boolean {
            return false
        }

        /**
         * Gets column name
         * @param column Column number.
         * @return A String with column name.
         */
        override fun getColumnName(column: Int): String {
            return when (column) {
                0 -> "Subdomain name"
                1 -> "Enable IPv4"
                2 -> "Enable IPv6"
                3 -> "Token"
                else -> ""
            }
        }

    }

    /**
     * Makes this menu jFrame visible.
     */
    fun makeVisible() {
        jFrame.isVisible = true
    }
}