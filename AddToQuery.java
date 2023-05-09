package com.example.login;

import java.io.*;
import java.sql.*;

import com.google.gson.JsonObject;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "AddToQuery", value = "/AddToQuery")
public class AddToQuery extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");

        // Imposta l'intestazione della risposta come JSON
        response.setContentType("application/json");

        // Leggi i dati dal POST
        int idUtente = Integer.parseInt(request.getParameter("id_utente"));
        int idTicket = Integer.parseInt(request.getParameter("id_ticket"));
        String descrizione = request.getParameter("descrizione");

        // Cerca l'utente nel database
        JsonObject result = new JsonObject();
        try {
            Connection conn = null;
            PreparedStatement stmt = null;

            // Carica il driver JDBC per il database
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            // Ottieni la connessione al database
            String url = "jdbc:mysql://localhost:3306/ticketing";
            String user = "root";
            String dbPassword = "";
            conn = DriverManager.getConnection(url, user, dbPassword);

            // Esegui la query per cercare l'utente
            String sql = "INSERT INTO class (id,ID_Studente,ID_Ticket,LVLCompetenzaStudente,Querystatus) VALUES (NULL,?,?,?,'in attesa')";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idUtente);
            stmt.setInt(2, idTicket);
            stmt.setString(3, descrizione);

            try{
                int row=stmt.executeUpdate();

                if(row>0) {
                    result.addProperty("AddQuery", "success");
                }else{
                    result.addProperty("AddQuery","problem");
                }
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