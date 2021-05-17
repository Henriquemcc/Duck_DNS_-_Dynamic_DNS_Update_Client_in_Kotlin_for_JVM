package duckdns.gui

import java.awt.Point
import java.awt.Toolkit
import javax.swing.JFrame


/**
 * JFrame default location
 */
val JFrame.defaultLocation: Point
    /**
     * Gets JFrame default location.
     */
    get() {
        val screenSize = Toolkit.getDefaultToolkit().screenSize
        return Point(
            screenSize.width / 2 - this.size.width / 2,
            screenSize.height / 2 - this.size.height / 2
        )
    }