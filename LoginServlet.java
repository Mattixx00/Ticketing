package com.example.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "Login",value = "/login")
public class Login extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.getWriter().append("Served at: ").append(request.getContextPath());
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");

        // Imposta l'intestazione della risposta come JSON
        response.setContentType("application/json");

        // Leggi i dati dal POST
        String username = request.getParameter("username");
        String password = request.getParameter("password");

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
            String sql = "SELECT * FROM utente WHERE Username=? AND Password=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            try {
                rs=stmt.executeQuery();
                if(rs.next()) {
                    jsonResponse.addProperty("status", "success");
                    jsonResponse.addProperty("id_utente", rs.getInt("ID"));
                    jsonResponse.addProperty("username", rs.getString("Username"));
                    jsonResponse.addProperty("name", rs.getString("Nome"));
                    jsonResponse.addProperty("cognome", rs.getString("Cognome"));
                    jsonResponse.addProperty("email", rs.getString("email"));
                    jsonResponse.addProperty("anno_classe", rs.getInt("anno_classe"));
                    jsonResponse.addProperty("sezione", rs.getString("sezione"));
                    jsonResponse.addProperty("indirizzo", rs.getString("indirizzo"));
                    jsonResponse.addProperty("tipo_utente", rs.getString("tipo_utente"));
                    jsonResponse.addProperty("zona_geografica", rs.getString("zona_geoografica"));
                }
                else{
                    jsonResponse.addProperty("status", "faiuler");
                    jsonResponse.addProperty("error", "username o password errata");
                }

            }catch(SQLException e){

                e.printStackTrace();
            }

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            jsonResponse.addProperty("status", "error");
            jsonResponse.addProperty("message", "An error occurred while processing your request");
        }

        // Scrivi la risposta come JSON
        PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
        out.flush();
    }
}
