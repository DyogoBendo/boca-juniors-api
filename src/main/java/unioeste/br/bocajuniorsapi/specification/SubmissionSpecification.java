package unioeste.br.bocajuniorsapi.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;
import unioeste.br.bocajuniorsapi.domain.Submission;
import unioeste.br.bocajuniorsapi.utils.SearchCriteria;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
public class SubmissionSpecification implements Specification<Submission> {
    private final SearchCriteria searchCriteria;
    @Override
    public Specification<Submission> and(Specification<Submission> other) {
        return Specification.super.and(other);
    }

    @Override
    public Specification<Submission> or(Specification<Submission> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<Submission> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if(searchCriteria.getValue() == null){
            return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
        }
        else if (searchCriteria.getOperation().equalsIgnoreCase(">")) {
            if(searchCriteria.getValue().getClass() == LocalDate.class){
                return criteriaBuilder.greaterThanOrEqualTo(root.get(searchCriteria.getKey()), (LocalDateTime) searchCriteria.getValue());
            }
        }
        else if (searchCriteria.getOperation().equalsIgnoreCase("<")) {
            if(searchCriteria.getValue().getClass() == LocalDate.class){
                return criteriaBuilder.lessThanOrEqualTo(root.get(searchCriteria.getKey()), (LocalDateTime) searchCriteria.getValue());
            }
        }
        else if (searchCriteria.getOperation().equalsIgnoreCase(":")) {
            return criteriaBuilder.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue());

        }
        return null;
    }
}
