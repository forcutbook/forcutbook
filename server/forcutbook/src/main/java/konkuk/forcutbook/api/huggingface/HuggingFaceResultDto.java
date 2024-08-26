package konkuk.forcutbook.api.huggingface;

import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
public class HuggingFaceResultDto {
    private String generated_text;
}
