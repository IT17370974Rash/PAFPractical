<%@page import="hospital.model.Hospital"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Hospital Service</title>

<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.5.1.min.js"></script>
<script src="Components/hospital.js"></script>
</head>
<body id="body">
	<div class="imagetag"></div>
	<div class="container">

		<div class="row">
			<div class="col-6">

				<h1 id="head">ADD NEW HOSPITALS</h1>

				<form id="formHospital" name="formHospital">

					Hospital ID: <input id="hospitalID" name="hospitalID" type="text"
						class="form-control form-control-sm"> 
						
					<br> Hospital Name:
					<input id="hospitalName" name="hospitalName" type="text"
						class="form-control form-control-sm"> 
						
					<br> Hospital Address: 
					<input id="hospitalAddress" name="hospitalAddress" type="text"
						class="form-control form-control-sm"> 
						
					<br> Hospital Description: 
					<input id="hospitalDesc" name="hospitalDesc" type="text"
						class="form-control form-control-sm"> 
						
					<br> Contact Number: 
					<input id="hospitalContact" name="hospitalContact" type="text"
						class="form-control form-control-sm"> 
						
					<br> 
					<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary"> 
						
					<input type="hidden" id="hidHospitalIDSave" name="hidHospitalIDSave" value="">


				</form>


				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>


				<div id="divHospitalsGrid">
					<%
						Hospital hospitalObj = new Hospital();
					out.print(hospitalObj.readHospitals());
					%>
				</div>
			</div>
		</div>
	</div>

</body>
</html>