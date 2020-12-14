/**
 * Displays the graphs for the snake length, death count, fitness and step count
 * data within this batch.
 *
 * @param generationData The generation data of this batch
 * @param batchNumber The batch number
 */
function displayGraphs(generationData, batchNumber) {
  am4core.options.autoDispose = true;

  // Themes
  am4core.useTheme(am4themes_dark);
  am4core.useTheme(am4themes_animated);

  // -------------------------- snake length ----------------------------------

  const lengthConfig = [
    {
      key: 'max_snake_length',
      tooltip: 'maximal snake length',
      colorIndex: 0,
    },
    {
      key: 'min_snake_length',
      tooltip: 'minimal snake length',
      colorIndex: 3,
    },
    {
      key: 'avg_snake_length',
      tooltip: 'average snake length',
      colorIndex: 5,
    },
  ];

  createLineChart(
      'Snake length',
      `chart__snake-length--batch-${batchNumber}`,
      generationData,
      lengthConfig
  );

  // ------------------------------ deaths ------------------------------------

  const deathsConfig = [
    {
      key: 'number_of_wall_deaths',
      tooltip: 'number of wall deaths',
      colorIndex: 0,
    },
    {
      key: 'number_of_body_deaths',
      tooltip: 'number of body deaths',
      colorIndex: 3,
    },
    {
      key: 'number_of_timeout_deaths',
      tooltip: 'number of timeout deaths',
      colorIndex: 5,
    },
  ];

  createLineChart(
      'Deaths',
      `chart__deaths--batch-${batchNumber}`,
      generationData,
      deathsConfig
  );

  // ------------------------------ fitness -----------------------------------

  const fitnessConfig = [
    {
      key: 'max_fitness',
      tooltip: 'maximal fitness',
      colorIndex: 0,
    },
    {
      key: 'min_fitness',
      tooltip: 'minimal fitness',
      colorIndex: 3,
    },
    {
      key: 'avg_fitness',
      tooltip: 'average fitness',
      colorIndex: 5,
    },
  ];

  createLineChart(
      'Fitness',
      `chart__fitness--batch-${batchNumber}`,
      generationData,
      fitnessConfig);

  // ------------------------------- steps ------------------------------------

  const stepConfig = [
    {
      key: 'max_steps',
      tooltip: 'maximal step count',
      colorIndex: 0,
    },
    {
      key: 'min_steps',
      tooltip: 'minimal step count',
      colorIndex: 3,
    },
    {
      key: 'avg_steps',
      tooltip: 'average step count',
      colorIndex: 5,
    },
  ];

  createLineChart(
      'Steps',
      `chart__steps--batch-${batchNumber}`,
      generationData,
      stepConfig)
}

/**
 * Creates a chart for the passed data.
 *
 * @param yAxisLabelText The label text to be shown for the graphs y-axis
 * @param chartElementId The html element id in which the chart will be inserted
 * @param data The data for the chart
 * @param lineConfigurations The configuration for the chart
 */
const createLineChart = (yAxisLabelText, chartElementId, data, lineConfigurations) => {
  const chart = am4core.create(chartElementId, am4charts.XYChart);
  chart.paddingRight = 20;

  chart.data = data;

  const xAxis = chart.xAxes.push(new am4charts.CategoryAxis());
  xAxis.dataFields.category = 'id';

  const yAxis = chart.yAxes.push(new am4charts.ValueAxis());
  yAxis.title.text = yAxisLabelText;

  lineConfigurations.forEach(config => {

    const series = chart.series.push(new am4charts.LineSeries());
    series.dataFields.categoryX = 'id';
    series.dataFields.valueY = config.key;
    series.tooltipText = config.tooltip;
    series.strokeWidth = 1;

    const bullet = series.bullets.push(new am4charts.Bullet());

    const circleStyle = bullet.createChild(am4core.Circle);
    circleStyle.horizontalCenter = "middle";
    circleStyle.verticalCenter = "middle";
    circleStyle.strokeWidth = 0;
    circleStyle.fill = chart.colors.getIndex(config.colorIndex);
    circleStyle.direction = "top";
    circleStyle.width = 6;
    circleStyle.height = 6;
  });

  chart.cursor = new am4charts.XYCursor();
  chart.cursor.xAxis = xAxis;
  chart.cursor.fullWidthLineX = true;
  chart.cursor.lineX.strokeWidth = 0;
  chart.cursor.lineX.fill = chart.colors.getIndex(2);
  chart.cursor.lineX.fillOpacity = 0.1;

  const scrollbarX = new am4core.Scrollbar();
  scrollbarX.marginBottom = 20;
  chart.scrollbarX = scrollbarX;
}