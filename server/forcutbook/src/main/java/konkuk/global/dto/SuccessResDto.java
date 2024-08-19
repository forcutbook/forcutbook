package konkuk.global.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public class SuccessResDto {
    private final Long id;
    private final boolean success = true;
    private String message;

    public SuccessResDto(Long id, SuccessMessage successMessage) {
        this.id = id;
        this.message = successMessage.message;
    }

    public static ResponseEntity<SuccessResDto> getResponseEntity(Long id, SuccessMessage message, HttpStatus status){
        return new ResponseEntity<>(new SuccessResDto(id, message), status);
    }

    public enum SuccessMessage {
        ADD_SUCCESS("등록 성공하였습니다."),
        MODIFY_SUCCESS("수정 성공하였습니다."),
        DELETE_SUCCESS("삭제 성공하였습니다."),
        REQUEST_SUCCESS("요청 성공하였습니다"),
        ACCEPT_SUCCESS("수락 성공하였습니다");

        private String message;

        SuccessMessage(String message) {
            this.message = message;
        }
    }
}
