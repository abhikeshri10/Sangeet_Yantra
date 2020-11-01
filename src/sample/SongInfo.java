package sample;





import java.io.*;

public class SongInfo implements Serializable {
    public int id;
    public String SongName;
    public int AlbumId;
    public String Genre;
    public String SongAddress;
    public String Date_Created;
    public int ImageId;
    public File file;
    public File subtitlefile;
    public SongInfo(  int id,String SongName,int AlbumId,String Genre,File subtitlefile,String Date_Created,int ImageId)
    {
        this.id=id;
        this.SongName=SongName;
        this.AlbumId= AlbumId;
        this.Genre=Genre;

        this.Date_Created=Date_Created;
        this.ImageId=ImageId;
        this.subtitlefile = subtitlefile;
        //file =new File(SongAddress);





    }



}
