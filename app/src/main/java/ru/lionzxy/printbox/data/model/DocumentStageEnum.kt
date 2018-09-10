package ru.lionzxy.printbox.data.model

enum class DocumentStageEnum(val id: Int) {
    PROCESSING(2),
    UPLOADING(-1),
    READY(1)
}