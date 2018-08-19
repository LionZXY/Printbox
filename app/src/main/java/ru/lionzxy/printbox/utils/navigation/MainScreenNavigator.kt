package ru.lionzxy.printbox.utils.navigation

import android.content.Context
import android.content.Intent
import ru.lionzxy.printbox.view.main.ui.MainActivity
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace

class MainScreenNavigator(private val context: Context) : PrintBoxNavigator {
    companion object {
        const val EXTRA_SCREENKEY = "screenkey"
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
        })
        return true
    }

}