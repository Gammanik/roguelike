import javax.swing.JFrame
import javax.swing.WindowConstants

class Example : JFrame() {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val ex = Example()
            ex.isVisible = true
        }
    }

    init {
        title = "Simple example"
        setSize(300, 200)
        setLocationRelativeTo(null)
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
    }
}
