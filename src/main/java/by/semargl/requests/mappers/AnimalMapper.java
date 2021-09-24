package by.semargl.requests.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import by.semargl.domain.Animal;
import by.semargl.requests.AnimalRequest;

@Mapper(componentModel = "spring")
public interface AnimalMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAnimalFromAnimalRequest(AnimalRequest animalRequest, @MappingTarget Animal animal);
}
