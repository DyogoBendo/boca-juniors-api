package unioeste.br.bocajuniorsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unioeste.br.bocajuniorsapi.domain.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
}
