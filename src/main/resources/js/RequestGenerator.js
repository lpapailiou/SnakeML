
const loadJSON = callback => {

  const xobj = new XMLHttpRequest();
  xobj.overrideMimeType("application/json");
  xobj.open('GET', 'http://localhost:8050/', true);
  xobj.onreadystatechange = () => {
    if (xobj.readyState === 4 && xobj.status === 200) {
      callback(xobj.responseText);
    }
  }
  xobj.send(null);
}

const loadCurrentState = () => {
  loadJSON(response => {
    const jsonResponse = JSON.parse(response);
    console.log(jsonResponse);
    jsonResponse.forEach(batch => {
      loadGraph(batch.generations)
    })

  });
}