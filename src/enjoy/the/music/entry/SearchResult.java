package enjoy.the.music.entry;


/**
 *Created by helizhi at 2016.6.10 
 * 搜索音乐的对象 
 */
public class SearchResult {
	private String musicName;
    private String url;
    private String artist;
    private String album;
    private long size;//大小

    private boolean isLoaded = false;
    
    public boolean isLoaded() {
		return isLoaded;
	}

	public void setLoaded(boolean isLoaded) {
		this.isLoaded = isLoaded;
	}
    //set方法,get方法
    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "musicName='" + musicName + '\'' +
                ", url='" + url + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", size=" + size +
                '}';
    }
}
