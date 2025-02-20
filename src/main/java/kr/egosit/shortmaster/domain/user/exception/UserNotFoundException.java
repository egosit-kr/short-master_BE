package kr.egosit.shortmaster.domain.user.exception;

import kr.egosit.shortmaster.global.exception.BasicException;
import kr.egosit.shortmaster.global.exception.ErrorCode;

public class UserNotFoundException extends BasicException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.USER_NOT_FOUND;
    }
}
