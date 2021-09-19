package info.nightscout.androidaps.utils

@Suppress("SpellCheckingInspection")
class T(val time: Long = 0L) {

    fun msecs(): Long = time
    fun secs(): Long = time / 1000L
    fun mins(): Long = time / 60 / 1000L
    fun hours(): Long = time / 60 / 60 / 1000L
    fun days(): Long = time / 24 / 60 / 60 / 1000L

    operator fun plus(plus: T): T = T(time + plus.time)
    operator fun minus(minus: T): T = T(time - minus.time)

    companion object {

        @JvmStatic fun now(): T = T(System.currentTimeMillis())
        @JvmStatic fun msecs(msec: Long): T = T(msec)
        @JvmStatic fun secs(sec: Long): T = T(sec * 1000L)
        @JvmStatic fun mins(min: Long): T = T(min * 60 * 1000L)
        @JvmStatic fun hours(hour: Long): T = T(hour * 60 * 60 * 1000L)
        @JvmStatic fun days(day: Long): T = T(day * 24 * 60 * 60 * 1000L)
        @JvmStatic fun months(month: Long): T = T(month * 31 * 24 * 60 * 60 * 1000L)
    }
}