package com.chatbot.aichatbot.service;

import okhttp3.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OpenRouterService {

    @Value("${openrouter.api.key}")
    private String apiKey;

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper mapper = new ObjectMapper();

    public String getResponse(String message) throws IOException {

        String jsonBody = """
        {
          "model": "openai/gpt-3.5-turbo",
          "messages": [
            {"role": "user", "content": "%s"}
          ]
        }
        """.formatted(message.replace("\"", "\\\""));

        RequestBody body = RequestBody.create(
                jsonBody,
                MediaType.parse("application/json")
        );

        Request request = new Request.Builder()
                .url("https://openrouter.ai/api/v1/chat/completions")
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .addHeader("HTTP-Referer", "http://localhost:8080")
                .addHeader("X-Title", "AI Chatbot Project")
                .build();

        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();

        JsonNode root = mapper.readTree(responseBody);

        return root
                .path("choices")
                .get(0)
                .path("message")
                .path("content")
                .asText();
    }
}