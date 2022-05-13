package model;

import java.sql.*;


public class Admin {
	
	
//connection to db
	public Connection connect() 
	{ 
	 Connection con = null; 
	 
	 try 
	 { 
	 Class.forName("com.mysql.jdbc.Driver"); 
	 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogrid", "root", ""); 
	 //For testing
	 System.out.println("Successfully connected to database"); 
	 } 
	 catch(Exception e) 
	 { 
	 e.printStackTrace(); 
	 } 
	 
	 return con; 
	} 
	
	
//===========================================================================================================
//===========================================================================================================
	

	//inserting
	public String insertNotice(String noticeContent, String issuedate) {
		String output = " ";
		String noticelimit= "^[A-Za-z0-9+_.-]{0}";
		try {
			
			Connection con = connect(); 
			 if (con == null) 
			 {return "Error while connecting to the database for inserting."; } 
			 // create a prepared statement
			 String query = " insert into notices(`nid`,`ncontent`,`issuedate`)"
			 + " values (?, ?, ?)"; 
			 PreparedStatement preparedStmt = con.prepareStatement(query); 
			 // binding values
			 preparedStmt.setInt(1, 0); 
			 preparedStmt.setString(2, noticeContent); 
			 preparedStmt.setString(3, issuedate); 
			 
			 if((noticeContent.matches(noticelimit))){
				 
				 output = "Can not insert empty notices";
				 
			 }else {
			 
			// execute the statement
			 preparedStmt.execute(); 
			 
			 con.close(); 
			 output = "Inserted successfully";
			 }
		}catch(Exception e){
			output = "Error in adding new notice";
			System.err.println(e.getMessage()); 
		}
		return output;
	}
	
	
	
//===========================================================================================================
//===========================================================================================================
	
	
	//View previous notices
	public String viewNotices() {
		String output = " ";
		
		try {
			
			Connection con = connect(); 
			
			if (con == null) 
			{return "Error while connecting to the database for reading."; }
			
			// create a HTML table to display values
			output = "<table border='2'><tr><th>Notice Id</th><th>Notice</th><th>Issued Date and Time</th><th>Update</th><th>Remove</th></tr>"; 
			String query = "select * from notices"; 
			Statement stmt = con.createStatement(); 
			ResultSet rs = stmt.executeQuery(query); 
			
			//Iteration through rows
			while (rs.next()) { 
				String nID = Integer.toString(rs.getInt("nid")); 
				String ncontent = rs.getString("ncontent"); 
				String issuedate = rs.getString("issuedate"); 
				
				// Inserting to HTML table
				output += "<tr><td>" + nID + "</td>"; 
				output += "<td>" + ncontent + "</td>"; 	
				output += "<td>" + issuedate + "</td>"; 	
				
				// buttons
//				output += "<td><form method='post' action='#'>" 
//				+ "<input name='btnRemove' type='submit' value='Remove' class='btn btn-danger'>" 
//				+ "<input name='finishedPID' type='hidden' value='Edit'" 
//				+ nID + "'>" + "</form></td></tr>";
				
				output += "<td><input name='btupdate'"
				+"type='button' value='Update' onclick=></td>"
				+"<td><form method='post' action=''>"
				+"<input name= 'btremove'"
				+"type='submit' value='Remove'>"
				+ "<input name = '' type = 'hidden' "
				+"value='" + nID + "'>"
				+"</form></td></tr>"; 
			}
				con.close(); 
				// Closing HTML table
				output += "</table>"; 
			
		}catch(Exception e) {
			output = "Error while reading notices."; 
			System.err.println(e.getMessage()); 
		}
		return output;
	}
	
//===========================================================================================================
//===========================================================================================================

	//Delete a notice
	public String removeNotice(String nID) 
	 { 
	 String output = "";
	 
	 try{
		 
	 Connection con = connect(); 
	 if (con == null)
	 {return "Error while connecting to the database for deleting."; } 
	 
	 // create a prepared statement
	 String query = "delete from notices where nid=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(nID));
	 
	 // execute the statement
	 preparedStmt.execute(); 
	 con.close(); 
	 output = "Relevant Notice Deleted successfully"; 
	 
	 }catch (Exception e){
		 
	 output = "Error while deleting the Notice"; 
	 System.err.println(e.getMessage()); 
	 }
	 
	 return output; 
	 }
	
//===========================================================================================================
//===========================================================================================================

	//public String updatenotice(String nID, String noticeContent, String issuedate) 
//	public String updatenotice(String noticeContent){ 
//		 
//		String output = ""; 
//	 
//		try { 
//			Connection con = connect(); 
//	 
//			if (con == null) {
//				return "Error while connecting to the database for updating."; } 
//	 
//			// create a prepared statement
//			String query = "UPDATE notices SET ncontent=?,issuedate=? 	where nid=?"; 
//			PreparedStatement preparedStmt = con.prepareStatement(query); 
//			
//			// binding values
//			
//			preparedStmt.setString(1, noticeContent); 
//			//preparedStmt.setString(2, issuedate); 
//			//preparedStmt.setInt(3, Integer.parseInt(nID)); 
//			
//			
//			// execute the statement
//			preparedStmt.execute(); 
//			con.close(); 
//			
//			output = "Updated successfully"; 
//		 } catch (Exception e) { 
//			 output = "Error while updating the details."; 
//			 System.err.println(e.getMessage()); 
//		 }
//		 return output; 
//	}
	
	public String updatenotice(String nID, String ncontent, String issuedate) {
		
		String output = ""; 
		
		 try{
			 
		 Connection con = connect(); 
		 if (con == null){
			 return "Error while connecting to the database for updating.";
			 } 
		 
		 // create a prepared statement
		 String query = "UPDATE notices SET ncontent=?,issuedate=? WHERE nid=?"; 
		 PreparedStatement preparedStmt = con.prepareStatement(query); 
		 
		 // binding values
		 preparedStmt.setString(1, ncontent); 
		 preparedStmt.setString(2, issuedate); 
		 preparedStmt.setInt(3, Integer.parseInt(nID)); 
		 
		 // execute the statement
		 preparedStmt.execute(); 
		 con.close(); 
		 output = "Updated successfully"; 
		 
		 }catch (Exception e){
			 
		 output = "Error while updating the notice."; 
		 System.err.println(e.getMessage());
		 
		 } 
		 return output; 
	}

}
