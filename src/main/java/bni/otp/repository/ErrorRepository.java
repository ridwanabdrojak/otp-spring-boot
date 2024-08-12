package bni.otp.repository;

import java.util.HashMap;
import java.util.Map;

import bni.otp.config.ErrorDetail;

public class ErrorRepository {

    private final Map<String, ErrorDetail> paramError;

    public ErrorRepository() {
        paramError = new HashMap<>();
        paramError.put("bad_request", new ErrorDetail("9001", "BAD REQUEST"));
        paramError.put("otp_fail", new ErrorDetail("9002", "TOO MANY OTP FAILURES"));
        paramError.put("otp_req", new ErrorDetail("9003", "TOO MANY OTP REQUESTS"));
        paramError.put("generate_otp", new ErrorDetail("9004", "FAILED TO GENERATE OTP"));
        paramError.put("otp_exp", new ErrorDetail("9004", "OTP HAS EXPIRED"));
        paramError.put("fail_validate", new ErrorDetail("9005", "FAILED TO VALIDATE OTP"));
        paramError.put("otp_used", new ErrorDetail("9006", "OTP HAS USED"));
        paramError.put("internal_exception", new ErrorDetail("9099", "INTERNAL SERVER ERROR"));
    }

    public String getErrorCode(String key) {
        ErrorDetail errorDetail = paramError.get(key);
        return (errorDetail != null) ? errorDetail.getCode() : "0000";
    }

    public String getErrorMessage(String key) {
        ErrorDetail errorDetail = paramError.get(key);
        return (errorDetail != null) ? errorDetail.getMessage() : "Unknown error";
    }
}
