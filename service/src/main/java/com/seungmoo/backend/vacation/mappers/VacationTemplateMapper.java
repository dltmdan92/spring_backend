package com.seungmoo.backend.vacation.mappers;

import com.seungmoo.backend.domain.aggregates.vacation.Vacation;
import com.seungmoo.backend.domain.aggregates.vacation.VacationTemplate;
import com.seungmoo.backend.vacation.dtos.VacationDTO;
import com.seungmoo.backend.vacation.dtos.VacationTemplateDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface VacationTemplateMapper {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    VacationTemplateDTO toDTO(VacationTemplate entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    VacationDTO toDTO(Vacation entity);
}
