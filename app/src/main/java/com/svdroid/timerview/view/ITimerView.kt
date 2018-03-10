package com.svdroid.timerview.view

/**
 * Created by SVDroid on 3/9/18.
 */
interface ITimerView : ITimerTickListener {
    fun start()
    fun pause()
    fun stop()
    fun changeTimerType(isCountDown: Boolean)
}