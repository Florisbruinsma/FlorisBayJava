package com.FlorisBay;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@WebServlet(urlPatterns = {"/YourItems"})
public class YourItemsServlet extends HttpServlet {
	public PrintWriter out;
	public JSONObject requestToJson(HttpServletRequest request) throws IOException, JSONException {
		//convert input to string
	    BufferedReader reader = request.getReader();
	    StringBuilder sb = new StringBuilder();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        sb.append(line);
	    }
	    JSONObject json = new JSONObject(sb.toString());
		return json;
	}

	public void buildFirstItem(String product_name, String price, String discription, String tags, String base_64_image) {
		out.format("<div class=\"row-fluid\">\r\n" + 
				"		<div class=\"span2\">\r\n" + 
				"			<img src=\"data:image/jpeg;base64,%s\">\r\n" + 
				"		</div>\r\n" + 
				"		<div class=\"span6\">\r\n" + 
				"			<h5>%s</h5>\r\n" + 
				"			<p>\r\n" + 
				"			%s" + 
				"			</p>\r\n" + 
				"		</div>\r\n" + 
				"		<div class=\"span4 alignR\">\r\n" + 
				"		    <form class=\"form-horizontal qtyFrm\">\r\n" + 
				"		    <h3> $%s</h3>\r\n" + 
				"		        <div class=\"btn-group\">\r\n" + 
				"                    <a href=\"product_details.html\" class=\"shopBtn\">Buy</a><!-- Link to item -->\r\n" + 
				"		        </div>\r\n" + 
				"			</form>\r\n" + 
				"		</div>\r\n" + 
				"	</div>",base_64_image, product_name, discription, price);
	};

	public void buildItem(String product_name, String price, String discription, String tags, String base_64_image) {
		out.format("<hr class=\"soften\">\r\n" + 
				"	<div class=\"row-fluid\">\r\n" + 
				"		<div class=\"span2\">\r\n" + 
				"			<img src=\"data:image/jpeg;base64,%s\">\r\n" + 
				"		</div>\r\n" + 
				"		<div class=\"span6\">\r\n" + 
				"			<h5>%s</h5>\r\n" + 
				"			<p>\r\n" + 
				"			%s" + 
				"			</p>\r\n" + 
				"		</div>\r\n" + 
				"		<div class=\"span4 alignR\">\r\n" + 
				"            <form class=\"form-horizontal qtyFrm\">\r\n" + 
				"            <h3> $%s</h3>\r\n" + 
				"            <div class=\"btn-group\">\r\n" + 
				"                <a href=\"product_details.html\" class=\"shopBtn\">Buy</a>\r\n" + 
				"            </div>\r\n" + 
				"            </form>\r\n" + 
				"		</div>\r\n" + 
				"	</div>",base_64_image, product_name, discription, price);
	};

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		out = response.getWriter();
		HttpSession session = request.getSession();
		String username = session.getAttribute("username").toString();
		out.println("<head>\r\n" + 
				"    <link href=\"css/Bootstrap.css\" rel=\"stylesheet\" />\r\n" + 
				"    <link href=\"css/MainPage.css\" rel=\"stylesheet\" />\r\n" + 
				"    <title>Your Items</title>\r\n" + 
				"</head>");
		out.format("<body>\r\n" + 
				"<jsp:forward page=\"YourItems\" />\r\n" + 
				"<div class=\"sidenav\">\r\n" + 
				"    <a href=\"#\">%s</a>\r\n" + 
				"    <a href=\"Logout\">Logout</a>\r\n" + 
				"    <a href=\"YourItems.jsp\">Your items</a>\r\n" + 
				"    <a href=\"AddItem.jsp\">Add item</a>\r\n" + 
				"    <a href=\"Search.jsp\">Search</a>\r\n" + 
				"</div>\r\n" + 
				"\r\n" + 
				"<div class=\"new_item\">\r\n" + 
				"<div class=\"span9\">\r\n" + 
				"<div class=\"well well-small\">", username);

		//getting the json

		String id = session.getAttribute("id").toString();
        String url = "http://localhost:8080/FlorisBayServer/user/" + id;
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer server_response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            server_response.append(inputLine);
        }
        in.close();
        JsonObject jsonObject = new JsonParser().parse(server_response.toString()).getAsJsonObject();
        JsonArray json_products = jsonObject.getAsJsonArray("Product");
        for (int i = 0; i < json_products.size(); i++) {
            JsonElement element = json_products.get(i);
            JsonObject json_product = element.getAsJsonObject();

            if(i==0) {
                buildFirstItem(
                        json_product.get("product_name").toString().replace("\"", ""),
                        json_product.get("price").toString().replace("\"", ""),
                        json_product.get("discription").toString().replace("\"", ""),
                        json_product.get("tags").toString().replace("\"", ""),
                        json_product.get("image").toString().replace("\"", "")
                        );
            }else {
                buildItem(
                        json_product.get("product_name").toString().replace("\"", ""),
                        json_product.get("price").toString().replace("\"", ""),
                        json_product.get("discription").toString().replace("\"", ""),
                        json_product.get("tags").toString().replace("\"", ""),
                        json_product.get("image").toString().replace("\"", "")
                        );
			}
		}
	}

}
