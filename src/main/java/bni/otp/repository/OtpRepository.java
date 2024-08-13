package bni.otp.repository;

import bni.otp.entity.Otp;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface OtpRepository extends JpaRepository<Otp, Integer> {

    @Query(value = "select attempt_generate from otp_list where nik = ?1 and modified_at >= ?2", nativeQuery = true)
    Integer selectAttemptReq(String nik, LocalDateTime interval);

    @Query(value = "select attempt_fail from otp_list where nik = ?1 and modified_at >= ?2", nativeQuery = true)
    Integer selectAttemptFail(String nik, LocalDateTime interval);

    @Query(value = "select 1 from otp_list where nik = ?1", nativeQuery = true)
    Integer selectNikExist(String nik);

    @Query(value = "select otp_code from otp_list where nik = ?1 and key = ?2 and modified_at >= ?3", nativeQuery = true)
    String selectOtpCode(String nik, String key, LocalDateTime interval);

    @Query(value = "select is_used from otp_list where nik = ?1", nativeQuery = true)
    boolean selectIsUsed(String nik);

    @Modifying
    @Transactional
    @Query(value = "insert into otp_list (nik, key, otp_code, attempt_generate, attempt_fail, created_at, modified_at, is_used) values (?1, ?2, ?3, ?4, ?5, current_timestamp, current_timestamp, FALSE)", nativeQuery = true)
    void insertOtp(String nik, String key, String otpCode, int attemptReq, int attemptFail);

    @Modifying
    @Transactional
    @Query(value = "update otp_list set key = ?1, otp_code = ?2, attempt_generate = ?3, attempt_fail = 0, modified_at = current_timestamp, is_used = FALSE where nik = ?4", nativeQuery = true)
    void resetOtp(String key, String otpCode, int attemptReq, String nik);

    @Modifying
    @Transactional
    @Query(value = "update otp_list set key = ?1, otp_code = ?2, attempt_generate = ?3, modified_at = current_timestamp, is_used = FALSE where nik = ?4", nativeQuery = true)
    void updateAttemptReqOtp(String key, String otpCode, int attemptReq, String nik);

    @Modifying
    @Transactional
    @Query(value = "update otp_list set attempt_fail = ?1, modified_at = current_timestamp where nik = ?2", nativeQuery = true)
    void updateAttemptFailOtp(int attemptFail, String nik);

    @Modifying
    @Transactional
    @Query(value = "update otp_list set is_used = TRUE, modified_at = current_timestamp where nik = ?1", nativeQuery = true)
    void updateOtpIsUsed(String nik);
}
