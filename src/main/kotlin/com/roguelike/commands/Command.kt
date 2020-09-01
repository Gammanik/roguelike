package com.roguelike.commands

/** command pattern. Used to separate business logic from UI classes **/
abstract class Command {
    /** executes the command
     * write your business logic here **/
    abstract fun execute(): Any
}
