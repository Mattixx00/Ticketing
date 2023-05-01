package com.example.registration;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.google.gson.JsonObject;
import com.mysql.cj.jdbc.MysqlDataSource;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        JsonObject stato = new JsonObject();

        MysqlDataSource dataSource = new MysqlDataSource();

        dataSource.setDatabaseName("ticket");

        String qry="INSERT INTO utenti('id','nome') VALUES (NULL,?)";

        try(Connection conn= dataSource.getConnection("root",""); PreparedStatement pstmt = conn.prepareStatement(qry)) {
            pstmt.setString(1,request.getParameter("username"));
            pstmt.setString(2,request.getParameter("password"));
            pstmt.executeUpdate();
            //json
            stato.addProperty("registration-status","success" );
            response.getWriter().append(stato.toString());
        } catch (SQLException e) {
            stato.addProperty("registration-status","unsuccessful");
            response.getWriter().append(stato.toString());
            throw new RuntimeException(e);
        }

    }

    public void destroy() {
    }
}