package assist;

import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import okhttp3.OkHttpClient;

public class WeatherMap implements WeatherModule {

    private static final long serialVersionUID = 1L;
    private String apiBase = "http://api.openweathermap.org/data/2.5/weather?q=";
    private String apiForecast = "http://api.openweathermap.org/data/2.5/forecast?q=";
    private String units = "imperial"; // metric
    private String lang = "en";
    private String apiKey = "334795718fc3f518f96cf2b7c2cd5f1f";
    private final OkHttpClient httpClient = new OkHttpClient();

    public WeatherMap() {
        try {
            fetch("234",3);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("here");
        }
    }

    private JSONObject fetch(String location, int nbDay) throws IOException {
        String apiUrl = apiForecast + URLEncoder.encode(location, "utf-8") + "&appid=" + apiKey + "&mode=json&units=" + units + "&lang=" + lang + "&cnt=" + nbDay;

        Request request = new Request.Builder()
                .url(apiUrl)
                .build();
            Response response = httpClient.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            // Get response body
            System.out.println(response.body().string());
            return new JSONObject(response.body().string());
    }
}
