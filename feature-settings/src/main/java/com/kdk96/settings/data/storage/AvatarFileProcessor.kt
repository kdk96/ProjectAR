package com.kdk96.settings.data.storage

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AvatarFileProcessor(private val context: Context) {
    companion object {
        const val MAX_WIDTH = 512f
    }

    var imagePath: String? = null

    private fun createPhotoFile(): File {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val photoFileName = "IMG_$timeStamp"
        val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val appPicturesDir = File(picturesDir, "ProjectAR")
        if (!appPicturesDir.exists())
            appPicturesDir.mkdirs()
        return File(
            appPicturesDir,
            "$photoFileName.jpg"
        ).apply { imagePath = absolutePath }
    }

    fun getTakePhotoIntent(): Intent? = Intent(MediaStore.ACTION_IMAGE_CAPTURE).let {
        if (it.resolveActivity(context.packageManager) != null) {
            val photoFile: File
            try {
                photoFile = createPhotoFile()
            } catch (exception: IOException) {
                return@let null
            }
            val photoUri = FileProvider.getUriForFile(context, "com.kdk96.projectar.fileprovider", photoFile)
            it.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        } else null
    }

    fun getImagePathFromUri(uri: Uri): String {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor = context.contentResolver.query(uri, projection, null, null, null)!!
        return cursor.use {
            it.moveToFirst()
            val columnIndex = cursor.getColumnIndex(projection[0])
            it.getString(columnIndex)
        }
    }

    fun deletePhotoFile() {
        File(imagePath!!).delete()
        imagePath = null
    }

    fun getCompressedImageFile(path: String): File {
        val originalFile = File(path)
        var bitmap = BitmapFactory.decodeFile(path)
        if (bitmap.width > MAX_WIDTH) {
            val scaleFactor = MAX_WIDTH / bitmap.width
            bitmap = Bitmap.createScaledBitmap(bitmap, MAX_WIDTH.toInt(), (bitmap.height * scaleFactor).toInt(), true)
        }
        if (originalFile.extension.toLowerCase() == "jpg") {
            try {
                val exif = ExifInterface(path)
                val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
                if (orientation != ExifInterface.ORIENTATION_NORMAL) {
                    bitmap = Bitmap.createBitmap(
                        bitmap,
                        0,
                        0,
                        bitmap.width,
                        bitmap.height,
                        getRotationMatrix(orientation),
                        false
                    )
                }
            } catch (exception: IOException) {
            }
        }
        val outFile = File.createTempFile(originalFile.nameWithoutExtension, ".jpg", context.cacheDir)
        FileOutputStream(outFile).use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, it)
            it.flush()
        }
        return outFile
    }

    private fun getRotationMatrix(orientation: Int): Matrix {
        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
        }
        return matrix
    }
}