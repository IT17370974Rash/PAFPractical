$(document).ready(function() {

	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();

});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateHospitalForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}

	// If valid use ajax------------------------

	var type = ($("#hidHospitalIDSave").val() == "") ? "POST" : "PUT";

	$.ajax({
		url : "HospitalApi",
		type : type,
		data : $("#formHospital").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onHospitalSaveComplete(response.responseText, status);
		}
	});

});

function onHospitalSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divHospitalsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}

	$("#hidHospitalIDSave").val("");
	$("#formHospital")[0].reset();

}

// UPDATE==========================================
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidHospitalIDSave").val(
					$(this).closest("tr").find('#hidHospitalIDUpdate').val());
			$("#hospitalID").val($(this).closest("tr").find('td:eq(0)').text());
			$("#hospitalName").val(
					$(this).closest("tr").find('td:eq(1)').text());
			$("#hospitalAddress").val(
					$(this).closest("tr").find('td:eq(2)').text());
			$("#hospitalDesc")
					.val($(this).closest("tr").find('td:eq(3)').text());
			$("#hospitalContact").val($(this).closest("tr").find('td:eq(4)').text());
		});

// REMOVE=======
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "HospitalApi",
		type : "DELETE",
		data : "id=" + $(this).data("id"),
		dataType : "text",
		complete : function(response, status) {
			onHospitalDeleteComplete(response.responseText, status);
		}
	});
});

function onHospitalDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);

		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divHospitalsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting.");
		$("#alertError").show();
	}

}

// CLIENTMODEL=========================================================================
function validateHospitalForm() {
	
	if ($("#hospitalID").val().trim() == "") {
		return "Insert hospital id.";
	}
	
	
	if ($("#hospitalName").val().trim() == "") {
		return "Insert Hospital Name.";
	}
	
	
	if ($("#hospitalAddress").val().trim() == "") {
		return "Insert hospital Address.";
	}

	
	if ($("#hospitalDesc").val().trim() == "") {
		return "Insert hospital description.";
	}

	
	if ($("#hospitalContact").val().trim() == "") {
		return "Insert telephone number.";
	}


	var tmpTel = $("#hospitalContact").val().trim();
	if (!$.isNumeric(tmpTel)) {
		return "Insert a numerical value for Telephone number.";
	}

	return true;
}