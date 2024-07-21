package com.fourcutbook.forcutbook.util

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

fun Bitmap.toFile(path: String): File {
    var file = File(path)
    var out: OutputStream? = null
    try {
        file.createNewFile()
        out = FileOutputStream(file)
        compress(Bitmap.CompressFormat.JPEG, 80, out)
    } finally {
        out?.close()
    }
    return file
}

fun saveBitmapToJpeg(bitmap: Bitmap, context: Context): File? {
    // 내부저장소 캐시 경로를 받아옵니다.
    val storage = context.cacheDir

    // 저장할 파일 이름
    val fileName = System.currentTimeMillis().toString() + ".jpg"

    // storage 에 파일 인스턴스를 생성합니다.
    val imgFile = File(storage, fileName)
    try {
        // 자동으로 빈 파일을 생성합니다.
        imgFile.createNewFile()

        // 파일을 쓸 수 있는 스트림을 준비합니다.
        val out = FileOutputStream(imgFile)

        // compress 함수를 사용해 스트림에 비트맵을 저장합니다.
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)

        // 스트림 사용후 닫아줍니다.
        out.close()
        return imgFile
    } catch (e: FileNotFoundException) {
        Log.e("MyTag", "FileNotFoundException : " + e.message)
    } catch (e: IOException) {
        Log.e("MyTag", "IOException : " + e.message)
    }
    return imgFile
}
