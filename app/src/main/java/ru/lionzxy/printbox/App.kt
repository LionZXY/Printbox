package ru.lionzxy.printbox

import android.support.multidex.MultiDexApplication
import net.gotev.uploadservice.UploadService
import ru.lionzxy.printbox.di.app.AppComponent
import ru.lionzxy.printbox.di.app.AppModule
import ru.lionzxy.printbox.di.app.DaggerAppComponent
import timber.log.Timber


class App : MultiDexApplication() {
    companion object {
        @JvmStatic
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(Timber.DebugTree()) //TODO
        }
    }
}