package ru.lionzxy.printbox.di.files

import dagger.Subcomponent
import ru.lionzxy.printbox.view.fakeview.FilesUploadReciever
import ru.lionzxy.printbox.view.print_files.presenter.PrintFilesPresenter
import ru.lionzxy.printbox.view.print_files.ui.DocumentViewHolder

@Subcomponent(
        modules = [FilesModule::class]
)
@FilesScope
interface FilesComponent {
    fun inject(presenter: PrintFilesPresenter)
    fun inject(holder: DocumentViewHolder)
    fun inject(broadcastReciever: FilesUploadReciever)
}
