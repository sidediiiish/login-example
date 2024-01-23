package kr.lesh.login_example.entity;


import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value="access_token", timeToLive=3600*24)
public class Auth {

    @Id
    private String authId;

    @Indexed
    private String token;

    public Auth() {
    }
    private  Auth(String token) {
        this.token = token;
    }

    public static Auth create(String token) {
        return new Auth(token);
    }
}
