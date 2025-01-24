package com.dimas.BassicBlog.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class ImgbbService {

    private final String IMGBB_API_URL = "https://api.imgbb.com/1/upload";

    @Value("${imgbb.api.token}")
    private String API_KEY;

    public String uploadImage(String base64Image) {
        RestTemplate restTemplate = new RestTemplate();

        // Configurar los headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Cuerpo de la solicitud
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("key", API_KEY);
        body.add("image", base64Image);

        // Crear la solicitud
        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Realizar la llamada a la API
        String response = restTemplate.postForObject(IMGBB_API_URL, requestEntity, String.class);

        return response; // Devuelve la respuesta de la API
    }

    public String imageToString(MultipartFile file) throws IOException {
        String base64Image = Base64.getEncoder().encodeToString(file.getBytes());
        String response = uploadImage(base64Image);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response);
        return jsonNode.get("data").get("url").asText();
    }

}
