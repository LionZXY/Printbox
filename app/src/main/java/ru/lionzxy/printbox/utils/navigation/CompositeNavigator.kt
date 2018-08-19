package ru.lionzxy.printbox.utils.navigation

import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.commands.Command

class CompositeNavigator : Navigator {
    private val navigators = ArrayList<PrintBoxNavigator>()

    public fun addNavigator(navigator: PrintBoxNavigator) {
        navigators.add(navigator)
    }

    public fun removeNavigator(navigator: PrintBoxNavigator) {
        navigators.remove(navigator)
    }

    override fun applyCommands(commands: Array<out Command>?) {
        commands?.forEach { command ->
            navigators.forEach navigator@{
                if (it.applyCommand(command)) {
                    return@navigator
                }
            }
        }
    }
}

interface PrintBoxNavigator {
    fun applyCommand(command: Command): Boolean
}