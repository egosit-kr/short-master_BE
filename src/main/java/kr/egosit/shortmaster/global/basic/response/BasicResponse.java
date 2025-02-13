package kr.egosit.shortmaster.global.basic.response;

import kr.egosit.shortmaster.global.exception.ErrorCode;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BasicResponse<T> {

    private final T data;
    private final int status;

    public BasicResponse(T data, int status) {
        this.data = data;
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public static ResponseEntity<BaseResponse> error(ErrorCode errorInfo) {
        BaseErrorResponse errorResponse = new BaseErrorResponse(errorInfo.getMsg(), errorInfo.getCode());
        BaseResponse response = new BaseResponse(BaseStatus.ERROR, errorResponse);
        return ResponseEntity
                .status(errorInfo.getStatus())
                .body(response);
    }

    public static ResponseEntity<BaseResponse> ok(Object data, HttpHeaders headers) {
        BaseResponse response = new BaseResponse(BaseStatus.SUCCESS, data);
        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(response);
    }

    public static ResponseEntity<BaseResponse> ok(Object data) {
        BaseResponse response = new BaseResponse(BaseStatus.SUCCESS, data);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    public static ResponseEntity<BaseResponse> ok(String msg) {
        MsgResponse msgResponse = new MsgResponse(msg);
        BaseResponse response = new BaseResponse(BaseStatus.SUCCESS, msgResponse);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    public static ResponseEntity<BaseResponse> customStatus(Object data, HttpStatus httpStatus, BaseStatus baseStatus) {
        BaseResponse response = new BaseResponse(baseStatus, data);
        return ResponseEntity
                .status(httpStatus)
                .body(response);
    }

    public static ResponseEntity<BaseResponse> customStatus(String data, HttpStatus httpStatus, BaseStatus baseStatus) {
        MsgResponse msgResponse = new MsgResponse(data);
        BaseResponse response = new BaseResponse(baseStatus, msgResponse);
        return ResponseEntity
                .status(httpStatus)
                .body(response);
    }

    public static ResponseEntity<BaseResponse> created(Object data) {
        BaseResponse response = new BaseResponse(BaseStatus.SUCCESS, data);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    public static class BaseResponse {
        private final BaseStatus status;
        private final Object data;

        public BaseResponse(BaseStatus status, Object data) {
            this.status = status;
            this.data = data;
        }

        public BaseStatus getStatus() {
            return status;
        }

        public Object getData() {
            return data;
        }
    }

    public static class BaseErrorResponse {
        private final String message;
        private final String code;

        public BaseErrorResponse(String message, String code) {
            this.message = message;
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public String getCode() {
            return code;
        }
    }

    public enum BaseStatus {
        SUCCESS, ERROR
    }

    public static class MsgResponse {
        private final String message;

        public MsgResponse(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }
}
