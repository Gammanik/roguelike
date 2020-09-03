package com.roguelike.commands

import java.awt.event.ActionEvent
import javax.swing.AbstractAction
import javax.swing.ActionMap
import javax.swing.InputMap
import javax.swing.KeyStroke

class CommandController private constructor(){

    private val keyToCommand = mutableMapOf<String, Command>()

    companion object {
        private val instance: CommandController? = null

        fun getInstance(): CommandController {
            return instance ?: CommandController()
        }
    }

    fun registerCommand(key: String, cmd: Command, im: InputMap? = null, am: ActionMap? = null) {
        keyToCommand[key] = cmd

        im?.put(KeyStroke.getKeyStroke(key), key)
        am?.put(key, object : AbstractAction() {
            override fun actionPerformed(p0: ActionEvent?) {
                cmd.execute()
            }
        } )
    }
    
    fun runCommand(key: String) {
        keyToCommand[key]?.execute()
    }
}
