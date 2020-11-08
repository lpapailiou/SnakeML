var xobj = new XMLHttpRequest();

function loadJSON(callback) {

    var xobj = new XMLHttpRequest();
    xobj.overrideMimeType("application/json");
    xobj.open('GET', 'http://localhost:8050/?cmd=load', true);
    xobj.onreadystatechange = function() {
        if (xobj.readyState == 4 && xobj.status == "200") {
            callback(xobj.responseText);
        }
    }
    xobj.send(null);
}

function loadCurrentState(){
loadJSON(function(response) {
    jsonresponse = JSON.parse(response);
    var formatted = JSON.stringify(jsonresponse, null, 2);
    let chars = jsonresponse['generations']
    for(let i=0, len=chars.length; i < len; i++){
      loadGraph(chars[i])
    }
});
}