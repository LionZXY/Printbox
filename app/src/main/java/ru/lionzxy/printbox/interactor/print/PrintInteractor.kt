package ru.lionzxy.printbox.interactor.print

import ru.lionzxy.printbox.repository.print.IPrintRepository

class PrintInteractor(
        val printRepository: IPrintRepository
) : IPrintInteractor {

}