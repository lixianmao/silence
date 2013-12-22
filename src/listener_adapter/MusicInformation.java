package listener_adapter;

public class MusicInformation {

	private int musicId;
	private String musicName;
	private String musicSinger;
	private String musicTime;
	private String musicAlbum;
	private String musicPath;

	public int getId() {
		return musicId;
	}
	public void setId(int musicId) {
		this.musicId = musicId;
	}
	
	public String getName() {
		return musicName;
	}
	public void setName(String musicName) {
		this.musicName = musicName;
	}
	
	public String getSinger() {
		return musicSinger;
	}
	public void setSinger(String musicSinger) {
		this.musicSinger = musicSinger;
	}
	
	public String getTime() {
		return musicTime;
	}
	public void setTime(String musicTime) {
		this.musicTime = musicTime;
	}
	
	public String getAlbum() {
		return musicAlbum;
	}
	public void setAlbum(String musicAlbum) {
		this.musicAlbum = musicAlbum;
	}
	
	public String getPath() {
		return musicPath;
	}
	public void setPath(String musicPath) {
		this.musicPath = musicPath;
	}
}
