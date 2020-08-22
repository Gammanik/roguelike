import graphics.Ramka
import java.awt.Color
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.Rectangle2D
import java.util.*
import javax.swing.JFrame
import javax.swing.JPanel

fun main() {

    val ramka = Ramka()

    for (i in 0..60) {
        for (j in 0..45) {
            ramka.addRectangle(i * 10, j * 10, 10, 10)
        }
    }

    ramka.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    ramka.isVisible = true

    Thread.sleep(2000)

//    ramka.isVisible = false
//
//    Thread.sleep(1000)
//
//    ramka.isVisible = true

    ramka.rectangle.map.changeColor(50, 50, Color.RED)

    ramka.repaint()
}
