package it.ProgettoNSI;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

/**
 * Servlet implementation class EmailReset
 */
public class EmailReset extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmailReset() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String RandomPass = RandomStringUtils.randomAlphabetic(5);
		Email from = new Email("recoveryssender36@gmail.com");
	    String subject = "La tua password è stata cambiata";
	    Email to = new Email("cava.davide4@gmail.com");//request.getParameter("email")
	    Content content = new Content("text/plain", "La tua password è stata cambiata a questo codice " + RandomPass);
	    Mail mail = new Mail(from, subject, to, content);
	    int success = 0;

	    SendGrid sg = new SendGrid("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");
	    Request Mailrequest = new Request();
	    try {
	      Mailrequest.setMethod(Method.POST);
	      Mailrequest.setEndpoint("mail/send");
	      Mailrequest.setBody(mail.build());
	      Response Mailresponse = sg.api(Mailrequest);
	      System.out.println(Mailresponse.getStatusCode());
	      System.out.println(Mailresponse.getBody());
	      System.out.println(Mailresponse.getHeaders());
	      success++;
	    } catch (IOException ex) {
	      throw ex;
	    }
	    
	    response.getWriter().append(Integer.toString(success));
	
	}

}
