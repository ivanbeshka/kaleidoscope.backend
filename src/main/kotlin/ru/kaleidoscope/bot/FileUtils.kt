package ru.kaleidoscope.bot

import org.apache.poi.xwpf.usermodel.ParagraphAlignment
import org.apache.poi.xwpf.usermodel.XWPFDocument
import ru.kaleidoscope.db.models.CodeDB
import ru.kaleidoscope.utils.CODES_FONT_SIZE
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

fun writeCodesToFile(codes: List<CodeDB>): File {
    val wordDoc = createCodesDocument(codes)

    val wordFile = File("codes.docx")
    val fileOut = convertToFileOutputStream(wordFile)

    writeToFile(wordDoc, fileOut)

    return wordFile
}

fun writeToFile(wordDoc: XWPFDocument, fileOut: FileOutputStream?) {
    try {
        wordDoc.write(fileOut)
    } catch (e: IOException) {
        e.printStackTrace()
    } finally {
        fileOut?.close()
    }
}

fun convertToFileOutputStream(wordFile: File): FileOutputStream? {
    val fileOut = try {
        FileOutputStream(wordFile)
    } catch (e: FileNotFoundException) {
        e.printStackTrace()
        null
    }
    return fileOut
}

fun createCodesDocument(codes: List<CodeDB>): XWPFDocument =
    XWPFDocument().apply {
        createParagraph().apply {
            alignment = ParagraphAlignment.LEFT

            createRun().apply {
                fontSize = CODES_FONT_SIZE
                setText(codes.joinToString(" ") { it.code })
            }
        }
    }