package astbina.sanetna.Dataclass;

public class NewsItem {


   String news_name, posteruid,description,postDate,image,uid;

    public NewsItem() {
    }

    public NewsItem(String news_name, String posteruid, String description, String postDate, String image, String uid) {
        this.news_name = news_name;
        this.posteruid = posteruid;
        this.description = description;
        this.postDate = postDate;
        this.image = image;
        this.uid = uid;
    }

    public String getNews_name() {
        return news_name;
    }

    public void setNews_name(String news_name) {
        this.news_name = news_name;
    }

    public String getPosteruid() {
        return posteruid;
    }

    public void setPosteruid(String posteruid) {
        this.posteruid = posteruid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}