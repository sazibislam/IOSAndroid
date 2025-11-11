package org.sazib.iosandroid
// commonMain/CameraFaceRecognitionScreen.kt
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CameraFaceRecognitionScreen(
  permissionHandler: CameraPermissionHandler
) {
  var hasPermission by remember { mutableStateOf(permissionHandler.hasCameraPermission()) }
  var faceText by remember { mutableStateOf("Add Face") }
  var additionalInfo by remember { mutableStateOf("") }
  var textAbovePreview by remember { mutableStateOf("") }
  var showPermissionRationale by remember { mutableStateOf(false) }

  val scope = rememberCoroutineScope()

  LaunchedEffect(Unit) {
    if (!hasPermission) {
      hasPermission = permissionHandler.requestCameraPermission()
      if (!hasPermission) {
        showPermissionRationale = true
      }
    }
  }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .background(Color.White)
  ) {
    if (showPermissionRationale) {
      PermissionRationaleDialog(
        onDismiss = { showPermissionRationale = false },
        onRequestAgain = {
          scope.launch {
            hasPermission = permissionHandler.requestCameraPermission()
            showPermissionRationale = !hasPermission
          }
        }
      )
    }

    Column(
      modifier = Modifier.fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // Camera Preview Container
      Box(
        modifier = Modifier
          .width(297.dp)
          .height(279.dp)
          .padding(top = 16.dp),
        contentAlignment = Alignment.Center
      ) {
        if (hasPermission) {
          // CameraPreview composable - platform specific
          CameraPreviewView(
            modifier = Modifier.fillMaxSize()
          )

          // Camera button overlay
          IconButton(
            onClick = { /* Capture photo */ },
            modifier = Modifier
              .size(68.dp)
              .align(Alignment.TopEnd)
              .padding(8.dp)
              .zIndex(1f)
          ) {
            Icon(
              imageVector = Icons.Default.Add,
              contentDescription = "Camera",
              tint = Color(0xFF80CBC4),
              modifier = Modifier.size(48.dp)
            )
          }
        } else {
          // Permission denied placeholder
          Column(
            modifier = Modifier
              .fillMaxSize()
              .background(Color.LightGray),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
          ) {
            Icon(
              imageVector = Icons.Default.Refresh,
              contentDescription = null,
              modifier = Modifier.size(64.dp),
              tint = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
              text = "Camera Permission Required",
              fontSize = 16.sp,
              color = Color.Gray,
              textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
              onClick = {
                scope.launch {
                  hasPermission = permissionHandler.requestCameraPermission()
                  if (!hasPermission) {
                    showPermissionRationale = true
                  }
                }
              }
            ) {
              Text("Grant Permission")
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(12.dp))

      // Add Face Button
      Button(
        onClick = { /* Add face action */ },
        enabled = hasPermission,
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
          MaterialTheme.colors.primary
        )
      ) {
        Text("Add Face")
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Text above preview
      if (textAbovePreview.isNotEmpty()) {
        Text(
          text = textAbovePreview,
          fontSize = 15.sp,
          color = Color.Black
        )
      }

      Spacer(modifier = Modifier.height(16.dp))

      // Image/Face Display Box
      Box(
        modifier = Modifier
          .width(203.dp)
          .height(200.dp)
          .background(
            color = Color(0x2C7E57C2),
            shape = RoundedCornerShape(8.dp)
          ),
        contentAlignment = Alignment.Center
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center,
          modifier = Modifier.padding(16.dp)
        ) {
          Text(
            text = faceText,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF6200EE),
            textAlign = TextAlign.Center
          )

          if (additionalInfo.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
              text = additionalInfo,
              fontSize = 15.sp,
              color = Color.Black,
              textAlign = TextAlign.Center
            )
          }
        }

        // Add Face Icon Button
        IconButton(
          onClick = { /* Add face icon action */ },
          modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(8.dp)
        ) {
          Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add",
            tint = Color.Black
          )
        }
      }

      Spacer(modifier = Modifier.height(32.dp))

      // Actions Button
      Button(
        onClick = { /* Actions */ },
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
          MaterialTheme.colors.primary
        )
      ) {
        Text("ACTIONS")
      }
    }
  }
}

@Composable
fun PermissionRationaleDialog(
  onDismiss: () -> Unit,
  onRequestAgain: () -> Unit
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text("Camera Permission Required") },
    text = {
      Text("This app needs camera access to capture and recognize faces. Please grant camera permission in settings.")
    },
    confirmButton = {
      TextButton(onClick = onRequestAgain) {
        Text("Try Again")
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text("Cancel")
      }
    }
  )
}

// Platform-specific camera preview
@Preview()
@Composable
expect fun CameraPreviewView(modifier: Modifier)
