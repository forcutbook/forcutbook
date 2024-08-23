package konkuk.forcutbook.global.response;

import lombok.Getter;

@Getter
public class BaseResultDto {
    private Long id;

    public BaseResultDto(Long id) {
        this.id = id;
    }
}
