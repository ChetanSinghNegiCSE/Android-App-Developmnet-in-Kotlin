package com.example.pdfbyandroid

import android.content.Intent
import android.graphics.pdf.PdfDocument
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.io.File
import java.io.FileOutputStream

class NewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)

        val editText: EditText = findViewById(R.id.editText)
        val generatePdfButton: Button = findViewById(R.id.generatePdfButton)
        val tableButton = findViewById<Button>(R.id.generateTable)

        generatePdfButton.setOnClickListener {
            val text = editText.text.toString()
            if (text.isNotEmpty()) {
                generatePdf(text)
            } else {
                Toast.makeText(this, "Enter text to generate PDF", Toast.LENGTH_SHORT).show()
            }
        }

        tableButton.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java )
                startActivity(intent)
        }
    }

    private fun generatePdf(content: String) {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(300, 600, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        // Customize text appearance and positioning
        val paint = android.graphics.Paint()
        paint.textSize = 12f
        canvas.drawText(content, 10f, 25f, paint)

        pdfDocument.finishPage(page)

        val directory = File(
            this.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
            "PDFFolder"
        )
        if (!directory.exists()) {
            directory.mkdirs()
        }

        val filePath = File(directory, "text.pdf")
/*
        val pdfFile = File(directory, "Generated_PDF.pdf")
*/

        try {
            val fileOutputStream = FileOutputStream(filePath)
            pdfDocument.writeTo(fileOutputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        pdfDocument.close()

        Toast.makeText(this, "PDF generated${filePath.absolutePath}", Toast.LENGTH_LONG).show()
    }
}