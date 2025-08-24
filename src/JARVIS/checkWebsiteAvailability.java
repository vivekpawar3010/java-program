package JARVIS;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class checkWebsiteAvailability extends ok{
	public boolean checkWebsiteAvailability(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return (responseCode == HttpURLConnection.HTTP_OK);
        } catch (IOException e) {
            return false;
        }
    }
}
