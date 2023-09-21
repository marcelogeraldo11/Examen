package com.example.examen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MediaItem()


        }
    }
}


@Preview(showBackground = true)
@Composable
fun MediaItem(){
    Column {
        Box(modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
        ){
           AsyncImage(
               model= "https://loremflickr.com/400/400/paris",
               contentDescription = null,
               modifier = Modifier.fillMaxSize(),
               contentScale = ContentScale.Crop
           )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onPrimary)
                .padding(16.dp)
        ){
            Text(
                text = "Lugar"
            )
        }

    }



    class CameraActivity : AppCompatActivity() {

        private lateinit var viewFinder: PreviewView
        private lateinit var imageCapture: ImageCapture
        private lateinit var cameraExecutor: ExecutorService

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_camera)

            viewFinder = findViewById(R.id.viewFinder)
            val captureButton: Button = findViewById(R.id.captureButton)

            // Solicitar permisos de cámara si no se han otorgado
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                ActivityCompat.requestPermissions(
                    this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
                )
            }

            captureButton.setOnClickListener { takePhoto() }

            cameraExecutor = Executors.newSingleThreadExecutor()
        }

        private fun startCamera() {
            val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

            cameraProviderFuture.addListener({
                val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

                val preview = Preview.Builder()
                    .build()
                    .also {
                        it.setSurfaceProvider(viewFinder.surfaceProvider)
                    }

                imageCapture = ImageCapture.Builder()
                    .build()

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                try {
                    cameraProvider.unbindAll()

                    cameraProvider.bindToLifecycle(
                        this as LifecycleOwner, cameraSelector, preview, imageCapture
                    )

                } catch (e: Exception) {
                    Log.e(TAG, "Error al iniciar la cámara: ${e.message}")
                }

            }, ContextCompat.getMainExecutor(this))
        }

        private fun takePhoto() {
            val imageCapture = imageCapture ?: return

            val photoFile = File(
                outputDirectory,
                SimpleDateFormat(FILENAME_FORMAT, Locale.US)
                    .format(System.currentTimeMillis()) + ".jpg"
            )

            val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

            imageCapture.takePicture(
                outputOptions, cameraExecutor, object : ImageCapture.OnImageSavedCallback {
                    override fun onError(error: ImageCaptureException) {
                        Log.e(TAG, "Error al capturar imagen: ${error.message}", error)
                    }

                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        val savedUri = outputFileResults.savedUri ?: Uri.fromFile(photoFile)
                        val msg = "Foto capturada con éxito: $savedUri"
                        Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    }
                })
        }

        private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                baseContext, it
            ) == PackageManager.PERMISSION_GRANTED
        }

        override fun onRequestPermissionsResult(
            requestCode: Int, permissions: Array<String>, grantResults:
            IntArray
        ) {
            if (requestCode == REQUEST_CODE_PERMISSIONS) {
                if (allPermissionsGranted()) {
                    startCamera()
                } else {
                    Toast.makeText(
                        this,
                        "Permisos de cámara no otorgados por el usuario.",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
        }

        companion object {
            private const val TAG = "CameraActivity"
            private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
            private const val REQUEST_CODE_PERMISSIONS = 10
            private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        }
    }
}



