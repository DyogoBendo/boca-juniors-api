package unioeste.br.bocajuniorsapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import unioeste.br.bocajuniorsapi.domain.Submission;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
}
