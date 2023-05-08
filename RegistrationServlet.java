package com.example.registrazione;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class Registration extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.addHeader("Access-Control-Allow-Origin", "*");

        // Imposta l'intestazione della risposta come JSON
        response.setContentType("application/json");

        // Leggi i dati dal POST
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String cognome = request.getParameter("cognome");
        int anno_classe = Integer.parseInt(request.getParameter("anno_classe"));
        String sezione = request.getParameter("sezione");
        String indirizzo = request.getParameter("indirizzo");

        JsonObject jsonResponse = new JsonObject();

        boolean ControlUser = ControlUsername(username);

        if (ControlUser) {

            jsonResponse.addProperty("status-registration", "failed");
            jsonResponse.addProperty("problem", "200");
            PrintWriter out = response.getWriter();
            out.print(jsonResponse.toString());
            out.flush();

        } else {


            // Effettua la registrazione
            boolean registrationSuccess = register(username, name, email, password, cognome, anno_classe, sezione, indirizzo);

            // Crea un oggetto JSON per la risposta
            JsonObject jonny = new JsonObject();
            jonny.addProperty("status-registration", registrationSuccess ? "success" : "failed");

            // Scrivi la risposta come JSON
            PrintWriter out = response.getWriter();
            out.print(jonny.toString());
            out.flush();
        }



    }
    private boolean ControlUsername (String username){
        boolean Control = false;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Carica il driver JDBC per il database
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Ottieni la connessione al database
            String url = "jdbc:mysql://localhost:3306/ticketing";
            String user = "root";
            String password = "";
            conn = DriverManager.getConnection(url, user, password);

            // Esegui la query per inserire i dati nella tabella della registrazione
            String sql = "SELECT ID FROM utente WHERE username=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);

            try{
                stmt.executeQuery();


            } catch (SQLException e) {
                Control=true;
                // Si è verificato un errore durante l'esecuzione della query
                e.printStackTrace();
            }


        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Chiudi le risorse del database
            try {
                if (stmt != null) stmt.close();
            } catch (Exception ex) {
            }
            try {
                if (conn != null) conn.close();
            } catch (Exception ignored) {
            }
        }

        return Control;
    }
    private boolean register (String username, String name, String email, String password, String cognome,
                              int anno_classe, String sezione, String indirizzo){
        boolean registrationSuccess = true;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Carica il driver JDBC per il database
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Ottieni la connessione al database
            String url = "jdbc:mysql://localhost:3306/ticketing";
            String user = "root";
            String pass = "";
            conn = DriverManager.getConnection(url, user, pass);

            // Esegui la query per inserire i dati nella tabella della registrazione
            String sql = "INSERT INTO utente (ID,Username,Password,Nome,Cognome,email,anno_classe,Sezione,indirizzo,tipo_user) VALUES (NULL,?,?,?,?,?,?,?,?,'guest')";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, name);
            stmt.setString(4, cognome);
            stmt.setString(5, email);
            stmt.setInt(6, anno_classe);
            stmt.setString(7, sezione);
            stmt.setString(8, indirizzo);
            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                registrationSuccess = true;
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Chiudi le risorse del database
            try {
                if (stmt != null) stmt.close();
            } catch (Exception ex) {
            }
            try {
                if (conn != null) conn.close();
            } catch (Exception ex) {
            }
        }

        return registrationSuccess;
    }
}