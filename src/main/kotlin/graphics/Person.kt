package graphics

import java.awt.geom.Ellipse2D

class Person(x: kotlin.Double,y: kotlin.Double,w: kotlin.Double, h: kotlin.Double)
    : Ellipse2D.Double(x, y, w, h) { }
