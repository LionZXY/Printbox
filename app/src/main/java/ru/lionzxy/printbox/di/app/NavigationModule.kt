package ru.lionzxy.printbox.di.app

import android.content.Context
import dagger.Module
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Singleton
import dagger.Provides
import ru.lionzxy.printbox.utils.navigation.CompositeNavigator
import ru.lionzxy.printbox.utils.navigation.MainScreenNavigator
import ru.lionzxy.printbox.utils.navigation.PrintBoxRouter
import ru.terrakok.cicerone.Cicerone

@Module
class NavigationModule {
    @Provides
    @Singleton
    fun provideCicerone(context: Context): Cicerone<PrintBoxRouter> {
        return Cicerone.create(PrintBoxRouter()).apply {
            navigatorHolder.setNavigator(CompositeNavigator().apply {
                addNavigator(MainScreenNavigator(context))
            })
        }
    }

    @Provides
    @Singleton
    fun provideRouter(cicerone: Cicerone<PrintBoxRouter>): PrintBoxRouter {
        return cicerone.router
    }

    @Provides
    @Singleton
    fun provideNavigatorHolder(cicerone: Cicerone<PrintBoxRouter>): NavigatorHolder {
        return cicerone.navigatorHolder
    }
}