package com.jg.shapedrawercustomview.shape

import kotlin.random.Random

enum class ShapeType {
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