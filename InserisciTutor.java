package it.ProgettoNSI;

import java.io.*;
import java.sql.*;

import com.google.gson.JsonObject;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

//@WebServlet(name = "InserisciTutor", value = "/InserisciTutor")
public class InserisciTutor extends HttpServlet {
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
        String materia = request.getParameter("Materia");
        String descrizione = request.getParameter("Descrizione");
        String giorni = request.getParameter("Disponibilita_Giorni");


        Connection conn = null;
        PreparedStatement stmt = null;
        JsonObject ticket = new JsonObject();

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
        try {
            conn = DriverManager.getConnection(url, user, dbPassword);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Esegui la query per cercare l'utente
        String sql = "INSERT INTO ticket (ID, Materia, Descrizione,Disponibilità_Giorni,ID_Utente) VALUES (NULL,?,?,?,?);";
        try {
            stmt = conn.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            stmt.setInt(4, IdUtente);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            stmt.setString(1, materia);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            stmt.setString(2, descrizione);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            stmt.setString(3, giorni);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int rowsInserted;
        try {
            rowsInserted = stmt.executeUpdate();
            if(rowsInserted>0) {
                ticket.addProperty("status", "success");
            }
        } catch (SQLException e) {
            ticket.addProperty("status", "failure");

            throw new RuntimeException(e);
        }





        out.print(ticket.toString());
        out.flush();


    }


    public void destroy() {
    }
}