import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Leggi i dati dal POST
        String username = request.getParameter("username");

        boolean deleteSuccess = deleteRecord(username);

        // Imposta l'intestazione della risposta come JSON
        response.setContentType("application/json");

        //Json
        JSONObject jsonResponse = new JSONObject();

        // Crea un oggetto JSON per la risposta
        jsonResponse.put("status-delete", deleteSuccess ? "success" : "failed");
        PrintWriter out = response.getWriter();
        out.print(jsonResponse.toJSONString());
        out.flush();
    }

    private boolean deleteRecord(String username) {
        boolean deleteSuccess = false;
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

            // Esegui la query per cancellare il record dalla tabella
            String sql = "DELETE FROM utenti WHERE username=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, username);
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
