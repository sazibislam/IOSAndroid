
// commonMain/CameraPermissionHandler.kt
expect class CameraPermissionHandler {
  suspend fun requestCameraPermission(): Boolean
  fun hasCameraPermission(): Boolean
}