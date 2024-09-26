package konkuk.forcutbook.api.clova;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter @Getter
@NoArgsConstructor
public class ClovaResponse {
    private Message message;
    private int inputLength;
    private int outputLength;
    private String stopReason;
    private long seed;
    private List<AiFilter> aiFilter;

    @Setter @Getter
    @NoArgsConstructor
    public static class Message {
        private String role;
        private String content;
    }

    @Setter @Getter
    @NoArgsConstructor
    public static class AiFilter {
        private String groupName;
        private String name;
        private String score;
        private String result;
    }
}
