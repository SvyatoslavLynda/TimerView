package com.svdroid.timerview.view.utils

/**
 * Created by SVDroid on 3/10/18.
 */
data class TimerData(
        val time: Long,
        var currentTime: Long = time,
        var tickPeriod: Long = 25L,
        val sweepAngel: Float,
        var currentSweepAngel: Float = sweepAngel,
        var isCountDown: Boolean = true,
        var timerPatternWithSeparator: String,
        var timerPatternWithoutSeparator: String
)