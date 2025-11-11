package org.sazib.iosandroid
import platform.AVFoundation.AVAuthorizationStatusAuthorized
import platform.AVFoundation.AVAuthorizationStatusNotDetermined
import platform.AVFoundation.AVCaptureDevice
import platform.AVFoundation.AVMediaTypeVideo
import platform.AVFoundation.authorizationStatusForMediaType
import platform.AVFoundation.requestAccessForMediaType
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual class CameraPermissionHandler {
  actual suspend fun requestCameraPermission(): Boolean = suspendCoroutine { continuation ->
    val status = AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo)

    when (status) {
      AVAuthorizationStatusAuthorized -> {
        continuation.resume(true)
      }
      AVAuthorizationStatusNotDetermined -> {
        AVCaptureDevice.requestAccessForMediaType(AVMediaTypeVideo) { granted ->
          continuation.resume(granted)
        }
      }
      else -> {
        continuation.resume(false)
      }
    }
  }

  actual fun hasCameraPermission(): Boolean {
    return AVCaptureDevice.authorizationStatusForMediaType(AVMediaTypeVideo) ==
      AVAuthorizationStatusAuthorized
  }
}