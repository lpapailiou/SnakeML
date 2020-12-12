function loadGraph(gen_data, batchNumber) {
  am4core.options.autoDispose = true;

  // Themes
  am4core.useTheme(am4themes_dark);
  am4core.useTheme(am4themes_animated);

  // -------------------------- snake length ----------------------------------

  const chartConfig = [
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

  const snakeLengthChart = am4core.create(
      `chart__snake-length--batch-${batchNumber}`,
      am4charts.XYChart);
  snakeLengthChart.data = gen_data

  const valueAxisX = snakeLengthChart.xAxes.push(new am4charts.ValueAxis());
  valueAxisX.title.text = 'Generation';
  valueAxisX.renderer.minGridDistance = 40;

  const valueAxisY = snakeLengthChart.yAxes.push(new am4charts.ValueAxis());
  valueAxisY.title.text = 'Snake length';

  chartConfig.forEach(config => {
    const lineSeries = snakeLengthChart.series.push(new am4charts.LineSeries());
    lineSeries.dataFields.valueY = config.key;
    lineSeries.dataFields.valueX = 'id';
    lineSeries.strokeOpacity = 0;

    lineSeries.tooltipText = config.tooltip;
    lineSeries.tooltip.pointerOrientation = "vertical";
    lineSeries.tooltip.background.fillOpacity = 0.9;

    const bullet = lineSeries.bullets.push(new am4charts.Bullet());

    const circleStyle = bullet.createChild(am4core.Circle);
    circleStyle.horizontalCenter = "middle";
    circleStyle.verticalCenter = "middle";
    circleStyle.strokeWidth = 0;
    circleStyle.fill = snakeLengthChart.colors.getIndex(config.colorIndex);
    circleStyle.direction = "top";
    circleStyle.width = 6;
    circleStyle.height = 6;

  });

  snakeLengthChart.scrollbarX = new am4core.Scrollbar();

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

  createLineChart(`chart__deaths--batch-${batchNumber}`, gen_data,
      deathsConfig);

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

  createLineChart(`chart__fitness--batch-${batchNumber}`, gen_data,
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
      key: 'avg_fitness',
      tooltip: 'average step count',
      colorIndex: 5,
    },
  ];

  createLineChart(`chart__steps--batch-${batchNumber}`, gen_data, stepConfig)
}

const createLineChart = (chartElementId, data, lineConfigurations) => {
  const chart = am4core.create(chartElementId,
      am4charts.XYChart);
  chart.paddingRight = 20;

  chart.data = data;

  const xAxis = chart.xAxes.push(new am4charts.CategoryAxis());
  xAxis.dataFields.category = 'id';
  chart.yAxes.push(new am4charts.ValueAxis());

  lineConfigurations.forEach(config => {

    const series = chart.series.push(new am4charts.LineSeries());
    series.dataFields.categoryX = 'id';
    series.dataFields.valueY = config.key;
    series.tooltipText = config.tooltip;
    series.strokeWidth = 1;
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