package com.svdroid.timerview.view.utils

import com.svdroid.timerview.view.ITimerTickListener

/**
 * Created by SVDroid on 3/10/18.
 */
data class TimerTask(
        private val listener: ITimerTickListener?,
        private val time: Long,
        private val tick: Long,
        private val isCountDown: Boolean,
        private var currentTime: Long = if (isCountDown) time else 0
) : java.util.TimerTask() {
    override fun run() {
        currentTime = if (isCountDown) currentTime - tick else currentTime + tick
        listener?.updateTime(currentTime)
    }
}