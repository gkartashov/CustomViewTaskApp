package com.jg.shapedrawercustomview.shape

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF

abstract class Shape(var paint: Paint, var point: PointF) {

    abstract fun getShapeType(): ShapeType

    abstract fun draw(canvas: Canvas)
}