package com.example.linepartsshifterview

/**
 * Created by anweshmishra on 02/05/18.
 */

import android.view.*
import android.content.*
import android.graphics.*

class LinePartsShifterView(ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}