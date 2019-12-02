package com.FlorisBayServer;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;

import static com.mongodb.client.model.Filters.*;

@WebServlet(urlPatterns = {"/product","/product/","/product/*"})
public class ProductServlet extends HttpServlet {
	static String uri = "mongodb://admin:admin@cluster0-shard-00-00-pg9mi.mongodb.net:27017,cluster0-shard-00-01-pg9mi.mongodb.net:27017,cluster0-shard-00-02-pg9mi.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true&w=majority";
	static MongoClient mongoClient = new MongoClient(new MongoClientURI(uri));
	static MongoDatabase database = mongoClient.getDatabase("FlorisBayServer");
	static MongoCollection<Document> collection = database.getCollection("Users");

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
		doPut(request, response);
	}

	//update
	public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String[] url = request.getRequestURI().split("/");
		//no id given so do nothing
		if(url[url.length-1].equals("user")) {
			out.println("need ot give valid id");
			return;
		}
		//find the user, get info, update with new info
		else
		{
			String id = url[url.length-1];
			Bson filter = eq("_id", new ObjectId(id));
			try {
				JSONObject json = requestToJson(request);
				Document doc = new Document();
				doc = doc.parse(json.toString());
				Document updateQuery = new Document("$push", doc);
				UpdateResult result = collection.updateOne(filter, updateQuery);
				out.println(result);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
