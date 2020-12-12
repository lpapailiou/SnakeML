/**
 * Provides an asynchronous client for fetching the batches from the SnakeML
 * backend application
 *
 * @param callback The callback to which the data will be passed when the
 * loading of the data is finished.
 */
const loadJSON = callback => {

  const request = new XMLHttpRequest();
  request.overrideMimeType("application/json");
  request.open('GET', 'http://localhost:8050/', true);
  request.onreadystatechange = () => {
    const errorToast = document.getElementById('error-toast');

    if (request.readyState === 4 && request.status === 200) {
      callback(request.responseText);
      errorToast.classList.remove('error-toast--visible');
      return;
    }

    errorToast.classList.add('error-toast--visible');
    setLoadingState(false);
  }

  request.send(null);
}