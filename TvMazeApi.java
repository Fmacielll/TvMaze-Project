package Prova_2tri;

import java.net.*;
import java.io.*;
import java.util.*;
import com.google.gson.*;

public class TvMazeApi {
    public static List<Serie> buscarSeries(String termo) throws Exception {
        String url = "https://api.tvmaze.com/search/shows?q=" + URLEncoder.encode(termo, "UTF-8");
        String json = lerUrl(url);
        JsonArray arr = JsonParser.parseString(json).getAsJsonArray();
        List<Serie> resultados = new ArrayList<>();
        for (JsonElement elem : arr) {
            JsonObject show = elem.getAsJsonObject().getAsJsonObject("show");
            int id = show.get("id").getAsInt();
            String nome = show.get("name").getAsString();
            String idioma = show.get("language").isJsonNull() ? "" : show.get("language").getAsString();

            List<String> generos = new ArrayList<>();
            for (JsonElement gen : show.get("genres").getAsJsonArray())
                generos.add(gen.getAsString());

            double nota = show.get("rating").getAsJsonObject().get("average").isJsonNull() ? 0.0
                    : show.get("rating").getAsJsonObject().get("average").getAsDouble();

            String estado = show.get("status").isJsonNull() ? "" : show.get("status").getAsString();
            String dataEstreia = show.get("premiered").isJsonNull() ? "" : show.get("premiered").getAsString();
            String dataFim = show.get("ended").isJsonNull() ? "" : show.get("ended").getAsString();

            String emissora = "";
            if (!show.get("network").isJsonNull())
                emissora = show.get("network").getAsJsonObject().get("name").getAsString();
            else if (!show.get("webChannel").isJsonNull())
                emissora = show.get("webChannel").getAsJsonObject().get("name").getAsString();

            Serie s = new Serie(id, nome, idioma, generos, nota, estado, dataEstreia, dataFim, emissora);
            resultados.add(s);
        }
        return resultados;
    }

    private static String lerUrl(String urlStr) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null)
            content.append(inputLine);
        in.close();
        return content.toString();
    }
}