package konkuk.forcutbook.api.huggingface;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
public class ImageToTextApiProvider {

    @Value("${hugging-face.api.key}")
    private String huggingFaceApiKey;
    private final ObjectMapper objectMapper;

    public HuggingFaceResultDto query(byte[] imageFile) {
        try {
            URL url = new URL("https://api-inference.huggingface.co/models/Salesforce/blip-image-captioning-large");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + huggingFaceApiKey);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(imageFile);
                os.flush();
            }

            // 응답 읽기
            StringBuilder response = new StringBuilder();
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
            }

            log.info(response.toString());
            return objectMapper.readValue(response.toString(), new TypeReference<List<HuggingFaceResultDto>>(){}).get(0);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
