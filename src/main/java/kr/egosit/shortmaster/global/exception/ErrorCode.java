package kr.egosit.shortmaster.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    //auth
    AUTH_FAILED("접근이 거부되었습니다.", HttpStatus.UNAUTHORIZED, "04010"),
    //oauth2
    OAUTH2_TOKEN_NOT_VALID("소셜 로그인 과정에서 문제가 발생하였습니다.", HttpStatus.INTERNAL_SERVER_ERROR, "15000"),
    //user
    USER_NOT_FOUND("유저를 찾을수 없습니다.", HttpStatus.NOT_FOUND, "24040"),
    //Item
    ITEM_NOT_FOUND("상품을 찾을수 없습니다.", HttpStatus.NOT_FOUND, "34040");

    private final String msg;
    private final HttpStatus status;
    private final String code;

    ErrorCode(String msg, HttpStatus status, String code) {
        this.msg = msg;
        this.status = status;
        this.code = code;
    }
}
