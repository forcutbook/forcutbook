package konkuk.forcutbook.api.clova;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ClovaStudioApi {

    private final ObjectMapper objectMapper;

    @Value("${clova.studio.host}")
    private String host;
    @Value("${clova.studio.apiKey}")
    private String apiKey;
    @Value("${clova.studio.apiKeyPrimaryVal}")
    private String apiKeyPrimaryVal;

    public ClovaResponse execute(String imageContent) {
        // Jackson을 이용하여 JSON 요청 본문 생성
        ArrayNode presetTextArray = objectMapper.createArrayNode();

        ObjectNode presetText1 = objectMapper.createObjectNode();
        presetText1.put("role", "system");
        presetText1.put("content", "사용자 일상 사진을 등록하면 사진을 통해 일기를 자동 생성해주는 어플리케이션을 제공할거야.\n사진은 사용자 본인 또는 친구일거야.\n사용자는 사용자가 올린 사진의 상황, 내용을 전달할거야. 전달 받은 상황을 기반으로 사용자의 일기를 생성해줘. 답변은 부가적인 말은 제외하고 일기 내용만 작성해줘. 그리고 생성한 내용에 맞는 일기 제목을 작성해줘");

        ObjectNode presetText2 = objectMapper.createObjectNode();
        presetText2.put("role", "user");
        presetText2.put("content", imageContent);

        presetTextArray.add(presetText1);
        presetTextArray.add(presetText2);

        ObjectNode requestData = objectMapper.createObjectNode();
        requestData.set("messages", presetTextArray);
        requestData.put("topP", 0.8);
        requestData.put("topK", 0);
        requestData.put("maxTokens", 500);
        requestData.put("temperature", 0.5);
        requestData.put("repeatPenalty", 5.0);
        requestData.put("stopBefore", objectMapper.createArrayNode());
        requestData.put("includeAiFilters", true);
        requestData.put("seed", 0);

        // 요청 실행

        String requestId = UUID.randomUUID().toString();
        try {
            URL url = new URL(host + "/testapp/v1/chat-completions/HCX-DASH-001");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("X-NCP-CLOVASTUDIO-API-KEY", apiKey);
            conn.setRequestProperty("X-NCP-APIGW-API-KEY", apiKeyPrimaryVal);
            conn.setRequestProperty("X-NCP-CLOVASTUDIO-REQUEST-ID", requestId);
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("Accept", "text/event-stream");
            conn.setDoOutput(true);

            // Write the request body
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = requestData.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Read the response
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;

                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine);
                }

                String result = response.toString().split("resultdata:")[1].split("id")[0];

                ClovaResponse clovaResponse = objectMapper.readValue(result, ClovaResponse.class);
                System.out.println("clovaResponse = " + clovaResponse.getMessage().getContent());
                return clovaResponse;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ClovaResponse execute(String imageContent, String pastContent) {
        // Jackson을 이용하여 JSON 요청 본문 생성
        ArrayNode presetTextArray = objectMapper.createArrayNode();

        ObjectNode presetText1 = objectMapper.createObjectNode();
        presetText1.put("role", "system");
        presetText1.put("content", "사용자 일상 사진을 등록하면 사진을 통해 일기를 자동 생성해주는 어플리케이션을 제공할거야.\n사진은 사용자 본인 또는 친구일거야.\n사용자는 사용자가 올린 과거 일기 내용, 작성할 일기의 사진의 상황, 내용을 전달할거야. 전달 받은 상황을 기반으로 사용자의 일기를 생성해줘. 답변은 부가적인 말은 제외하고 일기 내용만 작성해줘. 그리고 생성한 내용에 맞는 일기 제목을 작성해줘");

        ObjectNode presetText2 = objectMapper.createObjectNode();
        presetText2.put("role", "user");
        presetText2.put("content", "과거의 일기는[" + pastContent + "] 이미지의 맥락은"+ imageContent + "] 과거의 일기를 빗대서 새 일기를 작성해줘");

        presetTextArray.add(presetText1);
        presetTextArray.add(presetText2);

        ObjectNode requestData = objectMapper.createObjectNode();
        requestData.set("messages", presetTextArray);
        requestData.put("topP", 0.8);
        requestData.put("topK", 0);
        requestData.put("maxTokens", 500);
        requestData.put("temperature", 0.5);
        requestData.put("repeatPenalty", 5.0);
        requestData.put("stopBefore", objectMapper.createArrayNode());
        requestData.put("includeAiFilters", true);
        requestData.put("seed", 0);

        // 요청 실행

        String requestId = UUID.randomUUID().toString();
        try {
            URL url = new URL(host + "/testapp/v1/chat-completions/HCX-DASH-001");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("X-NCP-CLOVASTUDIO-API-KEY", apiKey);
            conn.setRequestProperty("X-NCP-APIGW-API-KEY", apiKeyPrimaryVal);
            conn.setRequestProperty("X-NCP-CLOVASTUDIO-REQUEST-ID", requestId);
            conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            conn.setRequestProperty("Accept", "text/event-stream");
            conn.setDoOutput(true);

            // Write the request body
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = requestData.toString().getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // Read the response
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;

                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine);
                }

                String result = response.toString().split("resultdata:")[1].split("id")[0];

                ClovaResponse clovaResponse = objectMapper.readValue(result, ClovaResponse.class);
                System.out.println("clovaResponse = " + clovaResponse.getMessage().getContent());
                return clovaResponse;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

