package ru.lionzxy.printbox.data.model

class PrintFinalOrder(
        var copies: Int,
        var colorOption: PrintOption?,
        var duplexOption: PrintOption?) {
    constructor() : this(1, null, null)
}