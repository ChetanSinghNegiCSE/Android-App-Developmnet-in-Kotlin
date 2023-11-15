package com.example.pdfbyandroid

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.View

class CustomTableView(context: Context) : View(context) {
    private val paint: Paint = Paint()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw the table on the canvas
        val tableWidth = width.toFloat()
        val tableHeight = height.toFloat()

        val rows = 12
        val columns = 4

        val cellWidth = tableWidth / columns
        val cellHeight = tableHeight / rows

        for (i in 0 until columns) {
            val x = i * cellWidth
            canvas.drawLine(x, 0F, x, tableHeight, paint)
        }


        for (i in 0 until rows) {
            val y = i * cellHeight
            canvas.drawLine(0F, y, tableWidth, y, paint)
        }
    }
}
