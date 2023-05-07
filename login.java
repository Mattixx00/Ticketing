package it.avbo.progettoNSI;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Servlet implementation class login
 */
@WebServlet("/login") // specifica l'URL a cui la servlet risponde
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LoginServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	     try {
             // Carica il driver JDBC per il database
             Class.forName("com.mysql.cj.jdbc.Driver");
             
             // Ottieni la connessione al database
             String url = "jdbc:mysql://localhost:3306/ticketing";
             String user = "root";
             String password = "";
             Connection conn = DriverManager.getConnection(url, user, password);

             // Esegui la query per verificare le credenziali dell'utente
             String sql = "SELECT * FROM utente WHERE username=? and password=?";
             PreparedStatement stmt = conn.prepareStatement(sql);
             stmt.setString(1, request.getParameter("username"));
             stmt.setString(2, request.getParameter("password"));
             ResultSet rs = stmt.executeQuery();
             
             if (rs.next()) {
                 // Le credenziali sono valide, reindirizza alla pagina di benvenuto
                 response.sendRedirect("welcome.jsp");
             } else {
                 // Le credenziali non sono valide, mostra un messaggio di errore
                 response.sendRedirect("login.jsp?error=1");
             }
             
             // Chiudi le risorse
             rs.close();
             stmt.close();
             conn.close();
             
         } catch (Exception e) {
             // In caso di errore, mostra un messaggio di errore generico
             response.sendRedirect("login.jsp?error=2");
             e.printStackTrace();
         }
	}

}
