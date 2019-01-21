package com.jg.shapedrawercustomview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.content.ContextCompat
import com.jg.shapedrawercustomview.shape.*
import kotlin.random.Random
import android.util.TypedValue

/**
 * View, которая по нажатию на нее рисует одну из следующих фигур
 * - круг
 * - квадрат
 * - квадрат со скругленными углами
 * В xml можно задать стандартный цвет фигуры.
 * Программно можно задать массив цветов, из которых будет случайным образом выбираться цвет фигуры
 */
class ShapeDrawerView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : View(context, attributeSet) {

    private val DEFAULT_COLOR = Color.GREEN
    private val MIN_SHAPE_SIZE_DP: Float
    private val MAX_SHAPE_SIZE_DP: Float
    private val MIN_RADIUS_SIZE_DP: Float
    private val MAX_RADIUS_SIZE_DP: Float

    var defaultColor = DEFAULT_COLOR
    var onDrawShapeLambda: (() -> Unit)? = null

    private var shapeList = mutableListOf<Shape>()
    private var colors: Array<Int>? = null

    init {
        MIN_SHAPE_SIZE_DP = dipToPixels(context, 40f)
        MAX_SHAPE_SIZE_DP = dipToPixels(context, 80f)
        MIN_RADIUS_SIZE_DP = dipToPixels(context, 16f)
        MAX_RADIUS_SIZE_DP = dipToPixels(context, 24f)
        applyAttributes(attributeSet)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (event?.action == MotionEvent.ACTION_DOWN) {
            val shape = getRandomShape(PointF(event.x, event.y))
            shapeList.add(shape)

            invalidate()
            onDrawShapeLambda?.invoke()
            true
        } else {
            super.onTouchEvent(event)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            for (shape in shapeList) {
                shape.draw(canvas)
            }
        }
    }

    private fun dipToPixels(context: Context, dipValue: Float): Float {
        val metrics = context.resources.displayMetrics
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics)
    }

    private fun applyAttributes(attributeSet: AttributeSet?) {
        val ta = context.theme.obtainStyledAttributes(attributeSet, R.styleable.ShapeDrawerView, 0, 0)
        defaultColor = ta.getColor(R.styleable.ShapeDrawerView_defaultColor, DEFAULT_COLOR)
        ta.recycle()
    }

    /**
     * Получение массива цветов из массива вида { 0x000000, ... }
     */
    fun setColorsFromHexArray(colors: Array<Int>) {
        this.colors = colors.map(this::fromHexColorToIntColor).toTypedArray()
    }

    /**
     * Получение массива цветов из массива вида { R.color.color, .... }
     */
    fun setColorsFromResArray(colors: Array<Int>) {
        this.colors = colors.map(this::fromResourceColorToIntColor).toTypedArray()
    }

    fun clearCanvas() {
        shapeList.clear()
        invalidate()
    }

    private fun fromResourceColorToIntColor(color: Int) = ContextCompat.getColor(context, color)

    private fun fromHexColorToIntColor(color: Int): Int {
        val hexString = String.format("#%06X", (0xFFFFFF and color))
        return Color.parseColor(hexString)
    }

    private fun getRandomShape(point: PointF): Shape {
        val shapeType = ShapeType.getRandomShapeType()
        val paint = getPaint()
        return when (shapeType) {
            ShapeType.CIRCLE -> {
                val radius = Random.nextInt(MIN_RADIUS_SIZE_DP.toInt(), MAX_RADIUS_SIZE_DP.toInt()).toFloat()
                Circle(paint, point, radius)
            }
            ShapeType.ROUNDED_SQUARE -> {
                val sideSize = Random.nextInt(MIN_SHAPE_SIZE_DP.toInt(), MAX_SHAPE_SIZE_DP.toInt()).toFloat()
                val centeredPoint = moveDrawPointToCenter(point, sideSize)
                val radius = Random.nextInt(MIN_RADIUS_SIZE_DP.toInt(), MAX_RADIUS_SIZE_DP.toInt()).toFloat()
                RoundedSquare(paint, centeredPoint, sideSize, radius)
            }
            ShapeType.SQUARE -> {
                val sideSize = Random.nextInt(MIN_SHAPE_SIZE_DP.toInt(), MAX_SHAPE_SIZE_DP.toInt()).toFloat()
                val centeredPoint = moveDrawPointToCenter(point, sideSize)
                Square(paint, centeredPoint, sideSize)
            }
        }
    }

    private fun getPaint() = Paint().apply {
        color = colors?.random() ?: defaultColor
    }

    private fun moveDrawPointToCenter(point: PointF, sideSize: Float): PointF {
        point.x -= sideSize / 2.0f
        point.y -= sideSize / 2.0f
        return point
    }
}