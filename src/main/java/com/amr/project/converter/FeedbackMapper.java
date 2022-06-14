package com.amr.project.converter;


import com.amr.project.model.dto.FeedbackDto;
import com.amr.project.model.entity.Feedback;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FeedbackMapper {

    @Mapping(target = "userId", source = "user.id")
    FeedbackDto toDto(Feedback feedback);

    Feedback toModel(FeedbackDto feedbackDto);
}
