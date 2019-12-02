package com.FlorisBay;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

@WebServlet(urlPatterns = {"/Login"})
public class LoginServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password_md5 = DigestUtils.md5Hex(password);

        String payload = String.format("{" +
                "\"username\": \"%s\", " +
                "\"password\": \"%s\", " +
                "}",username, password_md5);
        StringEntity entity = new StringEntity(payload,
                ContentType.APPLICATION_FORM_URLENCODED);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost server_request = new HttpPost("http://localhost:8080/FlorisBayServer/LoginServlet");
        server_request.setEntity(entity);

        HttpResponse server_response = httpClient.execute(server_request);

        if(server_response.getHeaders("result")[0].getValue().equals("false")) {
            String return_message = "Username or password is not correct";
            response.sendRedirect(request.getContextPath() + "/Login.jsp?login_state=" + return_message);
        }else {
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("id", server_response.getHeaders("result")[0].getValue());
            response.sendRedirect(request.getContextPath() + "/Search.jsp");
        }

	}
}
