package bni.otp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OtpResSuccess {

    @JsonProperty("logs_id")
    private String logsId;
    @JsonProperty("nik")
    private String nik;
    @JsonProperty("key")
    private String key;
}
