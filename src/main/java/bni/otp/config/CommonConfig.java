package bni.otp.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class CommonConfig {

    @Value("${param.attempt.req.otp}")
    private int attemptReqOtp;
    @Value("${param.limit.req.otp}")
    private int limitReqOtp;
    @Value("${param.interval.req.otp}")
    private long intervalReqOtp;
    @Value("${param.attempt.fail.otp}")
    private int attemptFailOtp;
    @Value("${param.limit.fail.otp}")
    private int limitFailOtp;
    @Value("${param.interval.fail.otp}")
    private long intervalFailOtp;
    @Value("${param.interval.validate.otp}")
    private long intervalValidateOtp;
    private final String generateString = "generate";
    private final String validateString = "validate";
}
