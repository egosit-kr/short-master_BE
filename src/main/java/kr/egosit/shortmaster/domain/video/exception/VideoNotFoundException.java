package kr.egosit.shortmaster.domain.video.exception;

import kr.egosit.shortmaster.global.exception.BasicException;
import kr.egosit.shortmaster.global.exception.ErrorCode;

public class VideoNotFoundException extends BasicException {
    public VideoNotFoundException() {
        super(ErrorCode.VIDEO_NOT_FOUND);
    }

    @Override
    public ErrorCode getErrorCode() {
        return ErrorCode.VIDEO_NOT_FOUND;
    }
}
