package astbina.sanetna.CraftsmanSetting;

public class Rate {
    String userId;
    String craftsmanId;
    float rating;

    public Rate() {
    }

    public Rate(String userId, String craftsmanId, float rating) {
        this.userId = userId;
        this.craftsmanId = craftsmanId;
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }

    public String getCraftsmanId() {
        return craftsmanId;
    }

    public float getRating() {
        return rating;
    }
}
