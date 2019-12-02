package com.FlorisBay;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

@WebServlet(urlPatterns = {"/AddItem"})
@MultipartConfig
public class AddItemServlet extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		//get the image
	    Part filePart = request.getPart("image"); // Retrieves <input type="file" name="file">
        InputStream fileContent = filePart.getInputStream();

        byte[] bytes = IOUtils.toByteArray(fileContent);
        String base64_image = Base64.getEncoder().encodeToString(bytes);
        //		out.println(base64_image);
        //get other values
        String id = request.getParameter("id");
        String product_name = request.getParameter("product_name");
        String price = 	request.getParameter("price");
        String tags = request.getParameter("tags");
        String[] tag_array = tags.split(",");
        for(int i=0;i<tag_array.length;i++) {
            tag_array[i] = "\"" + tag_array[i] + "\"";
        }
        String discription = request.getParameter("discription");
        String payload = String.format("{\"Product\":{" +
                "\"product_name\": \"%s\", " +
                "\"price\": \"%s\", " +
                "\"tags\": %s, " +
                "\"discription\": \"%s\", " +
                "\"image\": \"%s\" " +
                "}}", product_name, price, Arrays.toString(tag_array), discription, base64_image);

        StringEntity entity = new StringEntity(payload,ContentType.APPLICATION_FORM_URLENCODED);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost server_request = new HttpPost("http://localhost:8080/FlorisBayServer/product/" + id);
        server_request.setEntity(entity);

        HttpResponse server_response = httpClient.execute(server_request);
        response.sendRedirect(request.getContextPath() + "/YourItems.jsp");
	}

}
