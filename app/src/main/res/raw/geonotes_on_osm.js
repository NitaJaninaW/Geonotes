var indexAktuelleNotiz = Android.getIndexAktuelleNotiz();
var map = new ol.Map({
    target: 'map',
    layers: [
        new ol.layer.Tile({
            source: new ol.source.OSM()
        })
    ],
    view: new ol.View({
        center: ol.proj.fromLonLat([Android.getLongitude(indexAktuelleNotiz),
            Android.getLatitude(indexAktuelleNotiz)
        ]),
        zoom: 16
    })
});
var iconStyle = new ol.style.Style({
    image: new ol.style.Icon(({
        anchor: [0.5, 0.5],
        anchorXUnits: 'fraction',
        anchorYUnits: 'fraction',
        opacity: 0.75,
        src: 'marker.png'
    }))
});
var iconFeatures = [];
for (var i = 0; i < Android.getNotizenCount(); i++) {
    var iconFeature = new ol.Feature({
        geometry: new
        ol.geom.Point(ol.proj.transform([
                Android.getLongitude(i),
                Android.getLatitude(i)
            ], 'EPSG:4326',
            'EPSG:3857')),
        name: Android.getThema(i),
        description: Android.getNotiz(i),
    });
    iconFeature.setStyle(iconStyle);
    iconFeatures.push(iconFeature);
}

var layer = new ol.layer.Vector({
    source: new ol.source.Vector({
        features: iconFeatures
    })
});
map.addLayer(layer);
var container = document.getElementById('popup');
var content = document.getElementById('popup-content');
var closer = document.getElementById('popup-closer');
var overlay = new ol.Overlay({
    element: container,
    autoPan: true,
    autoPanAnimation: {
        duration: 250
    }
});
map.addOverlay(overlay);
map.on('singleclick', function(event) {
    if (map.hasFeatureAtPixel(event.pixel) === true) {
        var coordinate = event.coordinate;
        var features = map.getFeaturesAtPixel(event.pixel);
        content.innerHTML = '<b>' + features[0].get("name") +
            '</b><br />' + features[0].get("description");
        overlay.setPosition(coordinate);
    } else {
        overlay.setPosition(undefined);
        closer.blur();
    }
});