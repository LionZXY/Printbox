package ru.lionzxy.printbox.utils

import ru.lionzxy.printbox.BuildConfig
import java.net.URLEncoder
import java.util.regex.Pattern

object Constants {
    const val VKFLOW_URL = "https://printbox.io/login/vk-oauth2/"
    const val REDIR_URL = "https://printbox.io/complete/vk-oauth2/"
    const val PREFERENCE_FIRSTRUN = "firstrun"

    val LOGIN_PATTERN = Pattern.compile("([a-zA-Z0-9]+)")
    const val PASSWORD_MIN_LENGHT = 8
}