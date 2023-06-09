package com.example.demo1;

import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(name = "Login", value = "/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
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

		// Imposta l'intestazione della risposta come JSON
		response.setContentType("application/json");

		// Leggi i dati dal POST
		String username = request.getParameter("Username");
		String password = request.getParameter("Password");
		String toCheck = "";
		// Cerca l'utente nel database
		JsonObject jsonResponse = new JsonObject();
		try {
			Connection conn = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;

			// Carica il driver JDBC per il database
			Class.forName("com.mysql.jdbc.Driver");


			// Ottieni la connessione al database
			String url = "jdbc:mysql://localhost:3306/ticketing";
			String user = "root";
			String dbPassword = "";
			conn = DriverManager.getConnection(url, user, dbPassword);

			// Esegui la query per cercare l'utente

			String sql = "SELECT * FROM utente WHERE Username=?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			rs = stmt.executeQuery();

			int id_utente;

			if(rs.next()) {

				id_utente = rs.getInt("ID");
				toCheck = rs.getString("Password");
				username=rs.getString("Username");
				String nome=rs.getString("Nome");
				String cognome=rs.getString("Cognome");
				String email=rs.getString("email");
				int anno=rs.getInt("anno_classe");
				String sezione=rs.getString("Sezione");
				String zona=rs.getString("zona_geografica");

				sql = "SELECT * FROM change_pass WHERE id_utente=?";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, id_utente);
				rs = stmt.executeQuery();
				jsonResponse.addProperty("rr", "change password2");

				if (rs.next()) {
					if(BCrypt.checkpw(password, toCheck)) {
						jsonResponse.addProperty("LoginStatus", "change password");
						jsonResponse.addProperty("id_utente", id_utente);
					}
				} else {

					if (BCrypt.checkpw(password, toCheck)) {
						jsonResponse.addProperty("LoginStatus", "success");
						jsonResponse.addProperty("ID", id_utente);
						jsonResponse.addProperty("Username", username);
						jsonResponse.addProperty("Nome",nome);
						jsonResponse.addProperty("Cognome",cognome);
						jsonResponse.addProperty("email", email);
						jsonResponse.addProperty("anno_classe",anno);
						jsonResponse.addProperty("Sezione",sezione);
						jsonResponse.addProperty("zona_geografica", zona);

					} else {
						jsonResponse.addProperty("LoginStatus", "password errata");
					}
				}
			}else{
				jsonResponse.addProperty("LoginStatus", "username o password errati");
			}

			//}
		} catch (SQLException ex) {
			ex.printStackTrace();
			jsonResponse.addProperty("status", "error");
			//     jsonResponse.addProperty("message", "An error occurred while processing your request");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Scrivi la risposta come JSON
		PrintWriter out = response.getWriter();
		out.print(jsonResponse.toString());
		out.flush();

	}

}
