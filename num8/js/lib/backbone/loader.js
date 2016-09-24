define([ 'order!lib/jquery/jquery-min',
         'order!lib/underscore/underscore-min',
         'order!lib/backbone/backbone-min'],
function(){
  return {
    Backbone: Backbone,
    _: _.noConflict(),
    $: jQuery.noConflict()
  };
});
