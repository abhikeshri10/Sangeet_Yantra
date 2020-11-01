package sample;

import java.io.File;
import java.io.Serializable;
import java.net.MalformedURLException;

public class Song implements Serializable {
    public File file;
    public Song(String  address){

        file=new File(address);
    }


    public File getFile() {
        return file;
    }

    public void setFile(String  address) {

       file=new File(address);
    }
}
