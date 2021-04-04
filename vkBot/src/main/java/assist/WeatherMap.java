package assist;

import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import okhttp3.OkHttpClient;

public class WeatherMap implements WeatherModule {

    private static final long serialVersionUID = 1L;
    private String apiBase = "http://api.openweathermap.org/data/2.5/weather?q=";
    private String apiForecast = "http://api.openweathermap.org/data/2.5/forecast?q=";
    private String units = "metric";
    private String lang = "en";
    private String apiKey = "334795718fc3f518f96cf2b7c2cd5f1f";
    private final OkHttpClient httpClient = new OkHttpClient();
    private final String location;
    private final int nDay;

    public WeatherMap(String location, int nDay) {
        this.location = location;
        this.nDay = nDay;
    }

    private JSONObject fetch() throws IOException {
        String apiUrl = apiForecast + URLEncoder.encode(location, StandardCharsets.UTF_8) + "&appid=" + apiKey + "&mode=json&units=" + units + "&lang=" + lang + "&cnt=" + nDay;

        Request request = new Request.Builder()
                .url(apiUrl)
                .build();
            Response response = httpClient.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            // Get response body
            //System.out.println(response.body().toString());
            return new JSONObject(response.body().string());
    }

    public String getWeather() throws IOException {
        JSONObject mainObj = fetch();
        JSONArray list = mainObj.getJSONArray("list");
        JSONObject temp = list.getJSONObject(0);
        JSONObject main = temp.getJSONObject("main");
        double currentTemp = main.getDouble("temp");
        double feelsLike = main.getDouble("feels_like");
        return "Сейчас в СПб температура " + currentTemp + ", ощущается как " + feelsLike + ".";
    }
}
