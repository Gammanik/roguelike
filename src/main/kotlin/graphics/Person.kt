package graphics

import java.awt.geom.Ellipse2D

class Person(x: kotlin.Int, y: kotlin.Int, w: kotlin.Double, h: kotlin.Double)
    : Ellipse2D.Double(x.toDouble(), y.toDouble(), w, h) { }
