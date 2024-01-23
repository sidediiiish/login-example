package kr.lesh.login_example.repository;

import kr.lesh.login_example.entity.Auth;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository extends CrudRepository<Auth, String> {
    Boolean existsByToken(String token);
}
