package s3_gps_ivanti.business.login;


import s3_gps_ivanti.dto.login.AccessTokenDTO;

public interface AccessTokenEncoder {
    String encode(AccessTokenDTO accessTokenDTO);
}
