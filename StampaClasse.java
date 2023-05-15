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
import java.sql.SQLException;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * Servlet implementation class StampaStudenti
 */
public class StampaClasse extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StampaClasse() {
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
		JsonArray ArrInQuery = new JsonArray();
		String qry="SELECT u.ID,u.nome, u.cognome, u.anno_classe,u.Sezione,u.zona_geografica, a.materia,a.Descrizione,a.ID as ID_Ticket from Utente u inner join (SELECT c.ID_Studente,t.materia,t.ID,t.Descrizione FROM utente u inner join ticket t on u.ID=t.id_Utente inner join class c on c.ID_Ticket=t.ID and u.ID =1 and c.QueryStatus= 'iNCODA') a on u.ID = a.ID_Studente;";
		log(qry);
		log(request.getParameter("ID_Tutor"));
		try(Connection conn = datasource.getConnection("root", ""); PreparedStatement pstmt = conn.prepareStatement(qry)) {
			try {
			pstmt.setInt(1,Integer.parseInt(request.getParameter("ID_Tutor")));
			}catch(Exception e) {
				toSend.addProperty("PrintClassStatus", "failure");
			}
			
	
			try {
				ResultSet rs = pstmt.executeQuery();
				toSend.addProperty("PrintClassStatus", "successful");
			
			while(rs.next()) {
				
				JsonObject StudentInQuery = new JsonObject();
				int IDStudent = rs.getInt("ID");				
				String nome = rs.getString("nome");
				String cognome = rs.getString("cognome");
				int anno_classe = rs.getInt("anno_classe");
				String Sezione = rs.getString("Sezione");
				String zona_geografica = rs.getString("zona_geografica");
				String materia = rs.getString("materia");
				int ID_Ticket = rs.getInt("ID_Ticket");
				String descrizione = rs.getString("Descrizione");

				
				StudentInQuery.addProperty("ID_Student", IDStudent);
				StudentInQuery.addProperty("Nome",nome);
				StudentInQuery.addProperty("Cognome",cognome);
				StudentInQuery.addProperty("anno_classe",anno_classe);
				StudentInQuery.addProperty("Sezione",Sezione);
				StudentInQuery.addProperty("zona_geografica",zona_geografica);
				StudentInQuery.addProperty("materia",materia);
				StudentInQuery.addProperty("Descrizione", descrizione);
				StudentInQuery.addProperty("ID_Ticket",ID_Ticket);							
				ArrInQuery.add(StudentInQuery);
			}
			
			
			toSend.add("Students", ArrInQuery);
			
			
			}catch(SQLException sqe) {
				
				toSend.addProperty("PrintClassStatus", "failure");
			}
			//}
			
		//	log(toSend.toString());
			
			
				
			
	}catch(SQLException sqe) {
		
		toSend.addProperty("PrintClassStatus", "failure");
	}
		response.getWriter().append(toSend.toString());		
	}
	}