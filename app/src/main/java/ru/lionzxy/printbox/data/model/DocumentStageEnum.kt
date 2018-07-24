package ru.lionzxy.printbox.data.model

enum class DocumentStageEnum(val id: Int) {
    PROCESSING(0),
    UPLOADING(-1),
    READY(1)
}