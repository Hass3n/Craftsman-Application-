package astbina.sanetna.Dataclass;

public class Datacrafstman {

    private String name;
    private String email;
    private String phoneNumbe;
    private String password;
    private String places;
    private String data;
    private String identitycad;
    private String Imageidentitycad;
    private String Imagecadwok;
    private String Imagepersonal;
    private String type_w;
    private String uid;
    private String onlineState;
    private String typingTo;


    public Datacrafstman() {

    }

    public Datacrafstman(String name, String email, String phoneNumbe, String password, String places, String data, String identitycad, String imageidentitycad, String imagecadwok, String imagepersonal, String type_w, String uid, String onlineState, String typingTo) {
        this.name = name;
        this.email = email;
        this.phoneNumbe = phoneNumbe;
        this.password = password;
        this.places = places;
        this.data = data;
        this.identitycad = identitycad;
        Imageidentitycad = imageidentitycad;

        Imagepersonal = imagepersonal;
        this.type_w = type_w;
        this.Imagecadwok = imagecadwok;
        this.uid = uid;
        this.onlineState = onlineState;
        this.typingTo = typingTo;


    }

    public String getImagecadwok() {
        return Imagecadwok;
    }

    public void setImagecadwok(String imagecadwok) {
        Imagecadwok = imagecadwok;
    }

    public String getType_w() {
        return type_w;
    }

    public void setType_w(String type_w) {
        this.type_w = type_w;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumbe() {
        return phoneNumbe;
    }

    public void setPhoneNumbe(String phoneNumbe) {
        this.phoneNumbe = phoneNumbe;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPlaces() {
        return places;
    }

    public void setPlaces(String places) {
        this.places = places;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getIdentitycad() {
        return identitycad;
    }

    public void setIdentitycad(String identitycad) {
        this.identitycad = identitycad;
    }

    public String getImageidentitycad() {
        return Imageidentitycad;
    }

    public void setImageidentitycad(String imageidentitycad) {
        Imageidentitycad = imageidentitycad;
    }


    public String getImagepersonal() {
        return Imagepersonal;
    }

    public void setImagepersonal(String imagepersonal) {
        Imagepersonal = imagepersonal;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOnlineState() {
        return onlineState;
    }

    public void setOnlineState(String onlineState) {
        onlineState = onlineState;
    }

    public String getTypingTo() {
        return typingTo;
    }

    public void setTypingTo(String typingTo) {
        this.typingTo = typingTo;
    }
}
