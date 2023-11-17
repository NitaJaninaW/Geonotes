package and06.geonotes

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.OnBackPressedCallback
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker
import java.io.File

class NoteMapActivity : AppCompatActivity() {

    companion object {
        val AKTUELLE_NOTIZ_ID = "aktuelle_notiz_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notemap)
        val osmConfig = Configuration.getInstance()
        osmConfig.userAgentValue = packageName
        val basePath = File(cacheDir.absolutePath, "osmdroid")
        osmConfig.osmdroidBasePath = basePath
        val tileCache = File(osmConfig.osmdroidBasePath, "tile")
        osmConfig.osmdroidTileCache = tileCache
        val map = findViewById<MapView>(R.id.mapview)
        map.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)
        map.invalidate()

        val extras = intent.extras ?: return
        val notizen =
            intent.getParcelableArrayListExtra<Notiz>(GatherActivity.NOTIZEN) ?: return
        notizen.forEach {
            val marker = Marker(map)
            marker.position = GeoPoint(it.latitude, it.longitude)
            marker.title = it.thema
            marker.snippet = decimalToSexagesimal(it.latitude, it.longitude)
            marker.subDescription = it.notiz
            map.overlays.add(marker)
        }
        val controller = map.controller
        var indexAktuelleNotiz =
            extras.getInt(GatherActivity.INDEX_AKTUELLE_NOTIZ)
        val aktuelleNotiz = notizen.get(indexAktuelleNotiz)
        controller.setCenter(
            GeoPoint(
                aktuelleNotiz.latitude,
                aktuelleNotiz.longitude
            )
        )
        controller.setZoom(18.0)
        //Previous button event handling
        val buttonPrevious =
            findViewById<ImageButton>(R.id.button_previous_notiz)
        buttonPrevious.setOnClickListener {
            indexAktuelleNotiz = if (indexAktuelleNotiz == 0)
                notizen.size - 1 else indexAktuelleNotiz - 1
            val notiz = notizen.get(indexAktuelleNotiz)
            controller.setCenter(
                GeoPoint(
                    notiz.latitude,
                    notiz.longitude
                )
            )
        }
        val buttonNext =
            findViewById<ImageButton>(R.id.button_next_notiz)
        buttonNext.setOnClickListener {
            indexAktuelleNotiz = (indexAktuelleNotiz + 1) % notizen.size
            val notiz = notizen.get(indexAktuelleNotiz)
            controller.setCenter(
                GeoPoint(
                    notiz.latitude,
                    notiz.longitude
                )
            )
        }
        //Wenn Rücktaste gedrückt wird
        onBackPressedDispatcher.addCallback(this,
            object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val pushIntent = getIntent()
                pushIntent.putExtra(AKTUELLE_NOTIZ_ID,
                    notizen.get(indexAktuelleNotiz).id)
                setResult(RESULT_OK, pushIntent)
                finish()
            }
        })
    }

    override fun onResume() {
        super.onResume()
        val map = findViewById<MapView>(R.id.mapview);
        map.onResume()
    }

    override fun onPause() {
        super.onPause()
        val map = findViewById<MapView>(R.id.mapview);
        map.onPause()
    }
}