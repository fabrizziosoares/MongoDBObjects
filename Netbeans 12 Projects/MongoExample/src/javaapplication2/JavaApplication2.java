/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import helper.Application;

public class JavaApplication2 {

    public static void main(String[] args) {

        String SERVER = "localhost";
        String USERNAME = "";
        String PASSWORD = "";
        String PROJECT = "test";
        String DATABASE = "test";
       
        
        Application.initialize(SERVER, USERNAME, PASSWORD, PROJECT, DATABASE);
        Application app = Application.getInstance();
        
        Person p = new Person();
        p.setAge(10);
        p.setName("teste");
        
        PersonDAO dao = new PersonDAO();
        dao.save(p);
        //Person p = dao.getById(1);
        //System.out.println(p.getName());
        
        app.stop();

    }

}
