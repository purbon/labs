function locate_place() {
  var geocoder =  new google.maps.Geocoder();
  var address = document.getElementById("search_box").value;
  geocoder.geocode( { 'address': address}, function(results, status) {
    if (status == google.maps.GeocoderStatus.OK) {
      var location = results[0].geometry.location;
      map.setCenter({lat: location.$a, lon: location.ab});
    } else {
      alert("Geocode was not successful for the following reason: " + status);
    }
  });
}

function geolocate() {
  map.locate(16);
}

function showPlace(elem) {
  var option = elem[elem.selectedIndex];
  map.fetch_marker(option.value);
}
