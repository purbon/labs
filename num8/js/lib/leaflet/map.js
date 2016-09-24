define([ 'jQuery', 
         "Leaflet"],
       function($, leaflet) {
         function AMap () {
         }

         AMap.prototype.initialize = function() {
           var me = this;
           me.map = new L.Map('map');
           var cloudmadeUrl = 'http://{s}.tile.cloudmade.com/BC9A493B41014CAABB98F0471D759707/997/256/{z}/{x}/{y}.png',
           cloudmadeAttribution = 'The Berlin/Map',
           cloudmade = new L.TileLayer(cloudmadeUrl, {maxZoom: 18, attribution: cloudmadeAttribution});
           me.map.setView(new L.LatLng(52.500556, 13.398889), 13).addLayer(cloudmade);
           me.addMarkers();
           me.map.on('locationfound', 
                  function onLocationFound(e) {
                    var radius = e.accuracy / 2;
                    me.setCenter({ latlng: e.latlng, radius: radius});
                  });
           me.map.on('locationerror',  function onLocationError(e) { console.log(e.message); });
         };

         AMap.prototype.locate = function(num) {
           var me = this;
           me.map.locateAndSetView(num);
         }

         AMap.prototype.setCenter = function(loc, showYouAreHere) {
           var me = this;
           showYouAreHere = (showYouAreHere == undefined ?  true : showYouAreHere);
           var location = loc.latlng;
           if (location == undefined) {
            var location = new L.LatLng(loc.lat, loc.lon);
           }
           var Icon = L.Icon.extend({
             iconUrl: 'images/marker-here.png',
             shadowUrl: 'images/marker-shadow.png'
           });

           me.map.panTo(location);
           if (showYouAreHere == true) {
             if (me.youAreHere) {
               me.map.removeLayer(me.youAreHere);
             }
             var markerIcon = new Icon();
             me.youAreHere = new L.Marker(location, {icon: markerIcon});
             me.youAreHere.bindPopup('You are here!').openPopup();
             me.map.addLayer(me.youAreHere);

             if (loc.radius != undefined) {
               if (me.hereCircle) {
                me.map.removeLayer(me.hereCircle);
               }
               me.hereCircle = new L.Circle(loc.latlng, loc.radius);
               me.map.addLayer(me.hereCircle);
             }
           }

         };

         AMap.prototype.addMarkers = function() {
           var me = this;
           $.ajax({
             url: 'https://api.mongolab.com/api/1/databases/berlinmap/collections/berlinmap?apiKey=500ee72be4b0c439ebd3a6fe',
             dataType: 'json',
             success: function(data) {
              var places = document.getElementById("places");
              for(var i=0; i < data.length; i++) {
                var elem = data[i];
                var location = new L.LatLng(elem.loc[0], elem.loc[1]);

                var Icon = L.Icon.extend({
                  iconUrl: 'images/marker-'+elem.type.toLowerCase()+'.png',
                  shadowUrl: 'images/marker-shadow.png'
                });
                var markerIcon = new Icon();
                var marker = new L.Marker(location, {icon: markerIcon});
                marker.bindPopup(me.build_popup(elem, markerIcon)).openPopup();
                me.map.addLayer(marker);
              
                var option = document.createElement('option');
                option.text = elem.name;
                option.value = elem._id.$oid;
                places.add(option);
              }
             },
             error: function(error) {
              console.log(error);
             }
           }); 
         };

         AMap.prototype.fetch_marker = function(id) {
           var me = this;
          $.ajax({
            url: 'https://api.mongolab.com/api/1/databases/berlinmap/collections/berlinmap/'+id+'?apiKey=500ee72be4b0c439ebd3a6fe',
            dataType: 'json',
            success: function(data) {
              me.setCenter({lat: data.loc[0], lon: data.loc[1]}, false);
            },
            error:   function(error) {
              console.log(error);
            }
          });
         }

         AMap.prototype.build_popup = function(elem, icon) {
           var text =  "<div class='popup'><h1>"+elem.name+"</h1>";
               text += "<img src='"+icon.iconUrl+"'/>";
               text += "<span><b>Address: </b>"+elem.address+"<br/>";
               text += "<b>Telephone: </b>"+elem.telephone+"<br/>";
               text += "</span>";
               text += "</div>";
               
           return text;
         };

         window.map = new AMap();
         return window.map;
       })
