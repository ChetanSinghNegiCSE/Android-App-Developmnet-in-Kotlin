package com.example.pdfbyandroid

import android.content.pm.PackageManager
import android.graphics.pdf.PdfDocument
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.PowerManager
import android.provider.DocumentsContract.Document
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val customView = CustomTableView(this)
        // Generate PDF
        generatePDF(customView)
    }

    private fun generatePDF(view: View) {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(500, 500, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        // Calculate the width and height of the view
        val widthSpec = View.MeasureSpec.makeMeasureSpec(canvas.width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(canvas.height, View.MeasureSpec.EXACTLY)
        view.measure(widthSpec, heightSpec)
        view.layout(0, 0, canvas.width, canvas.height)

        // Draw the view on the PDF canvas
        view.draw(canvas)

        pdfDocument.finishPage(page)

        val directory = File(
            this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
            "PDFFolder"
        )
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val filePath = File(directory, "sample.pdf")

        // Save the PDF
        try {
            val fileOutputStream = FileOutputStream(filePath)
            pdfDocument.writeTo(fileOutputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        pdfDocument.close()
        Toast.makeText(this, "PDF created", Toast.LENGTH_SHORT).show()
    }
}