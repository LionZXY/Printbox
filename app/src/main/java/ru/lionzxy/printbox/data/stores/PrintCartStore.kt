package ru.lionzxy.printbox.data.stores

import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import ru.lionzxy.printbox.data.model.PrintCartModel

class PrintCartStore(printCartModel: PrintCartModel = PrintCartModel()) : IPrintCartStore {
    private val subject = BehaviorSubject.create<PrintCartModel>()

    init {
        subject.onNext(printCartModel)
    }

    override fun getObservableCart(): Observable<PrintCartModel> {
        return subject
    }

    override fun setCart(printCartModel: PrintCartModel) {
        subject.onNext(printCartModel)
    }
}