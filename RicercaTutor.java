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

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * Servlet implementation class RicercaTutor
 */
public class RicercaTutor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RicercaTutor() {
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
		JsonArray arrTutor=new JsonArray();
		JsonObject toSend = new JsonObject();
		String materia = request.getParameter("Materia");
		log(materia);
		PrintWriter out = response.getWriter();
		
		MysqlDataSource dataSource = new MysqlDataSource();
		dataSource.setDatabaseName("ticketing2");
		if(materia.equals("NOFILTRI")) { //Controllo se la materia è null se così esegueo un tipo di query
		String qry="SELECT u.ID,u.Nome,u.Cognome,u.email,u.anno_classe,u.Sezione,t.Materia,t.Descrizione"
				+ " FROM ticket t inner join utente u on t.ID_utente=u.ID;";
		try(Connection conn = dataSource.getConnection("root", ""); Statement stmt = conn.createStatement()){
			try {
			ResultSet rs = stmt.executeQuery(qry);
			
			while(rs.next()) {
				JsonObject ticket = new JsonObject();
				int IDTutor = rs.getInt("ID");
				String Nome = rs.getString("Nome");
				String Cognome = rs.getString("Cognome");
				String Email = rs.getString("email");
				int anno_classe = rs.getInt("anno_classe");
				String Sezione = rs.getString("Sezione");
				String Materia = rs.getString("Materia");
				String Descrizione = rs.getString("Descrizione");
				ticket.addProperty("IDTutor", IDTutor);
				ticket.addProperty("Nome", Nome);
				ticket.addProperty("Cognome", Cognome);
				ticket.addProperty("Email", Email);
				ticket.addProperty("anno_classe", anno_classe);
				ticket.addProperty("Sezione", Sezione);
				ticket.addProperty("Materia", Materia);
				ticket.addProperty("Descrizione", Descrizione);
				arrTutor.add(ticket);						 						
			}
			
			toSend.addProperty("FoundStatus", "successful");
			toSend.add("Tutors",arrTutor);
			
			}catch(Exception e) {
				toSend.addProperty("FoundStatus", "error");
			}
			log(toSend.toString());
			out.append(toSend.toString());
			
			
			
		}catch(Exception e) {
			toSend.addProperty("FoundStatus", "error");
		}
		
			
		}else {
			String qry="SELECT u.ID,u.Nome,u.Cognome,u.email,u.anno_classe,u.Sezione,t.Materia,t.Descrizione"
					+ " FROM ticket t inner join utente u on t.ID_utente=u.ID where materia=?";
			try(Connection conn = dataSource.getConnection("root", ""); PreparedStatement stmt = conn.prepareStatement(qry)){
				stmt.setString(1, materia);
			
			try {
				ResultSet rs = stmt.executeQuery();
			
				while(rs.next()) {
					JsonObject ticket = new JsonObject();
					int IDTutor = rs.getInt("ID");
					String Nome = rs.getString("Nome");
					String Cognome = rs.getString("Cognome");
					String Email = rs.getString("email");
					int anno_classe = rs.getInt("anno_classe");
					String Sezione = rs.getString("Sezione");
					String Materia = rs.getString("Materia");
					String Descrizione = rs.getString("Descrizione");
					ticket.addProperty("IDTutor", IDTutor);
					ticket.addProperty("Nome", Nome);
					ticket.addProperty("Cognome", Cognome);
					ticket.addProperty("Email", Email);
					ticket.addProperty("anno_classe", anno_classe);
					ticket.addProperty("Sezione", Sezione);
					ticket.addProperty("Materia", Materia);
					ticket.addProperty("Descrizione", Descrizione);
					arrTutor.add(ticket);												
				}
				
				toSend.addProperty("Found", "successful");
				toSend.add("Tutors",arrTutor);
				
				
			}catch(Exception e) {
				toSend.addProperty("Found", "error");
			}
				log(toSend.toString());
				out.append(toSend.toString());
				
				
				
			}catch(Exception e) {
				toSend.addProperty("Found", "error");
			}
			
			
		}
		

}
	
}
