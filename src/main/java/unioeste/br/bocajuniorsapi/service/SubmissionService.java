package unioeste.br.bocajuniorsapi.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import unioeste.br.bocajuniorsapi.domain.Exercise;
import unioeste.br.bocajuniorsapi.domain.Submission;
import unioeste.br.bocajuniorsapi.domain.SubmissionStatus;
import unioeste.br.bocajuniorsapi.dto.*;
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
        submission.setStatus(SubmissionStatus.IQ);
        submission.setSourceCode(form.getSourceCode());
        submission.setExercise(exercise);
        submission.setUsername(form.getUsername());
        submission.setTimeLimit(10d);
        submission.setLanguage("C++");
        submission.setMemoryLimit(10000);
        submission.setSizeLimit(10000);

        return submission;
    }

    public Specification<Submission> generateSpecification(Object object, String name){
        SearchCriteria criteria = new SearchCriteria(name, ":", object);
        return new SubmissionSpecification(criteria);
    }

    public Specification<Submission> generateAllSpecification(SubmissionFilterDTO submissionFilterDTO, Exercise exercise){
        Specification<Submission> usernameSpecification = generateSpecification(submissionFilterDTO.getUsername(), "username");
        Specification<Submission> exerciseSpecification = generateSpecification(exercise, "exercise");
        Specification<Submission> statusSpecification = generateSpecification(submissionFilterDTO.getStatus(), "status");
        return Specification.where(usernameSpecification).and(exerciseSpecification).and(statusSpecification);
    }
    public List<Submission> findSubmissionsByExerciseList(List<Exercise> exerciseList){
        List<Submission> submissions = new ArrayList<>();

        for (Exercise exercise : exerciseList){
            List<Submission> exerciseSubmissions = filter(new SubmissionFilterDTO(), exercise);
            submissions.addAll(exerciseSubmissions);
        }

        return submissions;
    }

    public void delete(List<Submission> submissions){
        submissionRepository.deleteAll(submissions);
    }

    public SubmissionQueueDTO convert(Submission submission, List<TestCaseDTO> testCaseDTOList){
        SubmissionQueueDTO submissionQueueDTO = new SubmissionQueueDTO();

        submissionQueueDTO.setId(submission.getId());
        submissionQueueDTO.setSourceCode(submission.getSourceCode());
        submissionQueueDTO.setTimeLimit(submission.getTimeLimit());
        submissionQueueDTO.setMemoryLimit(submission.getMemoryLimit());
        submissionQueueDTO.setSizeLimit(submission.getSizeLimit());
        submissionQueueDTO.setLanguage(submission.getLanguage());
        submissionQueueDTO.setTestCases(testCaseDTOList);

        return submissionQueueDTO;
    }

    public void update(Submission submission, SubmissionResultDTO submissionResultDTO){
        submission.setStatus(submissionResultDTO.getVerdict());
    }
}
