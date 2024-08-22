package com.fourcutbook.forcutbook.util

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Base64
import android.util.Log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

fun getKeyHashBase64(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, PackageManager.GET_SIGNING_CERTIFICATES)
        for (signature in packageInfo.signingInfo.apkContentsSigners) {
            try {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("getKeyHash", "key hash: ${Base64.encodeToString(md.digest(), Base64.NO_WRAP)}")
            } catch (e: NoSuchAlgorithmException) {
                Log.w("getKeyHash", "Unable to get MessageDigest. signature=$signature", e)
            }
        }
    }
}
