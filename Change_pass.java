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
@WebServlet(name = "Change_pass", value = "/Change_pass")
public class Change_pass extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Change_pass() {
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
        int id_utente = Integer.parseInt(request.getParameter("id_utente"));
        String password = request.getParameter("Password");

        // Cerca l'utente nel database
        JsonObject jsonResponse = new JsonObject();
        try {
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            // Carica il driver JDBC per il database
            Class.forName("com.mysql.cj.jdbc.Driver");


            // Ottieni la connessione al database
            String url = "jdbc:mysql://localhost:3306/ticketing";
            String user = "root";
            String dbPassword = "";
            conn = DriverManager.getConnection(url, user, dbPassword);

            // Esegui la query per cercare l'utente

            String sql = "UPDATE utente SET Password=? WHERE ID=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, password);
            stmt.setInt(2, id_utente);
            int row = stmt.executeUpdate();


            if (row > 0) {

                sql = "DELETE FROM change_pass WHERE id_utente=?";
                stmt = conn.prepareStatement(sql);
                stmt.setInt(1, id_utente);
                row = stmt.executeUpdate();

                if (row > 0) {
                    jsonResponse.addProperty("change_pass", "successful");
                }


            }else{
                jsonResponse.addProperty("change_pass", "failure");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        // Scrivi la risposta come JSON
        PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
        out.flush();


    }
}
