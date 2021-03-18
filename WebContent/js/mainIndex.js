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

var findAllGrid= function () {
	console.log('findAll');
	$.ajax({
		type: 'GET',
		url: rootURL,
		dataType: "json", // data type of response
		success: renderGrid
	});
};

var findById= function(id) {
	console.log('findById: ' + id);
	$.ajax({
		type: 'GET',
		url: rootURL + '/' + id,
		dataType: "json",
		success: function(data){
			$('#updateCustomer').show();
			console.log('findById success: ' + data.name);
			currentSaint = data;
			openModal(currentSaint);
		}
	});
};

//method to add a saint when the button add a saint is pressed
var addNewSaint = function(){
	$.ajax({type: 'POST', 
		contentType: 'application/json',
		url: rootURL, 
		datatype: "json", 
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){
			alert("New Saint added");
			cleanModalForm();
		},
		error: function(jqXHR, textStatus, errorThrown){
			var errorString = JSON.stringify(jqXHR.responseText)
			if( jqXHR.status === 403){
				alert(errorString);
			}
			
		}
	});
};

//method to update a saint
var updateSaint= function (id) {
	$.ajax({
		type: 'PUT',
		contentType: 'application/json',
		url: rootURL  + '/' + id,
		dataType: "json",
		data: formToJSON(),
		success: function(data, textStatus, jqXHR){
			alert('Saint updated successfully');
            currentSaint = data;
			openModal(currentSaint);
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('Updating Saint error: ' + textStatus);
		}
	});
};

//method to delete a saint using the delete button in the table
var deleteSaint=function(id) {
	console.log('deleteSaint');
	$.ajax({
		type: 'DELETE',
		url: rootURL + '/' + id,
		success: function(data, textStatus, jqXHR){
			alert('saint deleted successfully');
            findAll();
		},
		error: function(jqXHR, textStatus, errorThrown){
			alert('deleteSaint error');
		}
	});
};

var renderList = function (list) {
        console.log("response");
        $('#table_body tr').remove();
	$.each(list, function(index, saint) {
		$('#table_body').append("<tr><td>"+saint.name+"</td><td>"+saint.country+"</td><td>"+saint.city+"</td><td>"+saint.century+
				"</td><td><input type='button' id='" + saint.id + "' onclick='deleteSaint(" + saint.id + ")' value='Delete'></td></tr>");
	});
        $('#table_id').DataTable();
};

var formToJSON = function (){
	return JSON.stringify({
			"name" : $('#name').val(),
			"country" : $('#country').val(),
			"city" : $('#city').val(),
			"century" : $('#century').val(),
			"description" : $('#description').val(),
			"picture": currentSaint.picture,
	});
}

var renderGrid = function(list){
	 $('#saintsGrid div ').remove();
	$.each(list, function(index, saint){
		$('#saintsGrid').append("<div class='col'><img src=images/"+saint.picture+"><h4>"+saint.name+"</h4><p>"+saint.description+"</p></div>");
		        			
	});
}
//method to clean the form after an operation
var cleanModalForm= function(){
	$('#saintId').val("");
	$('#name').val("");
	$('#country').val("");
	$('#city').val("");
	$('#century').val("");
	$('#picture').val("");
	$('#description').val("");
	
}
var openModal = function(saints){
	$('#saintId').val(saints.id);
	$('#name').val(saints.name);
	$('#country').val(saints.country);
	$('#city').val(saints.city);
	$('#century').val(saints.century);
	$('#description').val(saints.description);
	
}

//Retrieve the saints list when the DOM is ready
$(document).ready(function(){
	 //show grid with saints info
    $('.nav-tabs a[href="#home"]').click(function (e) {
         e.preventDefault();
         //var list = findAll();
         findAllGrid();
         //alert("tab home");
         $(this).tab('show');
     });
	//show datable with saints info
	 $('.nav-tabs a[href="#saints"]').click(function (e) {
         e.preventDefault();
         findAll();
         $(this).tab('show');
     });
	 
	//show saints details in the modal
	 $('#modalBtn').click(function (e) {
          e.preventDefault();
          $('#myModal').modal('show');
      });
	 //findind a saint with id
	 $('#wineForm').on("click",'#findByIdButton', function(e){
		 e.preventDefault();
		var id = $('#saintId').val();
		 findById(id);
	 })
	 //add new Saint in the modal
	  $('#wineForm').on("click",'#addNewSaintBtn', function(e){
		 e.preventDefault();
		 addNewSaint();
	 })
	 //update a saint
	  $('#wineForm').on("click",'#updateSaintBtn', function(e){
		 e.preventDefault();
		 var id = $('#saintId').val();
		 updateSaint(id);
	 })
});
