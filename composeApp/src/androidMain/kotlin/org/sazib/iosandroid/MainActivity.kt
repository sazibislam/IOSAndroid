package org.sazib.iosandroid

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*

class MainActivity : ComponentActivity() {

    private var permissionGranted by mutableStateOf(false)

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionGranted = isGranted
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                val permissionHandler = rememberCameraPermissionHandler()

                LaunchedEffect(Unit) {
                    if (!permissionHandler.hasCameraPermission()) {
                        requestPermissionLauncher.launch(Manifest.permission.CAMERA)
                    } else {
                        permissionGranted = true
                    }
                }

                CameraFaceRecognitionScreen(permissionHandler)
            }
        }
    }
}