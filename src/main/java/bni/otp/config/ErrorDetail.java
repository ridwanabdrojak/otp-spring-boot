package bni.otp.config;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDetail {

    private String code;
    private String message;
}
