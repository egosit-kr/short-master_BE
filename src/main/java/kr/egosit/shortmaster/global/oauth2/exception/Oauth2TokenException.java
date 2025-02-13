package kr.egosit.shortmaster.global.oauth2.exception;

import kr.egosit.shortmaster.global.exception.BasicException;
import kr.egosit.shortmaster.global.exception.ErrorCode;

public class Oauth2TokenException extends BasicException {
    public Oauth2TokenException() {
        super(ErrorCode.OAUTH2_TOKEN_NOT_VALID);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.OAUTH2_TOKEN_NOT_VALID;
    }
}
