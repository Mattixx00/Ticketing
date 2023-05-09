package it.ProgettoNSI;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * Servlet implementation class AcceptFromQuery
 */
public class AcceptFromQuery extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AcceptFromQuery() {
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
		MysqlDataSource datasource = new MysqlDataSource();
		datasource.setDatabaseName("ticketing");
		JsonObject toSend = new JsonObject();
		String qry="UPDATE class SET `QueryStatus` = 'Inclass' WHERE class.ID_Studente =? AND class.ID_Ticket=?";


		try(Connection conn = datasource.getConnection("root", ""); PreparedStatement pstmt = conn.prepareStatement(qry)) {
			try {
			pstmt.setInt(1,Integer.parseInt(request.getParameter("ID_Studente")));
			pstmt.setInt(2,Integer.parseInt(request.getParameter("ID_Ticket")));
			}catch(Exception e) {
				toSend.addProperty("AcceptFromQuerystatus", "error");
			}	
			log(request.getParameter("ID_Studente"));
			log(request.getParameter("ID_Ticket"));
			int success= pstmt.executeUpdate();
			if(success > 0) {
				toSend.addProperty("AcceptFromQuerystatus", "successful");

			}else {
				toSend.addProperty("AcceptFromQuerystatus", "error");

			}
			
			
		}catch(SQLException sqe) {
			toSend.addProperty("AcceptFromQuerystatus", "error");

		}
		response.getWriter().append(toSend.toString());	
}
}
