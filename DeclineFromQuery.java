package it.ProgettoNSI;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


//@WebServlet(name = "DeclineFromQuery", value = "/DeclineFromQuery")
public class DeclineFromQuery extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        // Leggi i dati dal POST
        int id_ticket = Integer.parseInt(request.getParameter("idTicket"));
        int id_user = Integer.parseInt(request.getParameter("idUser"));

        boolean deleteSuccess = deleteRecord(id_ticket,id_user);

        // Imposta l'intestazione della risposta come JSON
        response.setContentType("application/json");

        //Json
        JsonObject jsonResponse = new JsonObject();

        // Crea un oggetto JSON per la risposta
        jsonResponse.addProperty("status-delete", deleteSuccess ? "success" : "failed");
        PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
        out.flush();
    }

    private boolean deleteRecord(int id,int id1) {
        boolean deleteSuccess = false;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Carica il driver JDBC per il database
            Class.forName("com.mysql.jdbc.Driver");

            // Ottieni la connessione al database
            String url = "jdbc:mysql://localhost:3306/ticketing";
            String user = "root";
            String password = "";
            conn = DriverManager.getConnection(url, user, password);

            // Esegui la query per cancellare il record dalla tabella
            String sql = "DELETE FROM class WHERE ID_Studente=? AND ID_Ticket=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id1);
            stmt.setInt(2, id);
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                deleteSuccess = true;
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

        return deleteSuccess;
    }
}
