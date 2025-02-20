package kr.egosit.shortmaster.domain.item.exception;

import kr.egosit.shortmaster.global.exception.BasicException;
import kr.egosit.shortmaster.global.exception.ErrorCode;

public class ItemNotFoundException extends BasicException {
    public ItemNotFoundException() {
        super(ErrorCode.ITEM_NOT_FOUND);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.ITEM_NOT_FOUND;
    }
}
