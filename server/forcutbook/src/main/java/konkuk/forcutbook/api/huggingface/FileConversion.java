package konkuk.forcutbook.api.huggingface;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileConversion {
    public static List<byte[]> multipartFileToBinary(List<MultipartFile> multipartFiles) {

        List<byte[]> imageDataList = new ArrayList<>();
        try {
            for (MultipartFile multipartFile : multipartFiles) {
                imageDataList.add(multipartFile.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imageDataList;
    }
}
