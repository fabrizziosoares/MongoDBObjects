/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import helper.dao.BasicDAOImpl;

/**
 *
 * @author fabrizzio
 */
public class PersonDAO extends BasicDAOImpl<Person> {

    @Override
    public String getCollectionName() {
        return "person";
    }

    @Override
    public Class getCollectionClass() {
        return Person.class;
    }
    
}
