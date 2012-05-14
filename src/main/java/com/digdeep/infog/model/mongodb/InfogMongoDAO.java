package com.digdeep.infog.model.mongodb;

import com.digdeep.infog.model.Content;
import com.digdeep.infog.model.ContentType;
import com.digdeep.infog.model.json.ModelJsonHandler;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

public class InfogMongoDAO {
	
	private final static String CONTENT_REPO = "contentrepository";
	
	private static Mongo mongo;
	
	
	public static Mongo getInfogMongo () throws Exception {
		if (mongo == null) {
			mongo = new Mongo("localhost",27017);
		}
		return mongo;
	}
	
	public static DB getInfogMongoDB () throws Exception {
		return getInfogMongo().getDB("infog");
	}
	
	public DBCollection getCollection(String name) throws Exception {
		if (!getInfogMongoDB().collectionExists(name)) {
			return getInfogMongoDB().createCollection(name, null);
		} else {
			return getInfogMongoDB().getCollection(name);
		}
	}
	
	public void insert(Content content) throws Exception {
		ModelJsonHandler jsonHandler = new ModelJsonHandler();
		DBObject obj = (DBObject) JSON.parse(jsonHandler.toJson(content));
		getInfogMongoDB().getCollection(CONTENT_REPO).insert(obj);
	}
	
	public static void main (String [] args) {
		try {
			Content ct = new Content();
			InfogMongoDAO dao = new InfogMongoDAO();
			dao.insert(ct);
			DBCollection repo = getInfogMongoDB().getCollection(CONTENT_REPO);
			DBCursor ct1c = repo.find();
			while (ct1c.hasNext()) {
				System.out.println(ct1c.next());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
