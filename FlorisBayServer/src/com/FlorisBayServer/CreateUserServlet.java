package com.FlorisBayServer;

import static com.mongodb.client.model.Filters.eq;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@WebServlet("/CreateUserServlet")
public class CreateUserServlet extends HttpServlet {
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
        JSONObject json = new JSONObject(sb.toString());
		return json;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			JSONObject json = requestToJson(request);
			Document doc = new Document();
			doc = doc.parse(json.toString());
			long amount = collection.countDocuments(eq("username",json.getString("username")));
			if(amount == 0) {
				collection.insertOne(doc);
				response.setHeader("result", "true");
			}else{
				response.setHeader("result", "false");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}


}
