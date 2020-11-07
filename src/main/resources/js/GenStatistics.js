var chart = null

function loadGraph(gen_data) {
console.log('graph building')
	if (chart != null) {
	chart.dispose()
	}
	if (chart2 != null) {
	chart2.dispose()
	}
	if (chart3 != null) {
	chart3.dispose()
	}
	if (chart4 != null) {
	chart4.dispose()
	}

// Themes begin
am4core.useTheme(am4themes_dark);
am4core.useTheme(am4themes_animated);
// Themes end

// Create chart instance
chart = am4core.create("chartdiv", am4charts.XYChart);
console.log(gen_data)
chart.data = gen_data
console.log(chart.data)
// Create axes
var valueAxisX = chart.xAxes.push(new am4charts.ValueAxis());
valueAxisX.title.text = 'Generations';
valueAxisX.renderer.minGridDistance = 40;

// Create value axis
var valueAxisY = chart.yAxes.push(new am4charts.ValueAxis());
valueAxisY.title.text = 'Snake length';

// Create series
var lineSeries = chart.series.push(new am4charts.LineSeries());
lineSeries.dataFields.valueY = "max_snake_length";
lineSeries.dataFields.valueX = "id";
lineSeries.strokeOpacity = 0;

var lineSeries2 = chart.series.push(new am4charts.LineSeries());
lineSeries2.dataFields.valueY = "min_snake_length";
lineSeries2.dataFields.valueX = "id";
lineSeries2.strokeOpacity = 0;

var lineSeries3 = chart.series.push(new am4charts.LineSeries());
lineSeries3.dataFields.valueY = "avg_snake_length";
lineSeries3.dataFields.valueX = "id";
lineSeries3.strokeOpacity = 0;

// Add a bullet
var bullet = lineSeries.bullets.push(new am4charts.Bullet());

// Add a triangle to act as am arrow
var arrow = bullet.createChild(am4core.Circle);
arrow.horizontalCenter = "middle";
arrow.verticalCenter = "middle";
arrow.strokeWidth = 0;
arrow.fill = chart.colors.getIndex(0);
arrow.direction = "top";
arrow.width = 6;
arrow.height = 6;

// Add a bullet
var bullet2 = lineSeries2.bullets.push(new am4charts.Bullet());

// Add a triangle to act as am arrow
var arrow2 = bullet2.createChild(am4core.Circle);
arrow2.horizontalCenter = "middle";
arrow2.verticalCenter = "middle";
arrow2.rotation = 180;
arrow2.strokeWidth = 0;
arrow2.fill = chart.colors.getIndex(3);
arrow2.direction = "top";
arrow2.width = 6;
arrow2.height = 6;

// Add a bullet
var bullet3 = lineSeries3.bullets.push(new am4charts.Bullet());

// Add a triangle to act as am arrow
var arrow3 = bullet3.createChild(am4core.Circle);
arrow3.horizontalCenter = "middle";
arrow3.verticalCenter = "middle";
arrow3.rotation = 180;
arrow3.strokeWidth = 0;
arrow3.fill = chart.colors.getIndex(5);
arrow3.direction = "top";
arrow3.width = 6;
arrow3.height = 6;

//scrollbars
chart.scrollbarX = new am4core.Scrollbar();
//chart.scrollbarY = new am4core.Scrollbar();

// --------------------------------------------------------------------------------------

var chart2 = am4core.create("chartdiv2", am4charts.XYChart);
             chart2.paddingRight = 20;


             console.log(gen_data)
             chart2.data = gen_data
             console.log(chart2.data)

             var dateAxis = chart2.xAxes.push(new am4charts.DateAxis());
             dateAxis.renderer.grid.template.location = 0;
             dateAxis.minZoomCount = 5;


             // this makes the data to be grouped
             dateAxis.groupData = true;
             dateAxis.groupCount = 500;

             var valueAxis = chart2.yAxes.push(new am4charts.ValueAxis());

             var series = chart2.series.push(new am4charts.LineSeries());
             series.stroke = chart.colors.getIndex(0)
             series.dataFields.dateX = "id";
             series.dataFields.valueY = "number_of_wall_deaths";
             series.tooltipText = "number of wall deaths";
             series.tooltip.pointerOrientation = "vertical";
             series.tooltip.background.fillOpacity = 0.5;

var series2 = chart2.series.push(new am4charts.LineSeries());
series2.stroke = chart.colors.getIndex(3)
series2.dataFields.dateX = "id";
series2.dataFields.valueY = "number_of_body_deaths";
series2.tooltipText = "number of body deaths";
series2.tooltip.pointerOrientation = "vertical";
series2.tooltip.background.fillOpacity = 0.1;

var series3 = chart2.series.push(new am4charts.LineSeries());
series3.stroke = chart.colors.getIndex(5)
series3.dataFields.dateX = "id";
series3.dataFields.valueY = "number_of_timeout_deaths";
series3.tooltipText = "number of timeout deaths";
series3.tooltip.pointerOrientation = "vertical";
series3.tooltip.background.fillOpacity = 0.9;

chart2.cursor = new am4charts.XYCursor();
chart2.cursor.xAxis = dateAxis;

var scrollbarX = new am4core.Scrollbar();
scrollbarX.marginBottom = 20;
chart2.scrollbarX = scrollbarX;


// ---------------------------------------------------------------------------------------------------------------------------------------------------

var chart3 = am4core.create("chartdiv3", am4charts.XYChart);

// Create axes
var dateAxis = chart3.xAxes.push(new am4charts.DateAxis());
var valueAxis = chart3.yAxes.push(new am4charts.ValueAxis());

chart3.data = gen_data;

var series = chart3.series.push(new am4charts.LineSeries());
series.dataFields.valueY = "max_fitness";
series.dataFields.dateX = "id";
series.name = "max fitness";
var segment = series.segments.template;
segment.interactionsEnabled = true;
var hoverState = segment.states.create("hover");
hoverState.properties.strokeWidth = 2;
var dimmed = segment.states.create("dimmed");
dimmed.properties.stroke = am4core.color("#fafafa");
segment.events.on("over", function(event) {
processOver(event.target.parent.parent.parent);
});
segment.events.on("out", function(event) {
processOut(event.target.parent.parent.parent);
});
series.data = gen_data;

var series2 = chart3.series.push(new am4charts.LineSeries());
series2.dataFields.valueY = "avg_fitness";
series2.dataFields.dateX = "id";
series2.name = "avg fitness";
var segment2 = series2.segments.template;
segment2.interactionsEnabled = true;
var hoverState2 = segment2.states.create("hover");
hoverState2.properties.strokeWidth = 3;
var dimmed2 = segment2.states.create("dimmed");
dimmed.properties.stroke = am4core.color("#dadada");
segment2.events.on("over", function(event) {
processOver(event.target.parent.parent.parent);
});
segment2.events.on("out", function(event) {
processOut(event.target.parent.parent.parent);
});
series2.data = gen_data;

var series3 = chart3.series.push(new am4charts.LineSeries());
series3.dataFields.valueY = "min_fitness";
series3.dataFields.dateX = "id";
series3.name = "min fitness";
var segment3 = series3.segments.template;
segment3.interactionsEnabled = true;
var hoverState3 = segment3.states.create("hover");
hoverState3.properties.strokeWidth = 2;
var dimmed3 = segment3.states.create("dimmed");
dimmed3.properties.stroke = am4core.color("#cacaca");
segment3.events.on("over", function(event) {
processOver(event.target.parent.parent.parent);
});
segment3.events.on("out", function(event) {
processOut(event.target.parent.parent.parent);
});
series3.data = gen_data;

chart3.legend = new am4charts.Legend();
chart3.legend.position = "right";
chart3.legend.scrollable = true;
chart3.legend.itemContainers.template.events.on("over", function(event) {
  processOver(event.target.dataItem.dataContext);
})

chart3.legend.itemContainers.template.events.on("out", function(event) {
  processOut(event.target.dataItem.dataContext);
})

function processOver(hoveredSeries) {
  hoveredSeries.toFront();

  hoveredSeries.segments.each(function(segment) {
    segment.setState("hover");
  })

  chart3.series.each(function(series) {
    if (series != hoveredSeries) {
      series.segments.each(function(segment) {
        segment.setState("dimmed");
      })
      series.bulletsContainer.setState("dimmed");
    }
  });
}

function processOut(hoveredSeries) {
  chart3.series.each(function(series) {
    series.segments.each(function(segment) {
      segment.setState("default");
    })
    series.bulletsContainer.setState("default");
  });
}


// ------------------------------------------------------------------------------------------

var chart4 = am4core.create("chartdiv4", am4charts.XYChart);
chart4.paddingRight = 20;

chart4.data = gen_data;

var dateAxis = chart4.xAxes.push(new am4charts.DateAxis());
var valueAxis = chart4.yAxes.push(new am4charts.ValueAxis());

var series = chart4.series.push(new am4charts.StepLineSeries());
series.dataFields.dateX = "id";
series.dataFields.valueY = "min_steps";
series.tooltipText = "min_steps";
series.strokeWidth = 3;

var series2 = chart4.series.push(new am4charts.StepLineSeries());
series2.dataFields.dateX = "id";
series2.dataFields.valueY = "avg_steps";
series2.tooltipText = "avg_steps";
series2.strokeWidth = 3;

var series3 = chart4.series.push(new am4charts.StepLineSeries());
series3.dataFields.dateX = "id";
series3.dataFields.valueY = "max_steps";
series3.tooltipText = "max_steps";
series3.strokeWidth = 3;

chart4.cursor = new am4charts.XYCursor();
chart4.cursor.xAxis = dateAxis;
chart4.cursor.fullWidthLineX = true;
chart4.cursor.lineX.strokeWidth = 0;
chart4.cursor.lineX.fill = chart4.colors.getIndex(2);
chart4.cursor.lineX.fillOpacity = 0.1;

chart4.scrollbarX = new am4core.Scrollbar();
	
}

am4core.ready(function() {
//loadGraph()

}); // end am4core.ready()