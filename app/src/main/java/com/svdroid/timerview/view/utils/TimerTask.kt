package com.svdroid.timerview.view.utils

import com.svdroid.timerview.view.ITimerTickListener

/**
 * Created by SVDroid on 3/10/18.
 */
data class TimerTask(
        private val listener: ITimerTickListener?,
        private var time: Long,
        private val tick: Long,
        private val isCountDown: Boolean
) : java.util.TimerTask() {
    override fun run() {
        time -= tick
        listener?.updateTime(time)
    }
}