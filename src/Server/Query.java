/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import java.io.Serializable;

/**
 *
 * @author Abhishek keshri
 */
public class Query implements Serializable{
    private String query;
    public Query(String query)
    {
        this.query = query;
    }
    public String getQuery()
    {
        return this.query;
    }
    
       @Override
    public String toString() {
        return this.query;

    }
}
