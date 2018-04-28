package ru.lionzxy.printbox.di.print

import dagger.Subcomponent

@Subcomponent(
        modules = [PrintModule::class]
)
@PrintScope
interface PrintComponent
