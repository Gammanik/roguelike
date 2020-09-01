package com.roguelike.graphics.model

import java.awt.Color
import java.awt.Font
import java.awt.Graphics2D

class SavePopUp {
    fun draw(g: Graphics2D) {
        g.color = Color.WHITE
        g.fill3DRect(400, 5, 112 , 25,true)

        g.font = Font("TimesRoman", Font.BOLD, 20)
        g.color = Color(0, 0, 0)
        g.drawString("Game saved!", 400, 25)
    }
}
