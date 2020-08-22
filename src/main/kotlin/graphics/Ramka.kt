package graphics

import java.awt.BorderLayout
import java.awt.Container
import javax.swing.JFrame
import javax.swing.JLabel

class Ramka : JFrame() {

    var powZawartosci: Container = contentPane
    var rectangle = DrawRectangle()

    fun addRectangle(startX: Int, startY: Int, sizeX: Int, sizeY: Int) {
        rectangle.addRectangle(startX, startY, sizeX, sizeY)
    }

    companion object {
        const val XSIZE = 800
        const val YSIZE = 600
    }

    init {
        setSize(XSIZE, YSIZE)
        title = "BROKE DAY"
        powZawartosci.add(JLabel("ROGUELIKE GAME v0.1.1"), BorderLayout.NORTH)
        powZawartosci.add(rectangle)
    }
}