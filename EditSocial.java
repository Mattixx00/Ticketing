package com.example.addsocial;

import com.google.gson.JsonObject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class EditSocial extends HttpServlet {
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
            String sql = "UPDATE social SET (descrizione=?) WHERE id_utente=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, descrizione);
            stmt.setInt(2, idUtente);
            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {

                result.addProperty("AddSocial","success");
            }else {
                result.addProperty("AddSocial","failure");
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