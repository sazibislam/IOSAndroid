package org.sazib.iosandroid

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import kotliniosandroid.composeapp.generated.resources.Res
import kotliniosandroid.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
  MaterialTheme {

    // CameraFaceRecognitionScreen()

    var showContent by remember { mutableStateOf(false) }
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

      Button(onClick = { showContent = !showContent }) {
        Text("Click me!")
      }
      AnimatedVisibility(showContent) {
        val greeting = remember { Greeting().greet() }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
          Image(painterResource(Res.drawable.compose_multiplatform), null)
          Text("Compose: $greeting")
        }
      }
    }
  }
}

// @Composable
// fun CameraFaceRecognitionScreen() {
//   var faceText by remember { mutableStateOf("Add Face") }
//   var additionalInfo by remember { mutableStateOf("") }
//   var textAbovePreview by remember { mutableStateOf("") }
//
//   Box(
//     modifier = Modifier
//       .fillMaxSize()
//       .background(Color.White)
//   ) {
//     Column(
//       modifier = Modifier.fillMaxSize(),
//       horizontalAlignment = Alignment.CenterHorizontally
//     ) {
//       // Camera Preview Container
//       Box(
//         modifier = Modifier
//           .width(297.dp)
//           .height(279.dp)
//           .padding(top = 16.dp),
//         contentAlignment = Alignment.TopEnd
//       ) {
//         // PreviewView placeholder - integrate CameraX here
//         Box(
//           modifier = Modifier
//             .fillMaxSize()
//             .background(Color.LightGray)
//         )
//
//         // Camera button overlay
//         IconButton(
//           onClick = { /* Camera action */ },
//           modifier = Modifier
//             .size(68.dp)
//             .padding(8.dp)
//             .zIndex(1f)
//         ) {
//           Icon(
//             imageVector = Icons.Default.Person,
//             contentDescription = "Camera",
//             tint = Color(0xFF80CBC4),
//             modifier = Modifier.size(48.dp)
//           )
//         }
//       }
//
//       Spacer(modifier = Modifier.height(12.dp))
//
//       // Add Face Button
//       Button(
//         onClick = { /* Add face action */ },
//         shape = RoundedCornerShape(24.dp),
//         colors = ButtonDefaults.buttonColors(
//           MaterialTheme.colors.primary
//         )
//       ) {
//         Text("Add Face")
//       }
//
//       Spacer(modifier = Modifier.height(8.dp))
//
//       // Text above preview
//       if (textAbovePreview.isNotEmpty()) {
//         Text(
//           text = textAbovePreview,
//           fontSize = 15.sp,
//           color = Color.Black
//         )
//       }
//
//       Spacer(modifier = Modifier.height(16.dp))
//
//       // Image/Face Display Box
//       Box(
//         modifier = Modifier
//           .width(203.dp)
//           .height(200.dp)
//           .background(
//             color = Color(0x2C7E57C2),
//             shape = RoundedCornerShape(8.dp)
//           ),
//         contentAlignment = Alignment.Center
//       ) {
//         Column(
//           horizontalAlignment = Alignment.CenterHorizontally,
//           verticalArrangement = Arrangement.Center,
//           modifier = Modifier.padding(16.dp)
//         ) {
//           Text(
//             text = faceText,
//             fontSize = 22.sp,
//             fontWeight = FontWeight.Bold,
//             color = Color(0xFF6200EE)
//           )
//
//           if (additionalInfo.isNotEmpty()) {
//             Spacer(modifier = Modifier.height(8.dp))
//             Text(
//               text = additionalInfo,
//               fontSize = 15.sp,
//               color = Color.Black
//             )
//           }
//         }
//
//         // Add Face Icon Button
//         IconButton(
//           onClick = { /* Add face icon action */ },
//           modifier = Modifier
//             .align(Alignment.BottomEnd)
//             .padding(8.dp)
//         ) {
//           Icon(
//             imageVector = Icons.Default.Add,
//             contentDescription = "Add",
//             tint = Color.Black
//           )
//         }
//       }
//
//       Spacer(modifier = Modifier.height(32.dp))
//
//       // Actions Button
//       Button(
//         onClick = { /* Actions */ },
//         shape = RoundedCornerShape(24.dp),
//         colors = ButtonDefaults.buttonColors(
//           MaterialTheme.colors.primary
//         )
//       ) {
//         Text("ACTIONS")
//       }
//     }
//   }
// }
//
// // Preview
// @Preview()
// @Composable
// fun PreviewCameraFaceRecognitionScreen() {
//   MaterialTheme {
//     CameraFaceRecognitionScreen()
//   }
// }