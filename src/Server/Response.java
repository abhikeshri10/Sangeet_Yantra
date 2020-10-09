package Server;

import java.io.File;
import java.io.FileInputStream;
import java.sql.*;

public class Response {
    private boolean query_status;
    private ResultSet rs;
    private  File file;
    //file setter

    public void setFile(File file) {//handle query server side
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    //file getter function
    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public void setQuery_status(boolean query_status) {
        this.query_status = query_status;
    }
}
