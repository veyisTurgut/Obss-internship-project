/*

package obss.intern.veyis.config.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashSet;


@Getter
//@Setter
@NoArgsConstructor
@Service
public class SessionManager {

    private HashSet<String> activeSessionIds;

    public byte[] generateSesionId(String username, String password){
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest((username+password).getBytes(StandardCharsets.UTF_8));
        return hash;
    }

    public void addSession(String sessionId){
        if (activeSessionIds == null){
            this.activeSessionIds = new HashSet<String>();
        }
        activeSessionIds.add(sessionId);
    }

    public Boolean removeSession(String sessionId){
        if (activeSessionIds == null){
            return false;
        }
        return activeSessionIds.remove(sessionId);
    }

    public Boolean isSessionValid(String sessionId){
        if (activeSessionIds == null){
            return false;
        }
        return activeSessionIds.contains(sessionId);
    }
}


 */