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

// Call to function with anonymous callback
loadJSON(function(response) {
    jsonresponse = JSON.parse(response);
    console.log(response);
    var formatted = JSON.stringify(jsonresponse, null, 2);
    var json_file = ''
    json_file = jsonresponse.generations;
    loadGraph(jsonresponse.generations)

    console.log(json_file);

});
}