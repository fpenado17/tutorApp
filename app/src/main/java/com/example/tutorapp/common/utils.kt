package com.example.tutorapp.common
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap.Config
import android.graphics.Canvas
import android.speech.RecognizerIntent
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.createBitmap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import java.util.Locale

// Decodificar polyline Directions Google
fun decodePolyline(encoded: String): List<LatLng> {
    val poly = ArrayList<LatLng>()
    var index = 0
    val len = encoded.length
    var lat = 0
    var lng = 0

    while (index < len) {
        var b: Int
        var shift = 0
        var result = 0
        do {
            b = encoded[index++].code - 63
            result = result or ((b and 0x1f) shl shift)
            shift += 5
        } while (b >= 0x20)
        val dlat = if (result and 1 != 0) (result shr 1).inv() else (result shr 1)
        lat += dlat

        shift = 0
        result = 0
        do {
            b = encoded[index++].code - 63
            result = result or ((b and 0x1f) shl shift)
            shift += 5
        } while (b >= 0x20)
        val dlng = if (result and 1 != 0) (result shr 1).inv() else (result shr 1)
        lng += dlng

        val p = LatLng(lat / 1E5, lng / 1E5)
        poly.add(p)
    }
    return poly
}

// Convetir icono a formato correcto para marcador
fun getBitmapDescriptorFromVector(context: Context, drawableName: String): BitmapDescriptor? {
    val resourceId = context.resources.getIdentifier(drawableName, "drawable", context.packageName)
    if (resourceId == 0) return null

    val drawable = AppCompatResources.getDrawable(context, resourceId) ?: return null

    val sizePx = 96
    val bitmap = createBitmap(sizePx, sizePx, Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    drawable.setBounds(0, 0, sizePx, sizePx)
    drawable.draw(canvas)

    return BitmapDescriptorFactory.fromBitmap(bitmap)
}

// Utilizar microfono
fun startVoiceRecognition(
    context: Context,
    launcher: ActivityResultLauncher<Intent>
) {
    val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
        putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        putExtra(RecognizerIntent.EXTRA_PROMPT, "Habla ahora...")
    }
    launcher.launch(intent)
}