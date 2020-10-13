package helper.dao;

import com.mongodb.client.MongoCollection;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;
import helper.Application;
import java.util.HashMap;
import java.util.Map;

public abstract class BasicDAOImpl<TDocument extends BasicEntity> implements BasicDAO<TDocument> {
    
    MongoCollection<TDocument> collection;
    
    @Override
    public boolean insert(TDocument d) {
        
        int id = Application.getInstance().getNextSequence(getCollectionName());
        d.setId(id);        
        InsertOneResult result = collection.insertOne(d);

        return result.wasAcknowledged();
        
    }
    
    public BasicDAOImpl() {
        collection = Application.getInstance().getCollection(getCollectionName(), getCollectionClass());
    }
    
    @Override
    public MongoCollection<TDocument> getCollection(){
        return collection;
    }

    @Override
    public TDocument getById(int id) {
        return collection.find(eq("_id", id)).first();
    }

    @Override
    public TDocument get(String field, Object value) {
        return collection.find(eq(field, value)).first();
    }

    @Override
    public List<TDocument> getAll() {

        List<TDocument> list = new ArrayList<>();
        for (TDocument d : collection.find()) {
            list.add(d);
        }
        return list;
    }

    @Override
    public Map<Integer, TDocument> getMap() {

        Map<Integer, TDocument> map = new HashMap<>();
        for (TDocument d : collection.find()) {
            map.put(d.getId(), d);
        }
        return map;
    }
    
    @Override
    public Iterable<TDocument> getIterator() {
        return collection.find();
    }

    @Override
    public boolean update(TDocument d) {
        
        UpdateResult result = collection.replaceOne(eq("_id", d.getId()), d);        
        return result.wasAcknowledged();
        
    }

    @Override
    public boolean save(TDocument d) {

        if (d.getId() == 0) {
            return insert(d);
        } else {
            return update(d);
        }
    }

    @Override
    public boolean delete(TDocument d) {

        DeleteResult result = collection.deleteOne(eq("_id", d.getId()));
        d.setId(0);
        
        return result.wasAcknowledged();
    }

    @Override
    public boolean deleteById(int id) {

        DeleteResult result = collection.deleteOne(eq("_id", id));

        return result.wasAcknowledged();
    }

    @Override
    public boolean collectionExists(){        
        return (collection.countDocuments() > 0);
    }    
    
}
