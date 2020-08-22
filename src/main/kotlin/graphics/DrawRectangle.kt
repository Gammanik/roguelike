package graphics

import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.Rectangle2D
import java.util.*
import javax.swing.JPanel

class DrawRectangle() : JPanel() {

    val map: Map = Map()

    private val squares: MutableList<Rectangle2D>

    fun addRectangle(startX: Int, startY: Int, w: Int, h : Int) { // square
        squares.add(Rectangle2D.Double(startX.toDouble(), startY.toDouble(), w.toDouble(), h.toDouble()))
    }

    public override fun paintComponent(g: Graphics) {

        val g1 = g as Graphics2D

        g1.color = Color.BLACK;

        for (rect in squares) {
            g1.draw(rect)
        }



        for (point in map.rectList) {
            g1.color = point.col;
            g1.fill3DRect(point.x * 10, point.y * 10, 10, 10, true);
        }

//        g1.color = Color.RED;
//        g1.fill3DRect(20, 20, 200, 200, true);
//        g1.color = Color.YELLOW;
//        g1.fill3DRect(200, 200, 200, 200, true);
    }

    init {
        squares = ArrayList()
    }
}