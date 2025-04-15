package kr.egosit.shortmaster.global.security.exception;

import kr.egosit.shortmaster.global.exception.BasicException;
import kr.egosit.shortmaster.global.exception.ErrorCode;

public class AuthFailedException extends BasicException {

    public AuthFailedException() {
        super(ErrorCode.AUTH_FAILED);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.AUTH_FAILED;
    }
}
