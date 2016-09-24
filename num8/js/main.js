require.config({
  paths: {
    loader: 'lib/backbone/loader',
    jQuery: 'lib/jquery/jquery',
    tools: 'lib/jquery/tools',
    uitools: 'lib/jquery/jquery-ui',
    Underscore: 'lib/underscore/underscore',
    Backbone: 'lib/backbone/backbone',
    Leaflet: 'lib/leaflet/leaflet',
    templates: '../templates'
  }

});

require([
  'app',
], function(App){
  console.log("App.initialize();");
  App.initialize();
});

require.onError = function(err) {
  console.log(err);
}
