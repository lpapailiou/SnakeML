const templateIds = [
  "chart__snake-length--batch-",
  "chart__deaths--batch-",
  "chart__fitness--batch-",
  "chart__steps--batch-",
];

/**
 * Loads the data using the SnakeMlClient. When done, it displays the selectable
 * batches and displays their charts.
 */
function loadCurrentBatchData() {
  setLoadingState(true)

  loadJSON(response => {
    setLoadingState(false);
    const batches = JSON.parse(response);

    handleEmptyMessage(batches);
    displayBatchSelectionForm(batches);
    updateChartVisibility();
    displayCharts(batches);

  });
}

/**
 * Handles the displaying states of the "reload" and "loading.." buttons
 * according to the loading state.
 *
 * @param isLoading If the app is currently loading data.
 */
function setLoadingState(isLoading) {
  let reloadButton = document.getElementById('reload-button');
  let loadingButton = document.getElementById('loading-button');
  if (isLoading) {
    reloadButton.disabled = true;
    reloadButton.classList.add('reload-button--loading');
    loadingButton.classList.add('loading-button--loading');
  } else {
    reloadButton.disabled = false;
    reloadButton.classList.remove('reload-button--loading');
    loadingButton.classList.remove('loading-button--loading');
  }
}

/**
 * Displays a message, if the batch data array does not contain any elements.
 * Hides this message otherwise.
 *
 * @param batches
 */
function handleEmptyMessage(batches) {
  if (batches.length === 0) {
    document.getElementById('empty-message').classList.add(
        'empty-message--visible');
  } else {
    document.getElementById('empty-message').classList.remove(
        'empty-message--visible');
  }
}

/**
 * Displays a checkbox and the configuration information for all batches.
 *
 * @param batches The batch data array.
 */
function displayBatchSelectionForm(batches) {
  let batchSelectionForm = document.getElementById('batch-selection-form');
  batchSelectionForm.textContent = null;

  batches.forEach((batch, index) => {
    const batchNumber = index + 1;
    let label = document.createElement('label');
    label.classList.add('batch-selection__label');
    label.innerText = `Batch #${batchNumber} - ${batch.configuration.algorithm}`;

    let checkbox = document.createElement('input');
    checkbox.type = 'checkbox';
    checkbox.id = 'batch-selection__checkbox--id-' + batchNumber;
    checkbox.dataset.batchNumber = batchNumber;
    checkbox.classList.add('batch-selection__checkbox');
    checkbox.checked = true;
    checkbox.onclick = () => updateChartVisibility();

    label.appendChild(checkbox)
    batchSelectionForm.insertBefore(label, batchSelectionForm.firstChild);
  });
}

/**
 * Displays or hides all charts according to the state of their respective
 * checkboxes.
 */
function updateChartVisibility() {
  document.querySelectorAll('#charts-container > div:not(.chart-template-div)')
  .forEach(chartDiv => chartDiv.style.display = 'none');

  getSelectedBatchNumbers().forEach(chartNumber => {
    document.querySelectorAll(`#charts-container > div[data-batch-number="${chartNumber}"]`)
    .forEach(element => {
      element.style.display = 'block';
    })
  });
}

/**
 * Displays all charts.
 *
 * @param batches The batch data array.
 */
function displayCharts(batches) {

  batches.forEach((batch, batchNumber) => {
    batchNumber++;

    templateIds.forEach(templateId => {
      if (document.getElementById(templateId + batchNumber) === null) {
        cloneChartDiv(templateId, batchNumber, batch);
      }
    });

    displayGraphs(batch.generations, batchNumber);
  });
}

/**
 * Returns all batch numbers which are currently selected.
 *
 * @returns string[] All selected batch numbers.
 */
function getSelectedBatchNumbers() {
  return Array.from(
      document.getElementsByClassName('batch-selection__checkbox'))
  .filter(checkbox => checkbox.checked)
  .map(checkbox => checkbox.dataset.batchNumber);
}

/**
 * Copies the template chart div element and populates it with the according
 * data of the batch.
 *
 * @param templateId The html element id of the template to be used.
 * @param batchNumber The batch number of the batch.
 * @param batch The batch data.
 */
function cloneChartDiv(templateId, batchNumber, batch) {
  const chartDivTemplate = document.getElementById(templateId);
  const chartDivClone = chartDivTemplate.cloneNode();
  chartDivClone.id = templateId + batchNumber;
  chartDivClone.classList.remove("chart-template-div");
  chartDivClone.setAttribute('data-batch-number', batchNumber);
  chartDivClone.setAttribute('data-batch-description',
      `Batch #${batchNumber} - ${batch.configuration.algorithm}`);
  chartDivTemplate.after(chartDivClone);
}
