package it.avbo.progettoNSI;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.google.gson.JsonObject;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;


public class DeleteStudentServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
						//recupera l'ID dello studente dalla request
		int ID_studente=Integer.parseInt(request.getParameter("ID_Utente"));
		response.addHeader("Access-Control-Allow-Origin", "*");

						// Elimina lo studente dal database
		boolean success = deleteStudent(ID_studente);
		
		JsonObject jsonR = new JsonObject();
		if(success) {
			jsonR.addProperty("DeleteUserstatus", "successful");
		} else {
			jsonR.addProperty("DeleteUserstatus", "error");
		}
		
															//mandare la risposta al client
		response.getWriter().println(jsonR.toString());
		response.getWriter().flush();
		// response.sendRedirect("https://localhost/pj/DeleteUser"); (?)
	}
	
	private boolean deleteStudent(int ID_studente) {
		boolean success=true;
		
		
		try {
			MysqlDataSource ds = new MysqlDataSource();
			ds.setUrl("jdbc:mysql://localhost:3306/ticketing2");
			
			Connection conn = ds.getConnection("root","");
			
			String query="DELETE FROM class WHERE ID_Studente=?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, ID_studente);
            int RowsAffected = stmt.executeUpdate();
            if(RowsAffected < 1) {
            	success = false;
            }
            
		} catch (SQLException e) {
			success=false;
			e.printStackTrace();
		}
		
		return success;
	}

}