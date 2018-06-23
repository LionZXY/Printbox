package ru.lionzxy.printbox.di.app

import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import ru.lionzxy.printbox.data.model.PrintCartModel
import ru.lionzxy.printbox.data.model.PrintPlace
import ru.lionzxy.printbox.data.stores.IPrintCartStore
import ru.lionzxy.printbox.data.stores.PrintCartStore
import ru.lionzxy.printbox.repository.print.PREF_LASTPRINT
import javax.inject.Singleton

@Module
class CartModule {
    @Singleton
    @Provides
    fun providePrintCart(preferences: SharedPreferences, gson: Gson): IPrintCartStore {
        val printCartModel = PrintCartModel()

        val jsonLastPrint = preferences.getString(PREF_LASTPRINT, "{}")
        val lastPrint = gson.fromJson(jsonLastPrint, PrintPlace::class.java)
        if (!lastPrint.isEmpty()) {
            printCartModel.printPlace = lastPrint
        }

        return PrintCartStore(printCartModel)
    }
}