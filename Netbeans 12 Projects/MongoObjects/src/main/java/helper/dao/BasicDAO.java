package helper.dao;

import com.mongodb.client.MongoCollection;
import java.util.List;
import java.util.Map;

public interface BasicDAO<TDocument> {

    TDocument getById(int id);
    TDocument get(String field, Object value);
    List<TDocument> getAll();
    Map<Integer, TDocument> getMap();
    Iterable<TDocument> getIterator();
    boolean insert(TDocument d);
    boolean update(TDocument d);
    boolean save(TDocument d);
    boolean delete(TDocument d);
    boolean deleteById(int id);
    boolean collectionExists();
    String getCollectionName();
    Class getCollectionClass();
    MongoCollection<TDocument> getCollection();
}
