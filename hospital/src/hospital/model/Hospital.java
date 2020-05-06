package hospital.model;

import java.sql.*;

public class Hospital{ 
	
	// A common method to connect to the DB
	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/healthcare?serverTimezone=UTC", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	public String insertHospital(String code, String name, String address, String desc, String contact) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into hospital(`id`,`hospitalID`,`hospitalName`,`hospitalAddress`,`hospitalDesc`,`hospitalContact`)"
					+ " values (?,?, ?, ?, ?, ?)";
			
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, code);
			preparedStmt.setString(3, name);
			preparedStmt.setString(4, address);
			preparedStmt.setString(5, desc);
			preparedStmt.setInt(6, Integer.parseInt(contact));
			
			
			//execute the statement
			preparedStmt.execute();
			con.close();
			
			String newHospitals = readHospitals();
			output = "{\"status\":\"success\", \"data\": \"" + newHospitals + "\"}"; 
			
			
		
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the hospital.\"}"; 
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String readHospitals() {
		
		String output = "";
		
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}

			// Prepare the html table to be displayed
			 output = "<table border='1'><tr>"
						+ "<th>Hospital Code</th>"
						+ "<th>Hospital Name</th>"
						+ "<th>Address</th>"
						+ "<th>Description</th>"
						+ "<th>Contact Number</th>"
						+ "<th>UPDATE</th>"
						+ "<th>REMOVE</th>"
						+ "</tr>"; 
			 
			String query = "select * from hospital";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			
			// iterate through the rows in the result set
			while (rs.next()) {
				String id = Integer.toString(rs.getInt("id")); 
				String hospitalID = rs.getString("hospitalID");
				String hospitalName = rs.getString("hospitalName");
				String hospitalAddress = rs.getString("hospitalAddress");
				String hospitalDesc = rs.getString("hospitalDesc");
				String hospitalContact = Integer.toString(rs.getInt("hospitalContact"));
				

				// Add into the html table
				output += "<tr><td><input id='hidHospitalIDUpdate' name='hidHospitalIDUpdate' type='hidden' "
						+ "value='" + id + "'>" + hospitalID + "</td>"; 
				output += "<td>" + hospitalName + "</td>";
				output += "<td>" + hospitalAddress + "</td>";
				output += "<td>" + hospitalDesc + "</td>";
				output += "<td>" + hospitalContact + "</td>";

				output += "<td><input name='btnUpdate' type='button' value='Update'class='btnUpdate btn btn-secondary'></td>"
						 + "<td><input name='btnRemove' type='button' value='Remove'class='btnRemove btn btn-danger'data-id='"
						 + id + "'>" + "</td></tr>";
			}
			
			con.close();

			// Complete the html table
			output += "</table>";
			
		} catch (Exception e) {
			output = "Error while reading the items.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	public String updateHospital(String hosid, String code, String name, String address, String desc, String contact) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE hospital SET hospitalID=? , hospitalName=? , hospitalAddress=?, hospitalDesc=?, hospitalContact=? where id=? ";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, code);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, address);
			preparedStmt.setString(4, desc);
			preparedStmt.setInt(5, Integer.parseInt(contact));
			preparedStmt.setInt(6, Integer.parseInt(hosid));

			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newHospitals = readHospitals();
			 output = "{\"status\":\"success\", \"data\": \"" +
					 newHospitals + "\"}"; 
		}
		
		catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while updating the hospital.\"}"; 
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteHospital(String id) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from hospital where id=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(id));

			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newHospitals = readHospitals();
			 output = "{\"status\":\"success\", \"data\": \"" + newHospitals + "\"}";
			
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the hospital.\"}"; 
			System.err.println(e.getMessage()); 
		}
		return output;
	}

}
