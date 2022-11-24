package s3_gps_ivanti.dto.login;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenDTO {
    private String subject;
    private List<String> roles;
    private String userID;
    private String sign;

    @JsonIgnore
    public boolean hasRole(String roleName) {
        if (roles == null) {
            return false;
        }
        return roles.contains(roleName);
    }
}
