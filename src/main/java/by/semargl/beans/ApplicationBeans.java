package by.semargl.beans;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import by.semargl.requests.mappers.KennelMapper;
import by.semargl.requests.mappers.AnimalMapper;
import by.semargl.requests.mappers.UserCreateMapper;
import by.semargl.requests.mappers.UserMapper;

@Configuration
public class ApplicationBeans {

    @Bean
    public NoOpPasswordEncoder noOpPasswordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public UserMapper userMapper() {
        return Mappers.getMapper(UserMapper.class);
    }

    @Bean
    public KennelMapper kennelMapper() {
        return Mappers.getMapper(KennelMapper.class);
    }

    @Bean
    public AnimalMapper animalMapper() {
        return Mappers.getMapper(AnimalMapper.class);
    }

    @Bean
    public UserCreateMapper userCreateMapper() {
        return Mappers.getMapper(UserCreateMapper.class);
    }
}
