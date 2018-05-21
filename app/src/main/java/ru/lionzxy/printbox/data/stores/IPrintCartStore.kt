package ru.lionzxy.printbox.data.stores

import io.reactivex.Observable
import ru.lionzxy.printbox.data.model.PrintCartModel

interface IPrintCartStore {
    fun getObservableCart(): Observable<PrintCartModel>
    fun setCart(printCartModel: PrintCartModel)
}