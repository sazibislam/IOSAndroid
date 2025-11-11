package org.sazib.iosandroid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import platform.AVFoundation.*
import platform.QuartzCore.CATransaction
import platform.QuartzCore.kCATransactionDisableActions
import platform.UIKit.UIView

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun CameraPreviewView(modifier: Modifier) {
  val captureSession = remember { AVCaptureSession() }

  UIKitView(
    factory = {
      val previewLayer = AVCaptureVideoPreviewLayer(session = captureSession)
      val containerView = UIView()

      containerView.layer.addSublayer(previewLayer)

      // Setup camera
      captureSession.beginConfiguration()

      val device = AVCaptureDevice.defaultDeviceWithMediaType(AVMediaTypeVideo)
      device?.let {
        val input = AVCaptureDeviceInput.deviceInputWithDevice(it, null)
        if (input != null && captureSession.canAddInput(input)) {
          captureSession.addInput(input)
        }
      }

      captureSession.commitConfiguration()
      captureSession.startRunning()

      containerView
    },
    modifier = modifier,
    update = { view ->
      CATransaction.begin()
      CATransaction.setValue(true, kCATransactionDisableActions)
      view.layer.sublayers?.firstOrNull()?.let { layer ->
        // layer.frame = view.layer.bounds // todo: check layer.frame
      }
      CATransaction.commit()
    }
  )
}