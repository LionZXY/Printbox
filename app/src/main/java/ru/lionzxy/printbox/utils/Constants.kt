package ru.lionzxy.printbox.utils

import ru.lionzxy.printbox.BuildConfig
import java.net.URLEncoder
import java.util.regex.Pattern

object Constants {
    val VKFLOW_URL = "https://oauth.vk.com/authorize?scope=email&client_id=5571444&" +
            "redirect_uri=${URLEncoder.encode(BuildConfig.API_URL, "UTF-8")}auth&response_type=code"
    const val PREFERENCE_SESSIONID = "sessionid"
    const val PREFERENCE_FIRSTRUN = "firstrun"
    const val COOKIE_SESSIONID = "sessionid"
    const val HEADER_COOKIE = "Cookie"
    const val HEADER_SETCOOKIE = "Set-Cookie"

    val LOGIN_PATTERN = Pattern.compile("([a-zA-Z0-9]+)")
    const val PASSWORD_MIN_LENGHT = 4
}