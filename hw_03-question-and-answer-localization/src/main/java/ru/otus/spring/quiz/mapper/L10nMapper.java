package ru.otus.spring.quiz.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import ru.otus.spring.quiz.domain.Answer;
import ru.otus.spring.quiz.domain.Question;
import ru.otus.spring.quiz.service.L10nService;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class L10nMapper {

    @Autowired
    protected L10nService l10nService;

    @Mapping(target = "formulation", expression = "java( l10nService.getMessage(answerVariant.getFormulation()) )")
    public abstract Answer.Variant localizeVariant(Answer.Variant answerVariant);
    public abstract List<Answer.Variant> localizeVariants(List<Answer.Variant> answerVariants);

    public abstract Answer localizeAnswer(Answer answer);

    @Mapping(target = "formulation", expression = "java( l10nService.getMessage(question.getFormulation()) )")
    public abstract Question localizeQuestion(Question question);
    public abstract List<Question> localizeQuestions(List<Question> questions);

}
