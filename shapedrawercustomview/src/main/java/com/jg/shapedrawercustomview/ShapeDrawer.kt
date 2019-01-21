package com.jg.shapedrawercustomview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.shape_drawer_layout.view.*

/**
 * Контейнер, содержащий [ShapeDrawerView] и счетчик фигур.
 * В XML можно задать максимальное количество фигур на экране и стандартный цвет фигуры (передается в [ShapeDrawerView])
 */
class ShapeDrawer @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : FrameLayout(context, attributeSet) {

    private val MAX_SHAPE_COUNT = 10

    private var color = Color.GREEN
    var maxShapeCount = MAX_SHAPE_COUNT
    var shapeCounter = 0

    private lateinit var shapeDrawerView: ShapeDrawerView
    private lateinit var counterTextView: TextView

    init {
        View.inflate(context, R.layout.shape_drawer_layout, this)
        applyAttributes(attributeSet)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        counterTextView = shape_drawer_counter_tv
        counterTextView.text = shapeCounter.toString()

        shapeDrawerView = shape_drawer_view
        shapeDrawerView.onDrawShapeLambda = this::handleDraw
        shapeDrawerView.defaultColor = color
    }

    private fun handleDraw() {
        shapeCounter++
        if (shapeCounter >= maxShapeCount) {
            shapeCounter = 0
            shapeDrawerView.clearCanvas()
            showToast()
        }
        counterTextView.text = shapeCounter.toString()
    }

    fun setColorsFromResArray(colors: Array<Int>) {
        shapeDrawerView.setColorsFromResArray(colors)
    }

    fun setColorsFromHexArray(colors: Array<Int>) {
        shapeDrawerView.setColorsFromHexArray(colors)
    }

    private fun applyAttributes(attributeSet: AttributeSet?) {
        val ta = context.theme.obtainStyledAttributes(attributeSet, R.styleable.ShapeDrawer, 0, 0)
        maxShapeCount = ta.getInt(R.styleable.ShapeDrawer_maxItemCount, MAX_SHAPE_COUNT)
        color = ta.getColor(R.styleable.ShapeDrawer_color, Color.GREEN)
        ta.recycle()
    }

    private fun showToast() =
        Toast.makeText(context, resources.getString(R.string.game_over), Toast.LENGTH_SHORT).show()
}