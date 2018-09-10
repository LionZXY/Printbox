package ru.lionzxy.printbox.data.model

enum class PrintHistoryStageEnum(val id: Int) {
    FAILED(1004),
    DONE(1003),
    IN_QUERY(1000), // В очереди
    HANDLING(1001), // Обрабатывается
    PRINTING(1002) // Печатается
}