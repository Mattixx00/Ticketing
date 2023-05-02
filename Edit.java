package it.ProgettoNSI;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Edit
 */
public class Edit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Edit() {
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
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setDatabaseName("ticketing");
		//response.getWriter().append( ",");
		String Nome=request.getParameter("Nome");
		String Cognome=request.getParameter("Cognome");
		String email=request.getParameter("email");
		String anno_classe=request.getParameter("anno_classe");
		String sezione=request.getParameter("Sezione");
		String indirizzo=request.getParameter("Indirizzo");
		//L'ID sarà passato in sessione
		JsonObject Resp = new JsonObject();
		String qry="UPDATE utente SET Nome=?, Cognome =?, email =?, anno_classe=? ,Sezione=?,Indirizzo=? WHERE ID=1";
		try(Connection conn = dataSource.getConnection("root", ""); PreparedStatement pstmt = conn.prepareStatement(qry)) {
		pstmt.setString(1,Nome);
		pstmt.setString(2,Cognome);
		pstmt.setString(3,email);
		pstmt.setString(4,anno_classe);
		pstmt.setInt(5,Integer.parseInt(sezione));
		pstmt.setString(6,indirizzo);
		int success=pstmt.executeUpdate();
		if(success>0) {
			Resp.addProperty("edit-status", "success");
		}else {
			Resp.addProperty("edit-status", "failure");
		}
		
		response.getWriter().append(Resp.toString());
		
			
		}catch(Exception ex) {System.err.println(ex.getMessage());}{
			
		}
	}

}
	
	
