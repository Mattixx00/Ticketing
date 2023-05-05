import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Imposta l'intestazione della risposta come JSON
        response.setContentType("application/json");

        // Leggi i dati dal POST
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Cerca l'utente nel database
        JSONObject jsonResponse = new JSONObject();
        try {
            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            // Carica il driver JDBC per il database
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Ottieni la connessione al database
            String url = "jdbc:mysql://localhost:3306/ticketing";
            String user = "root";
            String dbPassword = "";
            conn = DriverManager.getConnection(url, user, dbPassword);

            // Esegui la query per cercare l'utente
            String sql = "SELECT Username, Nome, Cognome, anno_classe, sezione , tipo_utente FROM utente WHERE username=? AND password=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            rs = stmt.executeQuery();

            // Altrimenti, restituisci un messaggio di errore
            if (!rs.next()) {
                jsonResponse.put("status", "error");
                jsonResponse.put("message", "Invalid username or password");
                jsonResponse.put("status", "success");
                jsonResponse.put("username", rs.getString("Username"));
                jsonResponse.put("name", rs.getString("Nome"));
                jsonResponse.put("cognome", rs.getString("Cognome"));
                jsonResponse.put("anno_classe", rs.getInt("anno_classe"));
                jsonResponse.put("sezione", rs.getString("sezione"));
                jsonResponse.put("tipo_utente", rs.getString("tipo_utente"));
            } else {
                // Se l'utente è stato trovato, crea l'oggetto JSON con le informazioni dell'utente
                // Altrimenti, restituisci un messaggio di errore
                jsonResponse.put("status", "success");
                jsonResponse.put("username", rs.getString("Username"));
                jsonResponse.put("name", rs.getString("Nome"));
                jsonResponse.put("cognome", rs.getString("Cognome"));
                jsonResponse.put("anno_classe", rs.getInt("anno_classe"));
                jsonResponse.put("sezione", rs.getString("sezione"));
                jsonResponse.put("tipo_utente", rs.getString("tipo_utente"));
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            jsonResponse.put("status", "error");
            jsonResponse.put("message", "An error occurred while processing your request");
        }

        // Scrivi la risposta come JSON
        PrintWriter out = response.getWriter();
        out.print(jsonResponse.toJSONString());
        out.flush();
    }
}
