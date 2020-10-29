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
        char t = 't';


        SongAddress.replace("mp3","srt");
        String srt = new String(".srt");
        String subtitileAddress = SongAddress.substring(0,SongAddress.length()-4)+srt;

        System.out.println(subtitileAddress);
        subtitlefile=new File(subtitileAddress);

    }



}
