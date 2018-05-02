package com.example.linepartsshifterview

/**
 * Created by anweshmishra on 02/05/18.
 */

import android.view.*
import android.content.*
import android.graphics.*
import java.util.concurrent.ConcurrentLinkedQueue

val LINE_PARTS : Int = 6

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

    data class State (var scale : Float = 0f, var dir : Float = 0f, var prevScale : Float = 0f) {

        fun update(stopcb : (Float) -> Unit) {
            scale += 0.1f * dir
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                stopcb(prevScale)
            }
        }

        fun startUpdating(startcb : () -> Unit) {
            if (dir == 0f) {
                dir = 1 - 2 * prevScale
                startcb()
            }
        }
    }

    data class Animator(var view : View, var animated : Boolean = false) {

        fun animate(updatecb : () -> Unit) {
            if (animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                } catch(ex : Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }

    data class LinePart(var i : Int, val state : State = State()) {

        fun draw(canvas: Canvas, paint : Paint) {
            val w : Float = canvas.width.toFloat()
            val h : Float = canvas.height.toFloat()
            val size : Float = h / (3 * LINE_PARTS)
            val ox : Float = 0.1f * w
            val dx : Float = 0.9f * w
            paint.strokeWidth = Math.min(w, h) / 60
            paint.strokeCap = Paint.Cap.ROUND
            paint.color = Color.parseColor("#2980b9")
            canvas.save()
            canvas.translate(ox + (dx - ox) * state.scale, h/10 + i * size)
            canvas.drawLine(0f, 0f, 0f, size, paint)
            canvas.restore()
        }

        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }

        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }

    data class ContainerState(var j : Int = 0, var dir : Int = 1) {

        fun incrementCounter() {
            j += dir
            if (j == LINE_PARTS || j == -1) {
                dir *= -1
            }
        }

        fun execute(cb : (Int) -> Unit) {
            cb(j)
        }
    }
}

fun ConcurrentLinkedQueue<LinePartsShifterView.LinePart>.at(i : Int) : LinePartsShifterView.LinePart? {
    var j : Int = 0
    forEach {
        if (i == j) {
            return it
        }
        j++
    }
    return null
}