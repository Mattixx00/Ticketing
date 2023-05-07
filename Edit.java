package it.ProgettoNSI;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
		response.addHeader("Access-Control-Allow-Origin", "*");

		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setDatabaseName("ticketing2");
		//response.getWriter().append( ",");
		String ID=request.getParameter("ID");
		String Nome=request.getParameter("Nome");
		String Cognome=request.getParameter("Cognome");
		String email=request.getParameter("email");
		String anno_classe=request.getParameter("anno_classe");
		String sezione=request.getParameter("Sezione");
		String indirizzo=request.getParameter("Indirizzo");
		//L'ID sar� passato in sessione
		JsonObject Resp = new JsonObject();
		String qry="UPDATE utente SET Nome=?, Cognome =?, email =?, anno_classe=? ,Sezione=?,tipo_utente=? WHERE ID=?";
		try(Connection conn = dataSource.getConnection("root", ""); PreparedStatement pstmt = conn.prepareStatement(qry)) {
		try {
		pstmt.setString(1,Nome);
		pstmt.setString(2,Cognome);
		pstmt.setString(3,email);
		pstmt.setInt(4,Integer.parseInt(anno_classe));
		pstmt.setString(5,sezione);
		pstmt.setString(6,indirizzo);
		pstmt.setInt(7,Integer.parseInt(ID));
		int success=pstmt.executeUpdate();
		if(success>0) {
			Resp.addProperty("editstatus", "success");
		}else {
			Resp.addProperty("editstatus", "failure");
		}
		}catch(SQLException ex) {
			Resp.addProperty("editstatus", "failure");

		}
		
		
	
		
			
		}catch(Exception ex) {Resp.addProperty("editstatus", "failure");}
		response.getWriter().append(Resp.toString());
		
		
	}

}
	
	
