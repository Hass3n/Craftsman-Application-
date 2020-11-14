package astbina.sanetna.Dataclass;

public class Data {
    String news_name;
    String image;
    String postDate;
    String description;
    String postLink;

    public Data() {

    }

    public Data(String news_name, String image, String postDate, String description) {
        this.news_name = news_name;
        this.image = image;
        this.postDate = postDate;
        this.description = description;
    }

    public String getNews_name() {
        return news_name;
    }

    public void setNews_name(String news_name) {
        this.news_name = news_name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostLink() {
        return postLink;
    }

    public void setPostLink(String postLink) {
        this.postLink = postLink;
    }

}