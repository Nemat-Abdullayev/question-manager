package az.cybernet.mapper;

import az.cybernet.model.entity.question.Question;
import az.cybernet.model.view.QuestionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class QuestionMapper {
    public static final QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    @Mappings({
            @Mapping(target = "content", source = "question.content"),
            @Mapping(target = "difficulty", source = "question.difficulty"),
            @Mapping(target = "confirmed", source = "question.confirmed")
    })

    public abstract QuestionResponse mapEntityToView(Question question);

}
