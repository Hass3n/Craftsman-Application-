package astbina.sanetna.Notifcation;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @Headers({

            "Content-Type:application/json",
            "Authorization:key=AAAASEHRaEk:APA91bFkoWjOOjepMzl86mqFyqkf3Vr2Id4pkURrWnVMXnGYakqzoIQPx7qfEBAss4hlpChleXBieix2wSTi-1wuJJU6X3BKsZO_nd7KIDyu_Y3P_Pndu9zBl_dvbnRktEsV--6dB6lO"


    })
    @POST("fcm/send")
    Call<Response>SendNotofication(@Body Sender body);
}
