package bni.otp.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final String code;
    private final String logsId;
    private final String action;

    public CustomException(String code, String message, String logsId, String action) {
        super(message);
        this.code = code;
        this.logsId = logsId;
        this.action = action;
    }
}
