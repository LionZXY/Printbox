package ru.lionzxy.printbox

import android.app.Application
import ru.lionzxy.printbox.di.app.AppComponent
import ru.lionzxy.printbox.di.app.AppModule
import ru.lionzxy.printbox.di.app.DaggerAppComponent


class App : Application() {
    companion object {
        @JvmStatic
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}