package astbina.sanetna.Dataclass;

 public class Data_item
 {
     String news_name;
     int image;


     public Data_item(String news_name, int image) {
         this.news_name = news_name;
         this.image = image;
     }

     public String getNews_name() {
         return news_name;
     }

     public void setNews_name(String news_name) {
         this.news_name = news_name;
     }

     public int getImage() {
         return image;
     }

     public void setImage(int image) {
         this.image = image;
     }
 }