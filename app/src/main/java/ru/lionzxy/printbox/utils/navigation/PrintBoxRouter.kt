package ru.lionzxy.printbox.utils.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringDef
import ru.lionzxy.printbox.view.main.ui.MainActivity
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.commands.Replace

@Retention(AnnotationRetention.SOURCE)
@StringDef(PrintBoxRouter.MAIN, PrintBoxRouter.MAIN_FILE)
annotation class ScreenKey

/*TODO: отрефакторить роутинг везде*/
class PrintBoxRouter : Router() {
    companion object {
        const val MAIN = "main"
        const val MAIN_PRINT = "${MAIN}_print"
        const val MAIN_PAY = "${MAIN}_pay"
        const val MAIN_FILE = "${MAIN_PRINT}_file"
    }

    fun openMainScreen(@ScreenKey screenKey: String, data: Bundle? = null) {
        replaceScreen(screenKey, data)
    }
}