package com.jg.shapedrawercustomview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.shape_drawer_layout.view.*
import kotlin.random.Random

class ShapeDrawerView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {

    private val DEFAULT_COLOR = Color.GREEN
    private val MAX_SHAPE_COUNT = 10
    private val MIN_SHAPE_SIZE = 20
    private val MAX_SHAPE_SIZE = 50

    private val drawPoint = PointF()
    private var shapeType = ShapeType.CIRCLE

    var colors: Array<Int>? = null
    var defaultColor = DEFAULT_COLOR
    var shapeCounter = 0
    var maxShapeCount = MAX_SHAPE_COUNT

    init {
        View.inflate(context, R.layout.shape_drawer_layout, this)
        applyAttributes(attributeSet)
    }

    fun setColorsFromHexArray(colors: Array<Int>) {
        this.colors = colors.map(this::fromHexColorToIntColor).toTypedArray()
    }

    private fun fromResourceColorToHexColor(color: Int) = ContextCompat.getColor(context, color)

    private fun fromHexColorToIntColor(color: Int): Int {
        val hexString = String.format("#%06X", (0xFFFFFF and color))
        return Color.parseColor(hexString)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        /*val colorRaw = Color.RED
        val colorInt = ContextCompat.getColor(context, android.R.color.holo_red_light)
        val colorRes = android.R.color.holo_red_light
        val colorRawHex = String.format("#%06X", (0xFFFFFF and Color.RED))
        val colorIntHex = String.format("#%06X", (0xFFFFFF and colorInt))
        val colorResHex = String.format("#%06X", (0xFFFFFF and android.R.color.holo_red_light))
        shape_drawer_counter_tv.text = "colorRaw: $colorRaw\ncolorInt: $colorInt\ncolorRes: $colorRes\ncolorRawHex: $colorRawHex\ncolorIntHex: $colorIntHex\n" +
                "colorResHex: $colorResHex"*/
        shape_drawer_counter_tv.text = shapeCounter.toString()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            drawPoint.x = event.x
            drawPoint.y = event.y

            shapeCounter++
            if (shapeCounter == maxShapeCount) {
                shapeCounter = 0
                showToast()
            }

            shape_drawer_counter_tv.text = shapeCounter.toString()
            shapeType = ShapeType.getRandomShapeType()
            invalidate()
            return true
        } else {
            return super.onTouchEvent(event)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.let {
            when (shapeType) {
                ShapeType.CIRCLE -> drawCircle(canvas)
                else -> {
                }
            }
        }
    }

    private fun drawCircle(canvas: Canvas) {
        val radius = Random.nextInt(MIN_SHAPE_SIZE, MAX_SHAPE_SIZE).toFloat()
        val paint = getPaint()
        canvas.drawCircle(drawPoint.x, drawPoint.y, radius, paint)
    }

    private fun getPaint() = Paint().apply {
        color = colors?.random() ?: defaultColor
    }

    private fun applyAttributes(attributeSet: AttributeSet?) {
        val ta = context.theme.obtainStyledAttributes(attributeSet, R.styleable.ShapeDrawerView, 0, 0)
        defaultColor = ta.getColor(R.styleable.ShapeDrawerView_defaultColor, DEFAULT_COLOR)
        maxShapeCount = ta.getInteger(R.styleable.ShapeDrawerView_maxShapeCount, MAX_SHAPE_COUNT)
        ta.recycle()
    }

    private fun showToast() =
        Toast.makeText(context, resources.getString(R.string.game_over), Toast.LENGTH_SHORT).show()

    private enum class ShapeType {
        CIRCLE,
        ROUNDED_SQUARE,
        SQUARE;

        companion object {
            private val random = Random(System.currentTimeMillis())
            private val size = values().size
            private val values = values()

            fun getRandomShapeType() = values[random.nextInt(size)]
        }
    }
}