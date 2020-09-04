package com.roguelike.commands

import java.awt.event.ActionEvent
import javax.swing.AbstractAction
import javax.swing.ActionMap
import javax.swing.InputMap
import javax.swing.KeyStroke

/** register and executes commands when button with key is pressed
 * use it if you want to add new logic **/
class CommandController private constructor(){

    private val keyToCommand = mutableMapOf<String, Command>()

    companion object {
        private val instance: CommandController? = null

        fun getInstance(): CommandController {
            return instance ?: CommandController()
        }
    }


    fun registerCommand(key: String, command: Command,
                        inputMap: InputMap? = null, actionMap: ActionMap? = null) {
        keyToCommand[key] = command

        inputMap?.put(KeyStroke.getKeyStroke(key), key)
        actionMap?.put(key, object : AbstractAction() {
            override fun actionPerformed(p0: ActionEvent?) {
                command.execute()
            }
        } )
    }
    
    fun runCommand(key: String) {
        keyToCommand[key]?.execute()
    }
}
