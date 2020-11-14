package astbina.sanetna.Basic.Aboutus;

import android.media.Image;
import android.widget.ImageView;

public class DeveloperData{

    private String name;
    private String job;
    private int image;

    public DeveloperData (String name, String job, int image) {
        this.name = name;
        this.job = job;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public int getImage() {
        return image;
    }
}
