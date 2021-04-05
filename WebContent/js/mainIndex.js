// The root URL for the RESTful services
var rootURL = "http://localhost:8080/Saints/rest/saints";

var currentSaint;
var findAll= function () {
	console.log('findAll');
	$.ajax({
		type: 'GET',
		url: rootURL,
		dataType: "json", 
		success: renderList
	});
};

var findAllGrid= function () {
	console.log('findAll');
	$.ajax({
		type: 'GET',
		url: rootURL,
		dataType: "json", 
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
		data: createFormToJSON(),
		success: function(data, textStatus, jqXHR){
			alert("New Saint added");
			cleanCreateModalForm();
		},
		error: function(jqXHR, textStatus, errorThrown){
			var errorString = jqXHR.responseText;
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
			//alert('Updating Saint error: ' + textStatus);
			var errorString = jqXHR.responseText;
			if( jqXHR.status === 403){
				alert(errorString);
			}
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

//creating the saint's datatable
var renderList = function (list) {
        console.log("response");
        $('#table_body tr').remove();
	$.each(list, function(index, saint) {
		$('#table_body').append("<tr><td>"+saint.name+"</td><td>"+saint.country+"</td><td>"+saint.city+"</td><td>"+saint.century+
				"</td><td><input class='btn btn-danger' type='button' id='" + saint.id + "' onclick='deleteSaint(" + saint.id + ")' value='Delete'>" +"       "+
						"<input class='btn btn-outline-success' type='button' name='"+saint.id+"'id='editBtn' value='Edit'></td></tr>");
	});
        $('#table_id').DataTable();
};

var formToJSON = function (){
	var id = $('#saintId').val();
	return JSON.stringify({
		"id": id == "" ? null : id, 
			"name" : $('#name').val(),
			"country" : $('#country').val(),
			"city" : $('#city').val(),
			"century" : $('#century').val(),
			"description" : $('#description').val(),
			"picture": $('#picture').val(),
	});
}

var createFormToJSON = function (){
	var id = $('#createsaintId').val();
	return JSON.stringify({
		"id": id == "" ? null : id, 
			"name" : $('#createname').val(),
			"country" : $('#createcountry').val(),
			"city" : $('#createcity').val(),
			"century" : $('#createcentury').val(),
			"description" : $('#createdescription').val(),
			"picture": $('#createpicture').val(),
	});
}

//creating the saints GRID
var renderGrid = function(list){
	 $('#saintsGrid div ').remove();
	$.each(list, function(index, saint){
		$('#saintsGrid').append("<div class='col'><img src=images/"+saint.picture+"><h4>"+saint.name+"</h4><p class='text-break'>"+saint.description+"</p></div>");		        			
	});
}

//method to clean the form after an operation
var cleanCreateModalForm= function(){
	$('#saintId').val("");
	$('#createname').val("");
	$('#createcountry').val("");
	$('#createcity').val("");
	$('#createcentury').val("");
	$('#createpicture').val("");
	$('#createdescription').val("");
	
}

var openModal = function(saints){
	$('#saintId').val(saints.id);
	$('#name').val(saints.name);
	$('#country').val(saints.country);
	$('#city').val(saints.city);
	$('#century').val(saints.century);
	$('#picture').val(saints.picture);
	$('#description').val(saints.description);
	
}

//validate the modal saint creator form 
var validateForm = function() {
	  var formInvalid = false;
	  $('#createForm input').each(function() {
	    if ($(this).val() === '') {
	      formInvalid = true;
	    }
	  });

	  return formInvalid;
}

// to validate the edit table modal when updating a saint
var validateUpdateForm = function() {
	  var formInvalid = false;
	  $('#editTableForm input').each(function() {
	    if ($(this).val() === '') {
	      formInvalid = true;
	    }
	  });
	  return formInvalid;
}

//Retrieve the saints list when the DOM is ready
$(document).ready(function(){
	  findAllGrid();
	  $('#formGoogleSearch').on("click", '#googleInput', function(e){
		  e.preventDefault();
		  $('#googleInput').val("");
		  $('footer').show();
		  
	  } )
	  
	  //show the grid with the saints in the home page
    $('.nav-tabs a[href="#home"]').click(function (e) {
         e.preventDefault();
         $('footer').hide();
         findAllGrid();
         $(this).tab('show');
     });
    
	//show datable with saints info
	 $('.nav-tabs a[href="#saints"]').click(function (e) {
         e.preventDefault();
         $('footer').hide();
         findAll();
         $(this).tab('show');
     });
	 
	 //show the modal to edit a saint
	 $('#table_id').on("click", '#editBtn', function (e) {
         e.preventDefault();
         $('#editSaintModal').modal('show');
         $('footer').hide();
         var id = event.target.name;
         findById(id);
     });
	 
	//show saints details in the modal
	 $('#modalBtn').click(function (e) {
          e.preventDefault();
          $('footer').hide();
          $('#myModal').modal('show');
      });
	
	 //add new Saint in the modal
	  $('#createForm').on("click",'#addNewSaintBtn', function(e){
		 e.preventDefault();
		 $('footer').hide();
			 if(validateForm()=== true){
					alert('One or Two fields are empty. Please fill up all fields');
			} else if (isNaN($('#createcentury').val())) {
				alert("Only numbers allowed in century field");
			}
			 else {
				addNewSaint();
			} 
	 })
	 
	 $('#createForm').on("click",'#createdescription', function(e){
		 $('#createChars').text("Max 250 characters allowed");
	 })
	 
	 //update a saint
	  $('#editTableForm').on("click",'#updateSaintBtn', function(e){
		 e.preventDefault();
		 $('footer').hide();
		 var id = $('#saintId').val();
			 if(validateUpdateForm () === true){
				 alert('One or Two fields are empty. Please fill up all fields');
			 } else if (isNaN( $('#century').val())) {
				 alert("Only numbers allowed in century field");
			} else if ($('#century').val() < 0 || $('#century').val() > 21) {
				alert("Type a valid century between 1 and 21");
			}
			 else {
				 updateSaint(id);
			}
	 });
	  
	  $('#editTableForm').on("click",'#description', function(e){
		 $('#descriptionChars').text("Max 250 characters allowed");
	 })
});
