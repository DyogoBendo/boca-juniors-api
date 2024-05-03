package unioeste.br.bocajuniorsapi.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import unioeste.br.bocajuniorsapi.domain.Exercise;
import unioeste.br.bocajuniorsapi.domain.Submission;
import unioeste.br.bocajuniorsapi.dto.SubmissionFilterDTO;
import unioeste.br.bocajuniorsapi.dto.SubmissionFormDTO;
import unioeste.br.bocajuniorsapi.repository.SubmissionRepository;
import unioeste.br.bocajuniorsapi.specification.SubmissionSpecification;
import unioeste.br.bocajuniorsapi.utils.SearchCriteria;

import java.util.ArrayList;
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
    public List<Submission> list(Specification<Submission> specification){
        return submissionRepository.findAll(specification);
    }

    public List<Submission> filter(SubmissionFilterDTO submissionFilterDTO, Exercise exercise){
        Specification<Submission> specification = generateAllSpecification(submissionFilterDTO, exercise);
        return list(specification);
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

    public Specification<Submission> generateSpecification(Object object, String name){
        SearchCriteria criteria = new SearchCriteria(name, ":", object);
        return new SubmissionSpecification(criteria);
    }

    public Specification<Submission> generateAllSpecification(SubmissionFilterDTO submissionFilterDTO, Exercise exercise){
        Specification<Submission> usernameSpecification = generateSpecification(submissionFilterDTO.getUsername(), "username");
        Specification<Submission> exerciseSpecification = generateSpecification(exercise, "exercise");
        Specification<Submission> acceptedSpecification = generateSpecification(submissionFilterDTO.getAccepted(), "accepted");
        return Specification.where(usernameSpecification).and(exerciseSpecification).and(acceptedSpecification);
    }
    public List<Submission> findSubmissionsByExerciseList(List<Exercise> exerciseList){
        List<Submission> submissions = new ArrayList<>();

        for (Exercise exercise : exerciseList){
            List<Submission> exerciseSubmissions = filter(new SubmissionFilterDTO(), exercise);
            submissions.addAll(exerciseSubmissions);
        }

        return submissions;
    }

}
