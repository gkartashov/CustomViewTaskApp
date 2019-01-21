package com.jg.shapedrawercustomview.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.RectF

class RoundedSquare(paint: Paint, point: PointF, sideSize: Float, var cornerRadius: Float) :
    Square(paint, point, sideSize) {
    override fun getShapeType() = ShapeType.ROUNDED_SQUARE

    override fun draw(canvas: Canvas) {
        val height = point.y + sideSize
        val width = point.x + sideSize
        canvas.drawRoundRect(RectF(point.x, point.y, width, height), cornerRadius, cornerRadius, paint)
    }
}