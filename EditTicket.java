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
import java.sql.SQLException;
import java.sql.Statement;

import com.google.gson.JsonObject;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * Servlet implementation class EditTicket
 */
public class EditTicket extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditTicket() {
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
		response.addHeader("Access-Control-Allow-Origin", "*");
		JsonObject modifiedticket = new JsonObject();

		PrintWriter out = response.getWriter();
	
	String materia = request.getParameter("Materia");
	String descrizione = request.getParameter("Descrizione");
	String Disponibilita_Giorni = request.getParameter("Disponibilita_Giorni");
log(materia);
	MysqlDataSource datasource = new MysqlDataSource();
	datasource.setDatabaseName("ticketing");
	String sql = "UPDATE ticket SET Materia=?, Descrizione=?, Disponibilità_Giorni=? WHERE id=?";
		    try (Connection conn = datasource.getConnection("root", "");  PreparedStatement pstmt= conn.prepareStatement(sql);) {
		        try {
		        pstmt.setString(1, materia);
		        pstmt.setString(2, descrizione);
		        pstmt.setString(3, Disponibilita_Giorni);

		        pstmt.setInt(4, Integer.parseInt(request.getParameter("ID_Ticket")));
		        }catch(Exception e) {
		        	modifiedticket.addProperty("EditTicketStatus", "failure");
		        }
		        int success=pstmt.executeUpdate();
				if(success>0) {
				
					modifiedticket.addProperty("EditTicketStatus", "successful");
					modifiedticket.addProperty("Materia", materia);
					modifiedticket.addProperty("Descrizione", descrizione);
					modifiedticket.addProperty("Disponibilità_Giorni",Disponibilita_Giorni );
					log("d");
				}else {
					modifiedticket.addProperty("EditTicketStatus", "failure");
				}
		    } catch (SQLException ex) {
		    	modifiedticket.addProperty("EditTicketStatus", "failure");
		    }
		    log(modifiedticket.toString());
		    out.append(modifiedticket.toString());
		    
	}

}
