package astbina.sanetna.Chat;

public class ShowData {

    private String name;
    private String image;
    private  String uid;

    public ShowData(String name, String image,String uid) {
        this.name = name;
        this.image = image;
        this.uid=uid;
    }

    public ShowData() {
    }

    public String getName() {
        return name;
    }


    public String getImage() {
        return image;
    }

    public String getUid() {
        return uid;
    }
}
