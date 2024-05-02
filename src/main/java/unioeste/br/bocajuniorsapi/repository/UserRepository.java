package unioeste.br.bocajuniorsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unioeste.br.bocajuniorsapi.domain.User;

public interface UserRepository extends JpaRepository<User, String> {

}
