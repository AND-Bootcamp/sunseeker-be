package and.digital.sunshine.service;

import and.digital.sunshine.dto.SunniestSpot;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.stereotype.Service;


@Service
public class SunSeekerService {

    final String UV_SERVICE_BASE_URI = "http://api.openuv.io/api/v1/uv";
    final String UV_SERVICE_TOKEN = "0e67670beedffdddfbf06d4e2d3a6776";

    public SunniestSpot getPosition(String latitude, String longitude) {

        OkHttpClient client = new OkHttpClient();
        Response response = null;
        Gson gson = new Gson();
        JsonObject entity = null;

        //TODO: Make calls to "nearby" locations
        Request request = new Request.Builder()
                .url(UV_SERVICE_BASE_URI + "?lat=" + latitude + "&lng=" + longitude)
                .get()
                .addHeader("x-access-token", UV_SERVICE_TOKEN)
                .build();

        try{
            response = client.newCall(request).execute();
            entity = gson.fromJson(response.body().string(), JsonObject.class);
            entity.get("result");
            entity.getAsJsonObject("result").get("uv");
        } catch (Exception e){
            System.out.println("Unable to process request..." + e);
        }

        //TODO: Remove mock response & process API results
        //entity.getAsJsonObject("result").get("uv").getAsString();
        return SunniestSpot.builder()
                .latitude("52.3676")
                .longitude("4.9041")
                .title("Sunny Spot")
                .subtitle("Sunniest spot in town")
                .build();
    }

}
