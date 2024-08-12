package bni.otp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "otp_list")
public class Otp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;
    @Column(name = "key")
    private String key;
    @Column(name = "nik")
    private String nik;
    @Column(name = "otp_code")
    private String otpCode;
    @Column(name = "attempt_generate")
    private int attemptGenerate;
    @Column(name = "attempt_fail")
    private int attemptFail;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;
    @Column(name = "is_used")
    private boolean isUsed;
}
