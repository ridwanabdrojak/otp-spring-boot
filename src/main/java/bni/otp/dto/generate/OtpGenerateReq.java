package bni.otp.dto.generate;

import jakarta.validation.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OtpGenerateReq {

    @JsonProperty("logs_id")
    @NotBlank(message = "logs_id cannot be blank")
    private String logsId;
    @JsonProperty("nik")
    @NotBlank(message = "nik cannot be blank")
    private String nik;
    @JsonProperty("key")
    @NotBlank(message = "key cannot be blank")
    private String key;
}
