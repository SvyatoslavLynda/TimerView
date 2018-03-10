package com.svdroid.timerview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        countDownCheckBox?.setOnCheckedChangeListener { _, b -> timerView?.changeTimerType(b) }
        btnStart?.setOnClickListener { timerView?.start() }
        btnPause?.setOnClickListener { timerView?.pause() }
        btnStop?.setOnClickListener { timerView?.stop() }
    }
}