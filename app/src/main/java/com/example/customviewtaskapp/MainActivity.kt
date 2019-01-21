package com.example.customviewtaskapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //shape_drawer.setColorsFromIntArray(arrayOf(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark))

        //shape_drawer.setColorsFromHexArray(arrayOf(0xFF0000, 0x00FF00, 0x0000FF))
    }
}
