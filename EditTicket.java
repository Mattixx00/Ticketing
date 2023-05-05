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
		PrintWriter out = response.getWriter();
	int IDTicket = Integer.parseInt(request.getParameter("ID"));
	String materia = request.getParameter("Materia");
	String descrizione = request.getParameter("Descrizione");
	MysqlDataSource datasource = new MysqlDataSource();
	datasource.setDatabaseName("ticketing");
	String sql = "UPDATE tickets SET Materia=?, descrizione=? WHERE id=?";
	JsonObject modifiedticket = new JsonObject();
		    try (Connection conn = datasource.getConnection("root", "");  PreparedStatement pstmt= conn.prepareStatement(sql);) {
		        
		        pstmt.setString(1, materia);
		        pstmt.setString(2, descrizione);
		        pstmt.setInt(3, IDTicket);
		        int success=pstmt.executeUpdate();
				if(success>0) {
					modifiedticket.addProperty("editTicket-status", "successful");
					modifiedticket.addProperty("Materia", materia);
					modifiedticket.addProperty("Descrizione", descrizione);
				}else {
					modifiedticket.addProperty("edit-status", "failure");
				}
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    }
		    out.append(modifiedticket.toString());
		    
	}

}
