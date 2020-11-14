package astbina.sanetna.Dataclass;

public class PersonalDate {

   private String name;
   private String email;
   private String phoneNumbe;
   private String password;
   private String places;
   private String data;
   private String identitycad;
   private String Imageidentitycad;
   private  String uid;

   private String Imagepersonal;
   private  String onlineState;
   private  String typingTo;



  public PersonalDate()
  {

  }
    public PersonalDate(String name, String email, String phoneNumbe, String password, String places, String data, String identitycad, String imageidentitycad,  String imagepersonal,String uid,String onlineState,String typingTo) {
        this.name = name;
        this.email = email;
        this.phoneNumbe = phoneNumbe;
        this.password = password;
        this.places = places;
        this.data = data;
        this.identitycad = identitycad;
       this.Imageidentitycad = imageidentitycad;

       this.Imagepersonal = imagepersonal;
       this.uid=uid;
       this.onlineState=onlineState;
        this.typingTo=typingTo;
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
