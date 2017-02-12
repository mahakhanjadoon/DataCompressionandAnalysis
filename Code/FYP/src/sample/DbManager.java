package sample;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;

import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class DbManager {
    private static DB db;
    public static DB getDb()
            throws UnknownHostException {
        MongoClient mongo = new MongoClient();
        if (db == null) {
            db = mongo.getDB("cern");
        }
        return db;
    }
}