package com;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import model.Admin;

@Path("/Admin")
public class AdminService {
	
	Admin adminObj = new Admin();
	Admin adminObj1 = new Admin();
	Admin adminObj2 = new Admin();
	
	@POST
	@Path("/addNotice")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN)
	public String insertAccountBuyer(@FormParam("ncontent") String ncontent, @FormParam("issuedate") String issuedate){ 
		
		String output = adminObj.insertNotice(ncontent, issuedate);
		
		
		return output; 
	}
	
	@GET
	@Path("/viewAllNotices") 
	@Produces(MediaType.TEXT_HTML) 
	public String viewNotices() { 
		return adminObj1.viewNotices(); 
	}
	
	@DELETE
	@Path("/removenotice") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String removeNotice(String noticedata) { 
		
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(noticedata, "", Parser.xmlParser()); 
	 
		String nID = doc.select("nId").text(); 
		String output = adminObj2.removeNotice(nID); 
	
		return output; 
	}
	
//	@PUT
//	@Path("/editnotice") 
//	@Consumes(MediaType.APPLICATION_JSON) 
//	@Produces(MediaType.TEXT_PLAIN) 
//	public String updatenotice(String noticeNew) { 
//	
//		//Convert the input string to a JSON object 
//		JsonObject adminOb = new JsonParser().parse(noticeNew).getAsJsonObject(); 
//	
//		//Read the values from the JSON object
//		String noticeContent = adminOb.get("ncontent").getAsString();
//		//String nID = adminOb.get("nid").getAsString();
//		//String issueDate = adminOb.get("issuedate").getAsString();
// 
//		
//		//String output = adminObj.updatenotice(nID, noticeContent,  issueDate); 
//		String output = adminObj.updatenotice(noticeContent);
//	
//		return output; 
//	}
	
	@PUT
	@Path("/editnotice") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updatenotice(String notice) 
	{ 
	//Convert the input string to a JSON object 
	 JsonObject itemObject = new JsonParser().parse(notice).getAsJsonObject(); 
	//Read the values from the JSON object
	 String nID = itemObject.get("nid").getAsString(); 
	 String ncontent = itemObject.get("ncontent").getAsString(); 
	 String issuedate = itemObject.get("issuedate").getAsString(); 
	 String output = adminObj.updatenotice(nID, ncontent, issuedate); 
	return output; 
	}

	
	
}
