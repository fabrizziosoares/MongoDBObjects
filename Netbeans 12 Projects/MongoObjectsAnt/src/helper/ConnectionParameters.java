/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helper;

/**
 *
 * @author fabrizzio
 */
public class ConnectionParameters {

//    String SERVER = "cluster0-0vxe4.mongodb.net";
//    String USERNAME = "fabrizzio";
//    String PASSWORD = "1234";
//    String PROJECT = "Project0";
//    String DATABASE = "test";

    
    private String server;
    private String username;
    private String password;
    private String project;
    private String database;

    public ConnectionParameters(String server, String username, String password,
            String project, String database){
        this.server = server;
        this.username = username;
        this.password = password;
        this.project = project;
        this.database = database;
    }
    
    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }


}
