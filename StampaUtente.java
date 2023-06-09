package com.example.login;

import java.io.*;
import java.sql.*;

import com.google.gson.JsonObject;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "StampaUtente", value = "/StampaUtente")
public class StampaUtente extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.getWriter().append("Served at: ").append(request.getContextPath());

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");

        PrintWriter out = response.getWriter();

        int IdUtente = Integer.parseInt(request.getParameter("ID"));

        Connection conn = null;
        PreparedStatement stmt = null;
        JsonObject Utente = new JsonObject();


        // Carica il driver JDBC per il database
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Ottieni la connessione al database
        String url = "jdbc:mysql://localhost:3306/ticketing";
        String user = "root";
        String password = "";
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String sql = "SELECT * FROM utente WHERE id=?";
        try {
            stmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            stmt.setInt(1, IdUtente);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet rs;
        try {
            rs = stmt.executeQuery();
            if(rs.next()) {
                Utente.addProperty("status", "success");
                Utente.addProperty("username", rs.getString("username"));
                Utente.addProperty("email", rs.getString("email"));
                Utente.addProperty("nome", rs.getString("nome"));
                Utente.addProperty("cognome", rs.getString("cognome"));
                Utente.addProperty("anno_classe", rs.getString("anno_classe"));
                Utente.addProperty("sezione", rs.getString("sezione"));
                Utente.addProperty("indirizzo", rs.getString("indirizzo"));
                Utente.addProperty("zona_geografica", rs.getString("zona_geografica"));

            }else{
                Utente.addProperty("status","utente non esistente");
            }
        } catch (SQLException e) {
            Utente.addProperty("status","failure");
            throw new RuntimeException(e);
        }



        out.print(Utente.toString());
        out.flush();


    }

    public void destroy() {
    }
}