package unioeste.br.bocajuniorsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unioeste.br.bocajuniorsapi.domain.ExercisesGroup;

public interface ExercisesGroupRepository extends JpaRepository<ExercisesGroup, Long> {
}
