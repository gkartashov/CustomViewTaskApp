package com.jg.shapedrawercustomview.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

class Circle(paint: Paint, point: PointF, var radius: Float = 0f) : Shape(paint, point) {

    override fun getShapeType() = ShapeType.CIRCLE

    override fun draw(canvas: Canvas) {
        canvas.drawCircle(point.x, point.y, radius, paint)
    }
}