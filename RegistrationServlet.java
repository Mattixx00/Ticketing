import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

public class RegistrationServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Imposta l'intestazione della risposta come JSON
        response.setContentType("application/json");

        // Leggi i dati dal POST
        String username = request.getParameter("username");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String cognome = request.getParameter("cognome");
        int anno_classe = Integer.parseInt(request.getParameter("anno_classe"));
        String sezione = request.getParameter("sezione");

        JSONObject jsonResponse = new JSONObject();

        boolean ControlUser = ControlUsername(username);

        if (ControlUser) {

            jsonResponse.put("status-registration", "failed");
            jsonResponse.put("problem", "200");
            PrintWriter out = response.getWriter();
            out.print(jsonResponse.toJSONString());
            out.flush();

        } else {


            // Effettua la registrazione
            boolean registrationSuccess = registerUser(username, name, email, password, cognome, anno_classe, sezione);

            // Crea un oggetto JSON per la risposta
            JSONObject jsonResponse = new JSONObject();
            jsonResponse.put("status-registration", registrationSuccess ? "success" : "failed");

            // Scrivi la risposta come JSON
            PrintWriter out = response.getWriter();
            out.print(jsonResponse.toJSONString());
            out.flush();
        }

        private boolean registerUser (String username, String name, String email, String password, String cognome,
        int anno_classe, String sezione){
            boolean registrationSuccess = true;
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

                // Esegui la query per inserire i dati nella tabella della registrazione
                String sql = "INSERT INTO utente (ID,Username,Password,Nome,Cognome,email,anno_classe,Sezione,tipo_user) VALUES (NULL,?,?,?,?,?,?,?,'guest')";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, name);
                stmt.setString(4, cognome);
                stmt.setString(5, email);
                stmt.serInt(6, anno_classe);
                stmt.setString(7, sezione);
                int rowsInserted = stmt.executeUpdate();

                if (rowsInserted > 0) {
                    registrationSuccess = true;
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

            return registrationSuccess;
        }

        private boolean ControlUsername (String username){
            boolean Control = false;
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

                // Esegui la query per inserire i dati nella tabella della registrazione
                String sql = "SELECT ID FROM utente WHERE username=?";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                int rowsInserted = stmt.executeQuery();

                if (rowsInserted > 0) {
                    Control = false;
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

            return Control;
        }


    }

}