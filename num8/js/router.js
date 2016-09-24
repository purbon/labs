define([
  'jQuery',
  'Underscore',
  'Backbone'
], function(jquery, underscore, Backbone){
  var AppRouter = Backbone.Router.extend({
    routes: {
      '*actions': 'defaultAction'
    },
    defaultAction: function(actions){
    }
  });
  var initialize = function(){
    var app_router = new AppRouter;
    Backbone.history.start();
  };
  return { 
    initialize: initialize
  };
});
