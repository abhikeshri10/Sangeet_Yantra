package Client;

import java.io.Serializable;

/**
 *
 * @author Abhishek keshri
 */
public class Query implements Serializable{
    public String query;
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
