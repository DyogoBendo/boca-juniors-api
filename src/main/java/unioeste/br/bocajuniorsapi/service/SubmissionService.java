package unioeste.br.bocajuniorsapi.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import unioeste.br.bocajuniorsapi.domain.Exercise;
import unioeste.br.bocajuniorsapi.domain.Submission;
import unioeste.br.bocajuniorsapi.dto.SubmissionFormDTO;
import unioeste.br.bocajuniorsapi.repository.SubmissionRepository;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@Service
public class SubmissionService {
    private SubmissionRepository submissionRepository;

    public void save(Submission submission){
        submissionRepository.save(submission);
    }

    public List<Submission> list(){
        return submissionRepository.findAll();
    }

    public Submission findById(Long id){
        return submissionRepository.findById(id).orElse(null);
    }

    public Submission convert(SubmissionFormDTO form, Exercise exercise){
        Submission submission = new Submission();
        submission.setAccepted(false);
        submission.setSourceCode(form.getSourceCode());
        submission.setExercise(exercise);
        submission.setUsername(form.getUsername());

        return submission;
    }
}
