package ru.lionzxy.printbox.utils

import android.content.Intent

interface IActivityResultReciever {
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}