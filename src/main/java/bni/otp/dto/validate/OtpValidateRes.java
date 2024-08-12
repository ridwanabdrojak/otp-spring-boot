package bni.otp.dto.validate;

import bni.otp.dto.OtpResSuccess;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OtpValidateRes extends OtpResSuccess {

    @JsonProperty("status_validate")
    private String statusValidate;
    @JsonProperty("attempt_fail")
    private String attemptFail;
}