package ru.lionzxy.printbox.utils

import ru.lionzxy.printbox.BuildConfig
import java.net.URLEncoder
import java.util.regex.Pattern

object Constants {
    const val VKFLOW_URL = "https://printbox.io/login/vk-oauth2/"
    const val BASE_URL = "https://printbox.io/"
    const val REDIR_URL = "https://printbox.io/complete/vk-oauth2/"
    const val COOKIE_SESSION = "sessionid"
    const val PREFERENCE_FIRSTRUN = "firstrun"
    const val UPLOAD_FILE_URL = "${BuildConfig.API_URL}user_documents/"

    val LOGIN_PATTERN = Pattern.compile("([a-zA-Z0-9]+)")
    const val PASSWORD_MIN_LENGHT = 8
}