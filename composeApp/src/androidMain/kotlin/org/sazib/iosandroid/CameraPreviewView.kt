package org.sazib.iosandroid

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


@RequiresApi(Build.VERSION_CODES.O)
@Composable
actual fun CameraPreviewView(modifier: Modifier) {
  val context = LocalContext.current
  val lifecycleOwner = LocalLifecycleOwner.current

  val previewView = remember { PreviewView(context) }

  LaunchedEffect(Unit) {
    val cameraProvider = suspendCoroutine<ProcessCameraProvider> { continuation ->
      ProcessCameraProvider.getInstance(context).also { future ->
        future.addListener({
          continuation.resume(future.get())
        }, ContextCompat.getMainExecutor(context))
      }
    }

    val preview = Preview.Builder().build().also {
      it.setSurfaceProvider(previewView.surfaceProvider)
    }

    val cameraSelector = CameraSelector.DEFAULT_FRONT_CAMERA

    try {
      cameraProvider.unbindAll()
      cameraProvider.bindToLifecycle(
        lifecycleOwner,
        cameraSelector,
        preview
      )
    } catch (e: Exception) {
      e.printStackTrace()
    }
  }

  AndroidView(
    factory = { previewView },
    modifier = modifier
  )
}
