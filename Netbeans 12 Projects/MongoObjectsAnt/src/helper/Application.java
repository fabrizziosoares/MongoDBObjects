package helper;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import helper.mongodb.MongoSequence;

/**
 *
 * @author fabrizzio
 */
public class Application {

    private static ConnectionParameters params = null;
    private static String URI = null;

    private MongoClient mongo = null;
    private MongoDatabase db = null;

    private static Application app = null;

    private Application() {
    }

    public static void initialize(
            String server, String username,
            String password, String project,
            String database) {

        if (app == null) {
            params = new ConnectionParameters(
                    server, username, password, project, database);

            if (params.getUsername().length() == 0) {
                URI = "mongodb://"
                        + params.getServer() + "/"
                        + "?retryWrites=true&w=majority";

            } else {
                URI = "mongodb+srv://"
                        + params.getUsername() + ":"
                        + params.getPassword() + "@"
                        + params.getServer() + "/"
                        + params.getProject()
                        + "?retryWrites=true&w=majority";
            }

            app = new Application();
            app.start();
        }
    }
    
    public static void initialize(String server, String database) {
        
        initialize(server, "", "", "", database);
        
    }
    

    public static Application getInstance() {
        if (app == null) {
            throw new RuntimeException("Initialize the application first!");
        }
        return app;
    }

    public boolean isInitialized() {
        return !(mongo == null);
    }

    public MongoClient start() {

        if (params == null) {
            throw new RuntimeException("Application needs to be initialized first!");
        }

        if (!isInitialized()) {
            CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                    fromProviders(PojoCodecProvider.builder().automatic(true).build()));

            ConnectionString con = new ConnectionString(URI);

            MongoClientSettings settings = MongoClientSettings.builder()
                    .codecRegistry(pojoCodecRegistry)
                    .applyConnectionString(con)
                    .build();

            mongo = MongoClients.create(settings);
            db = mongo.getDatabase(params.getDatabase());
        }

        return mongo;

    }

    public static void terminate() {
        if (app != null) {
            app.stop();
        }
    }

    public void stop() {

        if (isInitialized()) {
            mongo.close();
        }

        db = null;
        mongo = null;
    }

    public MongoClient getConnection() throws RuntimeException {
        if (!isInitialized()) {
            app.start();
        }
        return mongo;
    }

    public MongoDatabase getDatabase() throws RuntimeException {

        if (!isInitialized()) {
            start();
        }
        return db;
    }

    public <TDocument extends Object> MongoCollection<TDocument> getCollection(String colname, Class<TDocument> cls) {

        if (!isInitialized()) {
            start();
        }
        return db.getCollection(colname, cls);

    }

    public int getNextSequence(String seqname) {
        if (!isInitialized()) {
            start();
        }
        return MongoSequence.getNextSequence(db, seqname);
    }

}
