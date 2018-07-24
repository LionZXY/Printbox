package ru.lionzxy.printbox

import android.support.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import net.gotev.uploadservice.UploadService
import ru.lionzxy.printbox.di.app.AppComponent
import ru.lionzxy.printbox.di.app.AppModule
import ru.lionzxy.printbox.di.app.DaggerAppComponent
import timber.log.Timber
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import ru.lionzxy.printbox.utils.toast


class App : MultiDexApplication() {
    companion object {
        @JvmStatic
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

        UploadService.NAMESPACE = BuildConfig.APPLICATION_ID
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Stetho.initializeWithDefaults(this)
        } else {
            Timber.plant(Timber.DebugTree()) //TODO
        }

    }
}