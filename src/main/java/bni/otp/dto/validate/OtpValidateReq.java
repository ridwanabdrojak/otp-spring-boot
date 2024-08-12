package bni.otp.dto.validate;

import bni.otp.dto.generate.OtpGenerateReq;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class OtpValidateReq extends OtpGenerateReq {

    @JsonProperty("otp_value")
    @NotBlank(message = "otp_value cannot be blank")
    private String otpValue;
}
