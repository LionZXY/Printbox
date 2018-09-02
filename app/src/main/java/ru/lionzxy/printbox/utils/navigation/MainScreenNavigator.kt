package ru.lionzxy.printbox.utils.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import ru.lionzxy.printbox.view.main.ui.MainActivity
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace

class MainScreenNavigator(private val context: Context) : PrintBoxNavigator {
    companion object {
        const val EXTRA_SCREENKEY = "screenkey"
        const val EXTRA_DATA = "data"
    }

    override fun applyCommand(command: Command): Boolean {
        if (command !is Replace) {
            return false
        }

        if (!command.screenKey.startsWith(PrintBoxRouter.MAIN)) {
            return false
        }
        context.startActivity(Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(EXTRA_SCREENKEY, command.screenKey)
            if (command.transitionData != null && command.transitionData is Bundle) {
                putExtra(EXTRA_DATA, command.transitionData as Bundle)
            }
        })
        return true
    }

}