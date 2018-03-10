package com.svdroid.timerview.view.utils

/**
 * Created by SVDroid on 3/10/18.
 */
data class TimerData(
        val time: Long,
        val sweepAngel: Float,
        var tickPeriod: Long = 25L,
        var isCountDown: Boolean = true,
        var timerPatternWithSeparator: String,
        var timerPatternWithoutSeparator: String,
        var currentSweepAngel: Float = if (isCountDown) sweepAngel else 0f,
        var currentTime: Long = if (isCountDown) time else 0L
) {
    fun changeCountType(isCountDown: Boolean) {
        this.isCountDown = isCountDown
        resetData()
    }

    fun resetData() {
        currentSweepAngel = if (isCountDown) sweepAngel else 0f
        currentTime = if (isCountDown) time else 0L
    }
}