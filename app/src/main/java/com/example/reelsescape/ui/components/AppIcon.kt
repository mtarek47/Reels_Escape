package com.example.reelsescape.ui.components

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun getAppIconPainter(packageName: String): Painter? {
    val context = LocalContext.current
    return remember(packageName) {
        try {
            val pm = context.packageManager
            val drawable = pm.getApplicationIcon(packageName)
            val bitmap = drawableToBitmap(drawable)
            BitmapPainter(bitmap.asImageBitmap())
        } catch (e: Exception) {
            null
        }
    }
}

fun drawableToBitmap(drawable: Drawable): Bitmap {
    if (drawable is BitmapDrawable) {
        if (drawable.bitmap != null) {
            return drawable.bitmap
        }
    }

    val width = if (drawable.intrinsicWidth <= 0) 100 else drawable.intrinsicWidth
    val height = if (drawable.intrinsicHeight <= 0) 100 else drawable.intrinsicHeight

    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}

@Composable
fun AppIcon(
    packageName: String,
    fallbackColor: Color,
    modifier: Modifier = Modifier,
    iconSize: Int = 44,
    innerFallbackSize: Int = 18
) {
    val painter = getAppIconPainter(packageName)
    if (painter != null) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = modifier
                .size(iconSize.dp)
                .clip(CircleShape)
        )
    } else {
        // Fallback to stylized circle placeholder
        Box(
            modifier = modifier
                .size(iconSize.dp)
                .clip(CircleShape)
                .background(fallbackColor.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(innerFallbackSize.dp)
                    .clip(CircleShape)
                    .background(fallbackColor)
            )
        }
    }
}
