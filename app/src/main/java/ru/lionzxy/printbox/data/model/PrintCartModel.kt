package ru.lionzxy.printbox.data.model

data class PrintCartModel(
        var stage: PrintCartStage,
        var printPlace: PrintPlace?,
        var printDocument: PrintDocument?,
        var printOption: PrintOption?) {
    constructor() : this(PrintCartStage.SELECTION_FILE, null, null,
            null)
}