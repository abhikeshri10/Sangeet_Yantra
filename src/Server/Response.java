package Server;

import java.io.FileInputStream;
import java.sql.*;

public class Response {
    private boolean query_status;
    private ResultSet rs;

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public void setQuery_status(boolean query_status) {
        this.query_status = query_status;
    }
}
