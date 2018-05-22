package ru.lionzxy.printbox.di.files

import dagger.Subcomponent
import ru.lionzxy.printbox.view.print_files.presenter.PrintFilesPresenter

@Subcomponent(
        modules = [FilesModule::class]
)
@FilesScope
interface FilesComponent {
    fun inject(presenter: PrintFilesPresenter)
}
