package com.example.addsocial;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.google.gson.JsonObject;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class AddSocial extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Imposta l'intestazione della risposta come JSON
        response.setContentType("application/json");

        // Leggi i dati dal POST
        int idUtente = Integer.parseInt(request.getParameter("id_utente"));
        String descrizione=request.getParameter("descrizione");

        JsonObject result = new JsonObject();
        try {
            Connection conn = null;
            PreparedStatement stmt = null;

            // Carica il driver JDBC per il database
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            // Ottieni la connessione al database
            String url = "jdbc:mysql://localhost:3306/ticketing";
            String user = "root";
            String dbPassword = "";
            conn = DriverManager.getConnection(url, user, dbPassword);

            // Esegui la query per cercare l'utente
            String sql = "INSERT INTO social (id,id_utente,descrizione) VALUES (NULL,?,?)";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUtente);
            stmt.setString(2, descrizione);

            try{
            stmt.executeUpdate();
            result.addProperty("AddSocial","success");
            }catch (SQLException e){
                result.addProperty("AddSocial","failure");
                e.printStackTrace();
            }

            PrintWriter out = response.getWriter();
            out.print(result.toString());
            out.flush();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void destroy() {
    }
}