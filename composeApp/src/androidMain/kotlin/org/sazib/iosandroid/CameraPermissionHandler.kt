package org.sazib.iosandroid

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

actual class CameraPermissionHandler(
  private val context: android.content.Context
) {
  actual suspend fun requestCameraPermission(): Boolean {
    return ContextCompat.checkSelfPermission(
      context,
      Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
  }

  actual fun hasCameraPermission(): Boolean {
    return ContextCompat.checkSelfPermission(
      context,
      Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
  }
}

@Composable
fun rememberCameraPermissionHandler(): CameraPermissionHandler {
  val context = LocalContext.current
  return remember { CameraPermissionHandler(context) }
}