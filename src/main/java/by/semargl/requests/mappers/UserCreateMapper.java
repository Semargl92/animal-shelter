package by.semargl.requests.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import by.semargl.domain.User;
import by.semargl.requests.UserCreateRequest;

@Mapper(componentModel = "spring")
public interface UserCreateMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromUserCreateRequest(UserCreateRequest userCreateRequest, @MappingTarget User user);
}
