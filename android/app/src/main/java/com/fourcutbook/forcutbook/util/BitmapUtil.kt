package com.fourcutbook.forcutbook.util

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.nio.file.Files

fun Bitmap.toFile(context: Context): File {
    val storage = context.cacheDir
    val fileName = System.currentTimeMillis().toString() + ".jpg"
    val imgFile = File(storage, fileName)

    runCatching {
        imgFile.createNewFile()
        val out = FileOutputStream(imgFile)

        compress(Bitmap.CompressFormat.JPEG, 10, out)
        out.flush()
        Log.d("woogi", "${Files.size(imgFile.toPath()) / 1024}kb")

        return imgFile
    }.onFailure { e ->
        Log.e("Bitmap converting error: ", "Failed with + \'${e.message}\'")
    }

    return imgFile
}
