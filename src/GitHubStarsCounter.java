import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GitHubStarsCounter {
    public static void main(String[] args) {
        String owner = "navT703";
        String repo = "nome_do_repositorio";
        int stars = getStarsCount(owner, repo);
        System.out.println("O repositório " + owner + "/" + repo + " tem " + stars + " estrelas.");
    }

    public static int getStarsCount(String owner, String repo) {
        String apiUrl = "https://api.github.com/repos/" + owner + "/" + repo;
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/vnd.github.v3+json");
            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output;
            StringBuilder response = new StringBuilder();
            while ((output = br.readLine()) != null) {
                response.append(output);
            }
            conn.disconnect();
            String jsonResponse = response.toString();
            return Integer.parseInt(jsonResponse.split("\"stargazers_count\":")[1].split(",")[0].trim());
        } catch (IOException e) {
            e.printStackTrace();
            return -1; // Retorna -1 em caso de erro
        }
    }
}
