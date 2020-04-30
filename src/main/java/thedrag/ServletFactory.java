package thedrag;

import static com.mongodb.client.model.Filters.eq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.*;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.operation.OrderBy;

public class ServletFactory {
	public DBServlet getServlet(String servletType){  
        if(servletType == null){  
         return null;  
        }  
      if(servletType.equalsIgnoreCase("cars")) {  
             return new CarServlet();  
           }   
       else if(servletType.equalsIgnoreCase("dealerships")){  
            return new DealerServlet();  
        }   
      else if(servletType.equalsIgnoreCase("makes")) {  
            return new MakeServlet();  
      }  
      return null;  
}  
}
