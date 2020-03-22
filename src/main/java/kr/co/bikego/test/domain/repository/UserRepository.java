package kr.co.bikego.test.domain.repository;

import kr.co.bikego.test.domain.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}
