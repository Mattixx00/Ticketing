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
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public login() {
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
		doGet(request, response);
		
	     try {
             // Carica il driver JDBC per il database
             Class.forName("com.mysql.cj.jdbc.Driver");
             PreparedStatement stmt= null;
             // Ottieni la connessione al database
             String url = "jdbc:mysql://localhost:3306/ticketing";
             String user = "root";
             String password = "";
             Connection conn = DriverManager.getConnection(url, user, password);

             // Esegui la query per inserire i dati nella tabella della registrazione
             String sql = "SELECT * FROM utente WHERE username=? and password=?";
             stmt = conn.prepareStatement(sql);
             stmt.setString(1, request.getParameter("username"));
             stmt.setString(1, request.getParameter("password"));
             ResultSet rowsInserted = stmt.executeQuery();
             
             while (rs.next()) {
            	 
             }
         }
	}

}
