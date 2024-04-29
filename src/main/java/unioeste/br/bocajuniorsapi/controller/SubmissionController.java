package unioeste.br.bocajuniorsapi.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import unioeste.br.bocajuniorsapi.domain.Exercise;
import unioeste.br.bocajuniorsapi.domain.Submission;
import unioeste.br.bocajuniorsapi.dto.SubmissionFormDTO;
import unioeste.br.bocajuniorsapi.service.ExerciseService;
import unioeste.br.bocajuniorsapi.service.SubmissionService;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@RestController
@RequestMapping("/submission")
@CrossOrigin
public class SubmissionController {
    private SubmissionService submissionService;
    private ExerciseService exerciseService;

    @GetMapping
    public ResponseEntity<List<Submission>> list(){
        List<Submission> submissions = submissionService.list();

        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Submission> get(@PathVariable Long id){
        Submission submission = submissionService.findById(id);

        return ResponseEntity.ok(submission);
    }

    @PostMapping
    public ResponseEntity<Submission> create(@RequestBody SubmissionFormDTO form){
        Exercise exercise = exerciseService.findById(form.getExerciseId());

        Submission submission = submissionService.convert(form, exercise);

        submissionService.save(submission);

        return ResponseEntity.ok(submission);
    }
}
