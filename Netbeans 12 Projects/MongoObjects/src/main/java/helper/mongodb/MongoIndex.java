
package helper.mongodb;

import com.mongodb.client.ListIndexesIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.IndexOptions;
import com.mongodb.client.model.Indexes;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author fabrizzio
 */
public class MongoIndex {
    
    MongoCollection collection;
    
    public MongoIndex(MongoCollection collection){
        this.collection = collection;
    }
    
    public void createUniqueIndex(String idxname, String ... fields){        
        IndexOptions indexOptions = new IndexOptions()
                .name(idxname)
                .unique(true);
        
        collection.createIndex(Indexes.ascending(fields), indexOptions);        
    }
    
    public void createIndex(String idxname, String ... fields){
        
        if (indexExist(idxname)) return;
        
        IndexOptions indexOptions = new IndexOptions()
                .name(idxname)
                .unique(false);
        
        collection.createIndex(Indexes.ascending(fields), indexOptions);                
    }
    
    public void DropIndex(String idxname){
        if (indexExist(idxname))
            collection.dropIndex(idxname);
    }
    
    public List<String> getIndexNames(){
        
        ListIndexesIterable<Document> result = collection.listIndexes();
        List<String> indexnames = new ArrayList<>();
        
        for (Document i: result){
            indexnames.add(i.getString("name"));
        }
        
        return indexnames;
    }
    
    public boolean indexExist(String indexname){
        
        ListIndexesIterable<Document> result = collection.listIndexes();
        
        for (Document i: result){
            if (i.getString("name").equals(indexname)) return true;
        }
        
        return false;
        
    }

}
