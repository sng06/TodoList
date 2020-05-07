package network;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


// Code reference:
// 1. CPSC 210 Edge Design Patterns and the Web
// 2. https://dzone.com/articles/how-to-parse-json-data-from-a-rest-api-using-simpl

public class ReadWebPageEx {

    public ReadWebPageEx() {
    }

    public void getWeather(JsonObject json) {
        //Weather weather = gson.fromJson(json, Weather.class);
        JsonArray jsonArrayWeather = (JsonArray) json.get("weather");
        JsonObject jsobObjWeather = (JsonObject) jsonArrayWeather.get(0);
        String weather = jsobObjWeather.get("main").toString();

        JsonObject jsonObjTemp = (JsonObject) json.get("main");
        double temp = jsonObjTemp.get("temp").getAsDouble();
        int convertedExactTemp = (int) (temp - 273.15);
        //double minTemp = json.get("temp_min").getAsDouble();
        //double maxTemp = json.get("temp_max").getAsDouble();
        System.out.println("Current weather: " + weather + ", Current temperature: "
                + convertedExactTemp + " degree Celsius");
        System.out.println();
    }


    public JsonObject urlToJson(URL url) throws IOException {
        BufferedReader br = null;
        try {
            Gson gson = new Gson();
            br = new BufferedReader(new InputStreamReader(url.openStream()));
            String jsonText = readToString(br);
            //JsonObject json = new JsonObject(jsonText);
            //JsonElement jsonElement = new JsonParser().parse(jsonText);
            //Gson gson = new GsonBuilder().setPrettyPrinting().create();
            //String jsonPrettyPrint = gson.toJson(jsonElement);
            //System.out.println(jsonPrettyPrint);
            //JsonObject json = new JsonParser().parse(jsonText).getAsJsonObject();
            JsonObject json = gson.fromJson(jsonText, JsonObject.class);
            return json;
        } finally {
            br.close();
        }
    }

    public String readToString(BufferedReader br) throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = br.readLine()) != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
        }

        //System.out.println(prettyPrint);
        return sb.toString();
    }

    //public static void main(String[] args) throws MalformedURLException, IOException {
    public void setUp() throws IOException {
        //String apiKey = "874911e18c0b252613d94e2d05bfc05b";
        String vancouverWeatherQuery = "https://api.openweathermap.org/data/2.5/weather?q=Vancouver,ca&APPID=";
        String theURL = vancouverWeatherQuery + "874911e18c0b252613d94e2d05bfc05b";
        URL url = new URL(theURL);
        JsonObject json = urlToJson(url);
        getWeather(json);
    }


}