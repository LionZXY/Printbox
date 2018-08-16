package ru.lionzxy.printbox.utils

import ru.lionzxy.printbox.BuildConfig
import java.net.URLEncoder
import java.util.regex.Pattern

object Constants {
    const val BASE_URL = "https://printbox.io/"
    const val VKFLOW_URL = "${BASE_URL}login/vk-oauth2/"
    const val REDIR_URL = "${BASE_URL}complete/vk-oauth2/"
    const val PAY_FINISH_URL = "${BASE_URL}robokassa/"
    const val PAY_FINISH_URL_ALTERNATIVE = "http://fopf-print.ru/robokassa/"
    const val COOKIE_SESSION = "sessionid"
    const val BACKPRESS_TIMEOUT = 3000L
    const val PREFERENCE_FIRSTRUN = "firstrun"
    const val UPLOAD_FILE_URL = "${BuildConfig.API_URL}user_documents/upload_files/"

    val LOGIN_PATTERN = Pattern.compile("([a-zA-Z0-9@.]+)")
    const val PASSWORD_MIN_LENGHT = 8
}