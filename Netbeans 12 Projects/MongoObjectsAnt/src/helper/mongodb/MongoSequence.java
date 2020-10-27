package helper.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;

/**
 *
 * @author fabrizzio
 */
public class MongoSequence {

    private final static String MY_SEQUENCE_COLLECTION = "mySequenceCollection";
    
    private static void createCountersCollection(MongoCollection<Document> countersCollection, String seq_name) {

        Document document = new Document();
        document.append("_id", seq_name);
        document.append("seq", 1);
        countersCollection.insertOne(document);
    }

    public static int getNextSequence(MongoDatabase database, String seq_name) {

        MongoCollection<Document> countersCollection = database.getCollection(MY_SEQUENCE_COLLECTION);

        FindIterable<Document> find = countersCollection.find(eq("_id", seq_name));
        if (!find.iterator().hasNext())
            createCountersCollection(countersCollection, seq_name);            
                
        Document searchQuery = new Document("_id", seq_name);
        Document increase = new Document("seq", 1);
        Document updateQuery = new Document("$inc", increase);
        Document result = countersCollection.findOneAndUpdate(searchQuery, updateQuery);

        return (int) result.get("seq");
    }


}
