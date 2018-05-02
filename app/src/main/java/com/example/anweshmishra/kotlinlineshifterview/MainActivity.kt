package com.example.anweshmishra.kotlinlineshifterview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.linepartsshifterview.LinePartsShifterView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LinePartsShifterView.create(this)
    }
}
