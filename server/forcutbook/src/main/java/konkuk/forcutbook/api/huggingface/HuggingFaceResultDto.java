package konkuk.forcutbook.api.huggingface;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter @Getter
public class HuggingFaceResultDto {
    private String generated_text;
}
