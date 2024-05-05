package unioeste.br.bocajuniorsapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unioeste.br.bocajuniorsapi.domain.Exercise;
import unioeste.br.bocajuniorsapi.domain.Submission;
import unioeste.br.bocajuniorsapi.domain.TestCase;
import unioeste.br.bocajuniorsapi.dto.SubmissionFilterDTO;
import unioeste.br.bocajuniorsapi.dto.SubmissionFormDTO;
import unioeste.br.bocajuniorsapi.dto.SubmissionQueueDTO;
import unioeste.br.bocajuniorsapi.dto.TestCaseDTO;
import unioeste.br.bocajuniorsapi.rabbitmq.MessageProducer;
import unioeste.br.bocajuniorsapi.service.ExerciseService;
import unioeste.br.bocajuniorsapi.service.SubmissionService;
import unioeste.br.bocajuniorsapi.service.TestCaseService;
import unioeste.br.bocajuniorsapi.utils.JSONMapper;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@RestController
@RequestMapping("/submission")
@CrossOrigin
public class SubmissionController {
    private SubmissionService submissionService;
    private ExerciseService exerciseService;
    private TestCaseService testCaseService;
    private MessageProducer messageProducer;

    @GetMapping
    public ResponseEntity<List<Submission>> list(SubmissionFilterDTO form){
        Exercise exercise = exerciseService.findById(form.getExerciseId());
        List<Submission> submissions = submissionService.filter(form, exercise);

        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Submission> get(@PathVariable Long id){
        Submission submission = submissionService.findById(id);

        return ResponseEntity.ok(submission);
    }

    @PostMapping
    public ResponseEntity<Submission> create(@RequestBody SubmissionFormDTO form) throws JsonProcessingException {
        Exercise exercise = exerciseService.findById(form.getExerciseId());

        Submission submission = submissionService.convert(form, exercise);

        submissionService.save(submission);

        List<TestCase> testCaseList = testCaseService.findByExercise(exercise);
        List<TestCaseDTO> testCaseDTOList = testCaseService.convert(testCaseList);

        SubmissionQueueDTO submissionQueueDTO = submissionService.convert(submission, testCaseDTOList);
        String submissionJson = JSONMapper.map(submissionQueueDTO);

        messageProducer.sendMessage(submissionJson);
        return ResponseEntity.ok(submission);
    }
}
