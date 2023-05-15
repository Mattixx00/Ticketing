package it.ProgettoNSI;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.google.gson.JsonObject;

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
				stmt=conn.prepareStatement(sql);
				stmt.setString(1,username);
				rs=stmt.executeQuery();

	            while(rs.next()) {
					int id_utente=rs.getInt("ID");
	                toCheck = rs.getString("Password");
	            }

				sql = "SELECT * FROM change_pass WHERE Id_utente=?";
				stmt=conn.prepareStatement(sql);
				stmt.setString(1,id_utente);
				rs=stmt.executeQuery();

				if (rs.next()) {
					jsonResponse.addProperty("LoginStatus", "change password");
				}
				else {
					String sql = "SELECT * FROM utente WHERE Username=?";
					stmt=conn.prepareStatement(sql);
					stmt.setString(1,username);
					rs=stmt.executeQuery();

					if (BCrypt.checkpw(password, toCheck)) {
						jsonResponse.addProperty("LoginStatus", "success");
						jsonResponse.addProperty("ID", rs.getInt("ID"));
						jsonResponse.addProperty("Username", rs.getString("Username"));
						jsonResponse.addProperty("Nome", rs.getString("Nome"));
						jsonResponse.addProperty("Cognome", rs.getString("Cognome"));
						jsonResponse.addProperty("anno_classe", rs.getInt("anno_classe"));
						jsonResponse.addProperty("Sezione", rs.getString("Sezione"));
						jsonResponse.addProperty("zona_geografica", rs.getString("zona_geografica"));

					} else {
						jsonResponse.addProperty("LoginStatus", "failure");
					}
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
