package com.jg.shapedrawercustomview.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF

open class Square(paint: Paint, point: PointF, val sideSize: Float) : Shape(paint, point) {
    override fun getShapeType() = ShapeType.SQUARE

    override fun draw(canvas: Canvas) {
        val height = point.y + sideSize
        val width = point.x + sideSize
        canvas.drawRect(RectF(point.x, point.y, width, height), paint)
    }
}