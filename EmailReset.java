package it.ProgettoNSI;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;

import com.google.gson.JsonObject;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

/**
 * Servlet implementation class EmailReset
 */
public class EmailReset extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmailReset() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("gagada");	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	//	response.setContentType("application/json");
		response.addHeader("Access-Control-Allow-Origin", "*");

		PrintWriter out = response.getWriter();
		
		String RandomPass = RandomStringUtils.randomAlphabetic(10);
		log(RandomPass);
		String CryptedRandomPW = BCrypt.hashpw(RandomPass, BCrypt.gensalt());
		String emailUser = request.getParameter("email");
		JsonObject toSend= new JsonObject();
		int id = -1;
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setDatabaseName("ticketing");
		
		String FindID="SELECT ID FROM utente where email='" + emailUser + "';" ;
		log(FindID);
		String AddToTable ="INSERT INTO change_pass (Id_utente, pass) VALUES (? , ?);";
		try(Connection conn = dataSource.getConnection("root", ""); Statement stmt = conn.createStatement()){
		
			ResultSet rs = stmt.executeQuery(FindID);
			while(rs.next()) {
				id = rs.getInt("ID");
			}
			if(id != -1) {
				
			
			PreparedStatement pstmt = conn.prepareStatement(AddToTable);
			pstmt.setInt(1, id);
			pstmt.setString(2, CryptedRandomPW);
			pstmt.executeUpdate();
			

			Email from = new Email("recoveryssender36@gmail.com");
		    String subject = "La tua password è stata cambiata";
		    Email to = new Email(emailUser);//request.getParameter("email")
		    Content content = new Content("text/plain", "La tua password è stata cambiata a questo codice, usala per il prossimo login " + RandomPass);
		    Mail mail = new Mail(from, subject, to, content);
		   

		    SendGrid sg = new SendGrid("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");
		    Request Mailrequest = new Request();
		    try {
		      Mailrequest.setMethod(Method.POST);
		      Mailrequest.setEndpoint("mail/send");
		      Mailrequest.setBody(mail.build());
		      Response Mailresponse = sg.api(Mailrequest);
		      System.out.println(Mailresponse.getStatusCode());
		      System.out.println(Mailresponse.getBody());
		      System.out.println(Mailresponse.getHeaders());
		      toSend.addProperty("SendMailStatus", "success");
		    } catch (IOException ex) {
		    	toSend.addProperty("SendMailStatus", "failure");
		    	}
		    
			}else {
		    	toSend.addProperty("SendMailStatus", "failure");

			}
			
			
			}catch(Exception ex) {
				toSend.addProperty("SendMailStatus", "failure");
				}
			
		
	
	    
	   out.append(toSend.toString());
	    
	  //  out.flush();
	
	}

}
