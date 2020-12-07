const loadJSON = callback => {

  const request = new XMLHttpRequest();
  request.overrideMimeType("application/json");
  request.open('GET', 'http://localhost:8050/', true);
  request.onreadystatechange = () => {
    if (request.readyState === 4 && request.status === 200) {
      callback(request.responseText);
    }
  }
  request.send(null);
}