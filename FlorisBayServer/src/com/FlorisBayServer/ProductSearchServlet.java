package com.FlorisBayServer;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import static com.mongodb.client.model.Filters.*;

@WebServlet(urlPatterns = {"/productSearch","/productSearch/","/productSearch/*"})
public class ProductSearchServlet extends HttpServlet {
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

	//create return the json of the created object
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		doGet(request, response);
	}
	//read
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String[] url = request.getRequestURI().split("/");
		String tag_string = url[url.length-1];
		String[] tags = tag_string.split(",");

		ArrayList<String> list = new ArrayList<String>();
		for(String tag : tags) {
			list.add(tag);
		}

		Bson filter = all("Product.tags", "Book");

		FindIterable<Document> docs = collection.find(filter).projection(Filters.elemMatch("Product",Filters.in("tags", list)));
		String json = "";
		for (Document doc : docs) {

			json += doc.toJson(writerSettings);
        }
		out.println(json);
	}
}
