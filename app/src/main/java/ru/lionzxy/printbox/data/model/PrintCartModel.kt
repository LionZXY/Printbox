package ru.lionzxy.printbox.data.model

data class PrintCartModel(
        var stage: PrintCartStage,
        var printPlace: PrintPlace?) {
    constructor() : this(PrintCartStage.SELECTION_FILE, null)
}