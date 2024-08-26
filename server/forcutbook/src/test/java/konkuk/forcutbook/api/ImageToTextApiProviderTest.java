package konkuk.forcutbook.api;

import konkuk.forcutbook.api.huggingface.HuggingFaceResultDto;
import konkuk.forcutbook.api.huggingface.ImageToTextApiProvider;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Slf4j
@SpringBootTest
class ImageToTextApiProviderTest {

    @Autowired
    ImageToTextApiProvider imageToTextApiProvider;

    @Test
    void query() {
        String filePath = "/Users/kyn/Downloads/testImage.jpg";  // 여기서 경로를 실제 이미지 파일 경로로 바꿔주세요
        File imgFile = new File(filePath);

        try {
            // 파일을 byte[]로 변환
            byte[] fileBytes = Files.readAllBytes(imgFile.toPath());
            System.out.println("Image size in bytes: " + fileBytes.length);

            HuggingFaceResultDto result = imageToTextApiProvider.query(fileBytes);
            log.info("huggingFace 결과 = {}", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}