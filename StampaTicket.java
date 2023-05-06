package it.ProgettoNSI;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * Servlet implementation class StampaTicket
 */
public class StampaTicket extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StampaTicket() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		MysqlDataSource datasource = new MysqlDataSource();
		datasource.setDatabaseName("ticketing");
		JsonObject toSend = new JsonObject();
		JsonArray ArrTicket = new JsonArray();
		String qry="SELECT * FROM ticket WHERE ID_utente=?";
		try(Connection conn = datasource.getConnection("root", ""); PreparedStatement pstmt = conn.prepareStatement(qry)) {
			pstmt.setString(1,request.getParameter("ID_Utente"));
			ResultSet rs = pstmt.executeQuery();
			if(!rs.next())
			{
				toSend.addProperty("send-status", "failure");
			}else {
			while(rs.next()) {
				JsonObject TicketJson = new JsonObject();
				int IDTicket = rs.getInt("ID");
				String materia = rs.getString("Materia");
				String descrizione = rs.getString("Descrizione");
				TicketJson.addProperty("ID", IDTicket);
				TicketJson.addProperty("Materia",materia);
				TicketJson.addProperty("Descrizione",descrizione);
				ArrTicket.add(TicketJson);
				
			
			toSend.addProperty("send-status", "success");
			toSend.add("Tickets", ArrTicket);
			}
			}
			
			
			response.getWriter().append(toSend.toString());
			
				
			}catch(Exception ex) {System.err.println(ex.getMessage());}
	}	
			
	}


