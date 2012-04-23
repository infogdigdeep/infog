package com.digdeep.infog.model.mongodb;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;

public class InfogMongoDAO {
	
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
	
	public static void main (String [] args) {
		try {
			DBCollection repo = getInfogMongoDB().getCollection("contentrepository");
			BasicDBObject ct1 = new BasicDBObject();
			ct1.put("id", "news");
			ct1.put("imageUrl", "http://www.google.ca");
			repo.insert(ct1);
			DBCursor ct1c = repo.find();
			while (ct1c.hasNext()) {
				System.out.println(ct1c.next());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
