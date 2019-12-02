package com.FlorisBayServer;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.json.JsonMode;
import org.bson.json.JsonWriterSettings;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

//TODO HTTP response codes
@WebServlet(urlPatterns = {"/user","/user/","/user/*"})
public class User extends HttpServlet {
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
		PrintWriter out = response.getWriter();
        try {
			JSONObject json = requestToJson(request);
			Document document = new Document();
			for(int i = 0; i < json.names().length(); i++) {
				document.append(json.names().getString(i), json.opt(json.names().getString(i)));
			}
			out.println(json.toString());
			collection.insertOne(document);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	//read
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter out = response.getWriter();
		String[] url = request.getRequestURI().split("/");
		//print out all users
		if(url[url.length-1].equals("user")) {
			FindIterable<Document> docs = collection.find();
            for (Document doc : docs) {
                out.println(doc.toJson(writerSettings));
            }
		}
		//print the user of the given id
		else {
			String id = url[url.length-1];
			Document doc = collection.find(eq("_id", new ObjectId(id))).first();
			out.println(doc.toJson(writerSettings));
		}
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
//				collection.insertOne(doc);
				Document updateQuery = new Document("$push", doc);
				UpdateResult result1 = collection.updateOne(filter, updateQuery);
				Bson query = new BsonDocument();
				for(int i = 0; i < json.names().length(); i++) {
					String test1 = json.names().getString(i);
					Object test2 = json.opt(json.names().getString(i));
					query = combine(query,set(json.names().getString(i), json.opt(json.names().getString(i))));
				}
				out.println(json.toString());
				UpdateResult result = collection.updateOne(filter, query);
				out.println(result);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
	//delete
	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
			DeleteResult result = collection.deleteMany(filter);
			out.println(result);
		}
	}
}
