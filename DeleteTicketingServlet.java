package it.avbo.progettoNSI;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ticketing")
public class DeleteTicketingServlet extends HttpServlet {
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
        // Recupera l'ID del ticket da eliminare
        int ticketId = Integer.parseInt(request.getParameter("ticketId"));

        // Verifica se l'utente ha cliccato sul pulsante per eliminare il ticket
        if ("delete".equals(request.getParameter("action"))) {
            // Elimina il ticket dal database
            deleteTicket(ticketId);
        } 
        // Rimanda l'utente alla pagina per visualizzare i ticket aggiornati
        response.sendRedirect(request.getContextPath() + "/ticketing");
    }
    
    /**
     * Metodo che elimina un ticket dal database dato l'ID del ticket.
     * @param id ID del ticket da eliminare
     */
    private void deleteTicket(int id) {
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            String sql = "DELETE FROM tickets WHERE id=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    

}