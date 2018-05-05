package ru.lionzxy.printbox.view.intro.ui

import ru.lionzxy.printbox.R

/**
 * @author Nikita Kulikov <nikita@kulikof.ru>
 * @project SecretBookNoSecure
 * @date 12.04.18
 */

enum class IntroEnum(val imageRes: Int, val title: Int) {
    FIRST(R.drawable.intro_one, R.string.intro_one),
    TWO(R.drawable.intro_two, R.string.intro_two),
    THREE(R.drawable.intro_three, R.string.intro_three),
    FOUR(R.drawable.intro_four, R.string.intro_four),
    FIVE(R.drawable.intro_five, R.string.intro_five),
    SIX(R.drawable.intro_six, R.string.intro_six)
}