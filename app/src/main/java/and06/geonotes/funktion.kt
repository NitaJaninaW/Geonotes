package and06.geonotes

fun decimalToSexagesimal(latitude: Double, longitude: Double): String {
    val latDegrees = latitude.toInt()
    val lonDegrees = longitude.toInt()
    val latTempMinutes = Math.abs((latitude - latDegrees) * 60)
    val lonTempMinutes = Math.abs((longitude - lonDegrees) * 60)
    val latMinutes = latTempMinutes.toInt()
    val lonMinutes = lonTempMinutes.toInt()
    val latTempSeconds = (latTempMinutes - latMinutes) * 60
    val lonTempSeconds = (lonTempMinutes - lonMinutes) * 60
// auf drei Stellen runden
    val latSeconds = Math.round(latTempSeconds * 1000) / 1000.0
    val lonSeconds = Math.round(lonTempSeconds * 1000) / 1000.0
    val latDegreesString =
        Math.abs(latDegrees).toString() + if (latitude < 0) "°S " else "°N "
    val lonDegreesString =
        Math.abs(lonDegrees).toString() + if (longitude < 0) "°W " else "°E "
    return lonDegreesString + lonMinutes + "\' " + lonSeconds +
            "\'\' / " + latDegreesString + latMinutes + "\' " +
            latSeconds + "\'\'"
}

fun main() {
    println(decimalToSexagesimal(52.514365, 13.350140))
}


/*fun decimalToSexagesimal(latitude: Double, longitude: Double): String {
    // Umwandlung von Dezimalgrad in Grad, Minuten und Sekunden
    fun convert(decimal: Double): Triple<Int, Int, Double> {
        val degrees = decimal.toInt()
        val minutes = ((decimal - degrees) * 60).toInt()
        val seconds = (decimal - degrees - minutes / 60.0) * 3600.0
        return Triple(degrees, minutes, seconds)
    }

    val (latDeg, latMin, latSec) = convert(latitude)
    val (lonDeg, lonMin, lonSec) = convert(longitude)

    // Formatierung der Ausgabe
    return "${Math.abs(latDeg)}°${if (latDeg >= 0) "N" else "S"} ${latMin}' ${latSec}\" , ${Math.abs(lonDeg)}°${if (lonDeg >= 0) "E" else "W"} ${lonMin}' ${lonSec}\""
}

// Test der Funktion
fun main() {
    println(decimalToSexagesimal(52.514365, 13.350140)) Here some changes
}*/
