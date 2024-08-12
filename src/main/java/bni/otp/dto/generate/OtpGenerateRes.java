package bni.otp.dto.generate;

import bni.otp.dto.OtpResSuccess;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OtpGenerateRes extends OtpResSuccess {

    @JsonProperty("otp")
    private String otp;
    @JsonProperty("attempt_req")
    private String attemptReq;
}
