package com.wize.dashobard.ui

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.wize.dashobard.R
import com.wize.dashobard.extensions.color
import com.wize.dashobard.extensions.unsafeLazy

class WizeRefreshLayout : SwipeRefreshLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        setProgressViewEndTarget(false, 0)
        this.setColorSchemeResources(R.color.colorAccent)
        this.setProgressBackgroundColorSchemeColor(context.color(R.color.colorBackground))
    }

    var state: SwipeState = SwipeState.SWIPE

    private val screenHeight: Int by unsafeLazy {
        (context as Activity).screenHeight()
    }
    private val swipeableHeight: Float by unsafeLazy {
        screenHeight * FOR_FOU_ADDITIONAL_SWIPEABLE_SCREEN_PART
    }

    private val refreshFeedOffsetEnd: Int by unsafeLazy {
        resources.getDimensionPixelSize(R.dimen.swipe_refresh_layout_circle_target)
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        if (event != null && event.action == MotionEvent.ACTION_DOWN) {
            val eventY = MotionEvent.obtain(event).y
            state = if (eventY < swipeableHeight) {
                setProgressViewOffset(
                    false,
                    0,
                    refreshFeedOffsetEnd
                )

                SwipeState.SWIPE
            } else {
                setProgressViewEndTarget(false, -screenHeight)
                SwipeState.NONE
            }
        }
        return super.dispatchTouchEvent(event)
    }

    private fun Activity.screenHeight(): Int {
        val metrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(metrics)
        return metrics.heightPixels
    }

    companion object {
        private const val FOR_FOU_ADDITIONAL_SWIPEABLE_SCREEN_PART = 0.3f
    }
}

sealed class SwipeState {
    object SWIPE : SwipeState()
    object NONE : SwipeState()
}