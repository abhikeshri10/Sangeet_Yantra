package sample;





import java.io.File;
import java.io.Serializable;

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
    public SongInfo(  int id,String SongName,int AlbumId,String Genre,String SongAddress,String Date_Created,int ImageId)
    {
        this.id=id;
        this.SongName=SongName;
        this.AlbumId= AlbumId;
        this.Genre=Genre;
        this.SongAddress=SongAddress;
        this.Date_Created=Date_Created;
        this.ImageId=ImageId;
        file =new File(SongAddress);
        SongAddress.replace("mp3","srt");
        subtitlefile=new File(SongAddress);
    }



}
