define([
       'jQuery', 
       'Underscore', 
       'Backbone',
       'router',
       'lib/leaflet/map',
       'num8'
       // Request router.js
], function($, _, Backbone, Router, Map){
  var initialize = function(){
    var is_mobile = (window.location.host.charAt(0) == 'm');
    console.log("Router.initialize();");
    Router.initialize();
    $(document).ready(function() {
      console.log("Map.initialize();");
      Map.initialize(is_mobile);
      if (!is_mobile) {
      }
    });
  }
  return { 
    initialize: initialize
  };
});
