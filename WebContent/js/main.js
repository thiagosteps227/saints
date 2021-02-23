let rootURL = "http://localhost:8080/Saints/rest/saints";

let findAll = function(){
	$.ajax({type: 'GET',
		url: rootURL,
		datatype: "json",
		success:renderList});
}

function renderList(saints){
	$.each(saintList, function(index,saints){
		$('#saintList').append('<li><a href="#" '+ saints.id+'">'+saints.name+'</a></li>');
	})
}

$(document).ready(function(){
	findAll();
})
