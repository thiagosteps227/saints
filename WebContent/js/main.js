// The root URL for the RESTful services
var rootURL = "http://localhost:8080/Saints/rest/saints";

var currentSaint;
var findAll= function () {
	console.log('findAll');
	$.ajax({
		type: 'GET',
		url: rootURL,
		dataType: "json", // data type of response
		success: renderList
	});
};

var findById= function() {
	console.log('findById: ' + id);
	$.ajax({
		type: 'GET',
		url: rootURL + '/' + $('#saintId').val(),
		dataType: "json",
		success: function(data){
			//$('#updateCustomer').show();
			//console.log('findById success: ' + data.name);
			currentSaint = data;
			openModal(currentSaint);
		}
	});
};

var renderList = function (list) {
        console.log("response");
	$.each(list, function(index, saint) {
		$('#table_body').append('<tr><td>'+saint.name+'</td><td>'+
				saint.country+'</td><td>'+saint.city+'</td><td>'+saint.century+
				'</td></tr>');
	});
        $('#table_id').DataTable();
};

var showGrid = function(list){
	$.each(list, function(index, saint){
		$('<div>', {class: 'row row-cols-auto'}).append(
		        $('<div>', {class: 'col', title: 'Saints'})
		            .append('<h3>' + saint.name + '</h3>')
		            .append('<p>' + saint.description + '<p>')
		    ).appendTo(saint);
	})
}

var openModal = function(saint){
	$('#name').val(saint.name);
	$('#country').val(saint.country);
	$('#city').val(saint.city);
	$('#century').val(saint.century);
	$('#description').val(saint.description);
	
}

//Retrieve the wine list when the DOM is ready
$(document).ready(function(){
	//$(document).on("click", '#modalBtn', function(){findById();});
	findAll();
	  $('#modalBtn').click(function (e) {
          e.preventDefault();
          $('#myModal').modal('show');
      });
	  $('#findByIdButton').click(function(e){
		  e.preventDefault();
		  alert("button");
		  findById();
		});
    
	  
       $('.nav-tabs a[href="#home"]').click(function (e) {
            e.preventDefault();
            var list = findAll();
            showGrid(list);
            alert("tab home");
            $(this).tab('show');
        });
});

