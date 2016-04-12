package wayfoo.wayfoo;



public class FeedItem {
	private String Place;
	private String title;
    private String Ratings;
    private String thumbnail;
    private String b,b2;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getPlace() {
        return Place;
    }
    public void setPlace(String Place) {
        this.Place = Place;
    }
    public String getRatings() {
        return Ratings;
    }
    public void setRatings(String Ratings) {
        this.Ratings = Ratings;
    }
    public String getThumbnail() {
        return thumbnail;
    }
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    public String getFav() {
       return b;
    }
    public void setFav(String b) {
        this.b=b;
    }
    public String getID() {
        return b2;
    }
    public void setID(String b2) {
        this.b2=b2;
    }
}