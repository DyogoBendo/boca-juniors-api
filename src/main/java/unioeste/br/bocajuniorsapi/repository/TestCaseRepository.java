package unioeste.br.bocajuniorsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unioeste.br.bocajuniorsapi.domain.Exercise;
import unioeste.br.bocajuniorsapi.domain.TestCase;

import java.util.List;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
    List<TestCase> findByExerciseAndExample(Exercise exercise, boolean example);
    List<TestCase> findByExercise(Exercise exercise);
}
