package com.svdroid.timerview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import com.svdroid.timerview.R
import com.svdroid.timerview.view.utils.TimerData
import com.svdroid.timerview.view.utils.TimerTask
import java.util.*

/**
 * Created by SVDroid on 3/9/18.
 */
class TimerView : View, ITimerView {
    private val width = context?.resources?.getDimensionPixelSize(R.dimen.timer_default_width)
    private val height = context?.resources?.getDimensionPixelSize(R.dimen.timer_default_height)
    private val padding = context?.resources?.getDimensionPixelSize(R.dimen.timer_default_padding)
    private var timerTextSize = context?.resources?.getDimensionPixelSize(R.dimen.timer_default_text_size)
    private var timerTextPaint = Paint()
    private var bgTimerRoundPaint = Paint()
    private var fgTimerRoundPaint = Paint()
    private var arcRect: RectF? = null
    private var timer: Timer? = null
    private var timerData: TimerData? = null

    constructor(context: Context) : this(context, null) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(width!!, height!!)
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawText(formattedTime(timerData!!.currentTime), (width!! / 2).toFloat(), (height!! / 2 + timerTextSize!! / 3).toFloat(), timerTextPaint)
        canvas?.drawCircle((width!! / 2).toFloat(), (height!! / 2).toFloat(), ((width / 2) - padding!!).toFloat(), bgTimerRoundPaint)
        canvas?.drawArc(arcRect, 270f, timerData!!.currentSweepAngel, false, fgTimerRoundPaint)
    }

    override fun updateTime(time: Long) {
        timerData?.currentSweepAngel = timerData!!.sweepAngel * time / timerData!!.time
        timerData!!.currentTime = time

        if (timerData!!.currentSweepAngel in 0f..360f) {
            invalidate()

            return
        }

        cancel()
    }

    override fun start() {
        cancel()
        timer = Timer()
        timer?.schedule(
                TimerTask(this, timerData!!.currentTime, timerData!!.tickPeriod, timerData!!.isCountDown),
                0,
                timerData!!.tickPeriod
        )
    }

    override fun pause() {
        cancel()
    }

    override fun stop() {
        timerData!!.currentTime = timerData!!.time
        timerData!!.currentSweepAngel = timerData!!.sweepAngel
        cancel()
        invalidate()
    }

    private fun init(context: Context, attrs: AttributeSet? = null) {
        timerTextSize = context.resources?.getDimensionPixelSize(R.dimen.timer_default_text_size) ?: 32
        timerTextPaint.textSize = timerTextSize!!.toFloat()
        timerTextPaint.color = ContextCompat.getColor(context, R.color.colorPrimary)
        timerTextPaint.textAlign = Paint.Align.CENTER
        timerTextPaint.isLinearText = true
        bgTimerRoundPaint.strokeWidth = 6f
        bgTimerRoundPaint.style = Paint.Style.STROKE
        bgTimerRoundPaint.color = ContextCompat.getColor(context, R.color.colorPrimary)
        fgTimerRoundPaint.strokeWidth = 6f
        fgTimerRoundPaint.style = Paint.Style.STROKE
        fgTimerRoundPaint.color = ContextCompat.getColor(context, R.color.colorAccent)
        arcRect = RectF(padding!!.toFloat(), padding.toFloat(), width!!.toFloat() - padding, height!!.toFloat() - padding)
        timerData = TimerData(
                30L * 1000L,
                360f,
                25L,
                true,
                context.getString(R.string.default_pattern_time_with_dot),
                context.getString(R.string.default_pattern_time_without_dot)
        )
    }

    private fun cancel() {
        timer?.purge()
        timer?.cancel()
        timer = null
    }

    private fun formattedTime(timeML: Long): String {
        val m = timeML / (60L * 1000L)
        val s = (timeML - (m * 60L * 1000L)) / 1000L

        return String.format(
                Locale.getDefault(),
                if (((timerData!!.time - timeML) % 1000) > 500) timerData!!.timerPatternWithoutSeparator else timerData!!.timerPatternWithSeparator,
                m,
                s
        )
    }
}