package commands;

import assist.WeatherModule;
import org.json.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ResourceBundle;

public class WeatherCommand implements Command{

    private WeatherModule weatherModule;

    public WeatherCommand(WeatherModule weatherModule) {
        this.weatherModule = weatherModule;
    }

    public String execute(String argument) {
        try {
            return weatherModule.getWeather();
        } catch (Exception e) {
            e.printStackTrace();
            return "Ой.. Что-то пошло не так.";
        }
    }
}
