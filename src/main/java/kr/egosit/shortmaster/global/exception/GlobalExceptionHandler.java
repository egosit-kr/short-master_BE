package kr.egosit.shortmaster.global.exception;


import kr.egosit.shortmaster.global.basic.response.BasicResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BasicException.class)
    public ResponseEntity<BasicResponse.BaseResponse> basicException(BasicException e) {
        return BasicResponse.error(e.getErrorCode());
    }

    private ResponseEntity<BasicResponse.BaseResponse> createErrorResponse(ErrorCode errorCode, Exception e) {
        log.error("Exception: ${e.javaClass.simpleName}", e);
        return BasicResponse.error(errorCode);
    }

    private ResponseEntity<BasicResponse.BaseResponse> createCustomErrorResponse(String message, HttpStatus status,  BasicResponse.BaseStatus baseStatus, Exception e) {
        log.error("Exception: ${e.javaClass.simpleName}", e);
        return BasicResponse.customStatus(message, status, baseStatus);
    }
}
