var url = "http://localhost:8080/api/games"

var app = new Vue({
    el:'#app',
    data:{
        games:[],
    }
})
fetch(url)
.then(function(myData){
    return myData.json();
})
.then(function(myData){
    data = myData;
    app.games = data;
    done();
})

//Crear tabla de datos
function done(){
    var table = document.getElementById('salvo-table');
    table.innerHTML="";
    var tableContent = createTableContent(app.games);
    table.innerHTML = tableContent;
}
//crear el contenido de la tabla
function createTableContent(games){
    var table = '<thead class="thead"><tr><th>DateGame</th><th>Player1</th><th>Player2</th><th>Winner</th></tr></thead>';
    table +='<tbody>';
    games.forEach(function(game){
        table += '<tr>';
        table += '<td>'+new Date(game.created).toLocaleString()+'</td>';
        if(game.gamePlayers[0] === null){
            table += '<td>'+"N/A"+'</td>';
        }else{
            table += '<td>'+game.gamePlayers[0].player.email+'</td>';
        }
        if(game.gamePlayers[1] === null){
            table += '<td>'+"N/A"+'</td>';
        }else{
            table += '<td>'+game.gamePlayers[1].player.email+'</td>';
        }
        table += '<td></td>';
        table += '</tr>';
    });
    table += '</tbody>';
    return table;
}