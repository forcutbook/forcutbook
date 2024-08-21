package konkuk.forcutbook.diary.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
public class DiaryAddDto {
    private String title;
    private String content;
    private List<MultipartFile> images;
}
