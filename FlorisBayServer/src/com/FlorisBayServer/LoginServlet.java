package com.FlorisBayServer;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	static String uri = "mongodb://admin:admin@cluster0-shard-00-00-pg9mi.mongodb.net:27017,cluster0-shard-00-01-pg9mi.mongodb.net:27017,cluster0-shard-00-02-pg9mi.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true&w=majority";
	static MongoClient mongoClient = new MongoClient(new MongoClientURI(uri));
	static MongoDatabase database = mongoClient.getDatabase("FlorisBayServer");
	static MongoCollection<Document> collection = database.getCollection("Users");
	private JsonWriterSettings writerSettings = new JsonWriterSettings(JsonMode.SHELL, true);

	public JSONObject requestToJson(HttpServletRequest request) throws IOException, JSONException {
		//convert input to string
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        //convert string to json
        //and convert json to bson by key value pair
        JSONObject json = new JSONObject(sb.toString());
		return json;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			JSONObject json = requestToJson(request);
			Document document = new Document();
			for(int i = 0; i < json.names().length(); i++) {
				document.append(json.names().getString(i), json.opt(json.names().getString(i)));
			}
			String username = json.getString("username");
			String password = json.getString("password");
			Bson filter = and(eq("username",username), eq("password",password));
			long amount = collection.countDocuments(filter);
			if(amount == 0) {
				response.setHeader("result", "false");
			}else{
				Document doc = collection.find(filter).first();
				String id = doc.get("_id").toString();
				response.setHeader("result", id);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}


}
