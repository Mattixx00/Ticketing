package com.example.login;

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

@WebServlet(name = "DeleteTicket", value = "/DeleteTicket")
public class Login extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Impostazioni per la connessione al database
    private final String url = "jdbc:mysql://localhost:3306/ticketing";
    private final String user = "root";
    private final String password = "";

    /**
     * Metodo che viene chiamato quando l'utente invia una richiesta per eliminare un ticket.
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin", "*");
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        // Recupera l'ID del ticket da eliminare
        int ticketId = Integer.parseInt(request.getParameter("ticketId"));

        // Verifica se l'utente ha cliccato sul pulsante per eliminare il ticket

        boolean status=deleteTicket(ticketId);

        JsonObject stato=new JsonObject();
        if(status){
            stato.addProperty("stato","success");
        }else{
            stato.addProperty("stato","failure");
        }
        PrintWriter out = response.getWriter();
        out.print(stato.toString());
        out.flush();
    }
    private boolean deleteTicket(int id) {
        boolean status=false;
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "DELETE FROM ticket WHERE ID=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            int row=statement.executeUpdate();

            if(row>0){
                status=true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return status;
    }



}