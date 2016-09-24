var config = {
  labels: [],
  datasets: [
    {
    label: "simple line in/out",
    fillColor: "rgba(220,220,220,0.2)",
    strokeColor: "rgba(220,220,220,1)",
    pointColor: "rgba(220,220,220,1)",
    pointStrokeColor: "#fff",
    pointHighlightFill: "#fff",
    pointHighlightStroke: "rgba(220,220,220,1)",
    data: []
  },
  {
    label: "simple line in/json out",
    fillColor: "rgba(151,187,205,0.2)",
    strokeColor: "rgba(151,187,205,1)",
    pointColor: "rgba(151,187,205,1)",
    pointStrokeColor: "#fff",
    pointHighlightFill: "#fff",
    pointHighlightStroke: "rgba(151,187,205,1)",
    data: []
  },
  {
    label: "json codec in/out",
    fillColor: "rgba(153, 204, 179, 0.2)",
    strokeColor: "rgba(153, 204, 179, 1)",
    pointColor: "rgba(153, 204, 179, 1)",
    pointStrokeColor: "#fff",
    pointHighlightFill: "#fff",
    pointHighlightStroke: "rgba(153, 204, 179, 1)",
    data: []
  },
  {
    label: "line in/json filter/json out",
    fillColor: "rgba(156,79,79,0.2)",
    strokeColor: "rgba(156,79,279,1)",
    pointColor: "rgba(156,79,79,1)",
    pointStrokeColor: "#fff",
    pointHighlightFill: "#fff",
    pointHighlightStroke: "rgba(156,79,79,1)",
    data: []
  },
  {
    label: "apache in/json out",
    fillColor: "rgba(255,255,204,0.2)",
    strokeColor: "rgba(255,255,204,1)",
    pointColor: "rgba(255,255,204,1)",
    pointStrokeColor: "#fff",
    pointHighlightFill: "#fff",
    pointHighlightStroke: "rgba(255,255,204,1)",
    data: []
  },
  {
    label: "apache in/grok codec/json out",
    fillColor: "rgba(255,173,92,0.2)",
    strokeColor: "rgba(255,173,92,1)",
    pointColor: "rgba(255,173,92,1)",
    pointStrokeColor: "#fff",
    pointHighlightFill: "#fff",
    pointHighlightStroke: "rgba(255,173,92,1)",
    data: []
  },
  {
    label: "syslog in/json out",
    fillColor: "rgba(92,173,255,0.2)",
    strokeColor: "rgba(92,173,255,1)",
    pointColor: "rgba(92,173,255,1)",
    pointStrokeColor: "#fff",
    pointHighlightFill: "#fff",
    pointHighlightStroke: "rgba(92,173,255,1)",
    data: []
  }
  ]
};

function legend(parent, data) {
    parent.className = 'legend';
    var datas = data.hasOwnProperty('datasets') ? data.datasets : data;

    // remove possible children of the parent
    while(parent.hasChildNodes()) {
        parent.removeChild(parent.lastChild);
    }

    datas.forEach(function(d) {
        var title = document.createElement('span');
        title.className = 'title';
        title.style.borderColor = d.hasOwnProperty('strokeColor') ? d.strokeColor : d.color;
        title.style.borderStyle = 'solid';
        parent.appendChild(title);

        var text = document.createTextNode(d.label);
        title.appendChild(text);
    });
}

var _legendTemplate = "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].lineColor%>\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>"
var options = { datasetFill : true, bezierCurve: false, legendTemplate: _legendTemplate, scaleShowLabels: true, showTooltips: true   }

function init_config() {
  config.labels = [];
  for(var i=0; i < config.datasets.length; i++) {
    config.datasets.data = [];
  }
}

function paint(chart_name) {
  var ctx = document.getElementById(chart_name+"-chart").getContext("2d");
  init_config();
  var chart = new Chart(ctx).Line(config, options);
  $.getJSON( chart_name+".json", function( data ) {
    $.each(data, function(e,i) {
      chart.addData(i, e);
    })
    chart.update();
  });
  legend(document.getElementById(chart_name+'-placeholder'), config);
}


paint("avg-tps");
paint("events");
paint("best-tps");
