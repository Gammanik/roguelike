package com.roguelike.commands

class CommandController private constructor(){

    private val map = mutableMapOf<String, Command>()

    companion object {
        private val instance: CommandController? = null

        fun getInstance(): CommandController {
            return instance ?: CommandController()
        }
    }

    fun registerCommand(key: String, cmd: Command) {
        map[key] = cmd
    }
    
    fun runCommand(key: String) {
        map[key]?.execute()
    }
}
