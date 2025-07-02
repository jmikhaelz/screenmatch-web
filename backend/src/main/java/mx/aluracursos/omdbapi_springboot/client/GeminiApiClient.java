package mx.aluracursos.omdbapi_springboot.client;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;

public class GeminiApiClient {
    private static final String MODELO = "gemini-2.0-flash-lite";
    private static final String FORMATO_PROMPT = "Traduce el siguiente texto al idioma '%s' : \"%s\"";

    public static String getTranslate(String texto, String language, String apiKey) {
        if (texto == null || texto.isEmpty() || language == null || language.isEmpty()) {
            return texto;
        }

        String prompt = String.format(FORMATO_PROMPT, language, texto);

        try {
            Client cliente = new Client.Builder().apiKey(apiKey).build();
            GenerateContentResponse response = cliente.models.generateContent(MODELO, prompt, null);
            String rawText = (response != null && !response.text().isEmpty()) ? response.text() : texto;
            return extractQuotedText(rawText);

        } catch (Exception e) {
            System.err.println("Error al traducir con Gemini: " + e.getMessage());
            return texto;
        }
    }

    private static String extractQuotedText(String input) {
        int firstQuote = input.indexOf('"');
        int lastQuote = input.lastIndexOf('"');

        if (firstQuote != -1 && lastQuote != -1 && firstQuote < lastQuote) {
            return input.substring(firstQuote + 1, lastQuote);
        }

        return input;
    }

}