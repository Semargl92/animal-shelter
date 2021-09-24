package by.semargl.requests.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import by.semargl.domain.Kennel;
import by.semargl.requests.KennelRequest;

@Mapper(componentModel = "spring")
public interface KennelMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateKennelFromKennelRequest(KennelRequest kennelRequest, @MappingTarget Kennel kennel);
}
