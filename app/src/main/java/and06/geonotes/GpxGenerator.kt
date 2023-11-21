package and06.geonotes;

import android.content.Context
import android.net.Uri
import android.util.Log
import org.w3c.dom.Document
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.parsers.ParserConfigurationException

class GpxGenerator {
    fun createGpxFile(context: Context, notizen: List<Notiz>):
            Uri? {
        val document = createGpxDocument()
        return null // provisorisch!
    }

    private fun createGpxDocument(): Document? {
        val factory = DocumentBuilderFactory.newInstance()
        val gpxDocument = try {
            val builder = factory.newDocumentBuilder()
            builder.newDocument()
        } catch (ex: ParserConfigurationException) {
            Log.e(javaClass.simpleName, ex.toString())
            null
        }
        return gpxDocument
    }
}