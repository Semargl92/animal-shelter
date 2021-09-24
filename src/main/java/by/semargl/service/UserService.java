package by.semargl.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import by.semargl.domain.Credentials;
import by.semargl.domain.Animal;
import by.semargl.domain.User;
import by.semargl.exception.NoSuchEntityException;
import by.semargl.repository.AnimalRepository;
import by.semargl.repository.UserRepository;
import by.semargl.requests.UserCreateRequest;
import by.semargl.requests.UserRequest;
import by.semargl.requests.mappers.UserCreateMapper;
import by.semargl.requests.mappers.UserMapper;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AnimalRepository animalRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final UserCreateMapper userCreateMapper;

    public Page<User> findAllUsersWithCredentials() {
        return userRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id")));
    }

    public List<UserRequest> findAllUsersWithLogin() {
        return wrapUsersListWithUserRequest(userRepository.findAll());
    }

    public User findOneUserWithCredentials(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("User not found by id " + id));
    }

    public UserRequest findOneUserWithLogin(Long id) {
        UserRequest request = new UserRequest();
        User user = findOneUserWithCredentials(id);
        userMapper.updateUserRequestFromUser(user, request);
        request.setLogin(user.getCredentials().getLogin());
        return request;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public void deleteUserWithOrphans(Long id) {
        User user = findOneUserWithCredentials(id);
        List<Animal> animals = animalRepository.findByPatron(user);
        for (Animal animal : animals) {
            animal.setPatron(null);
            animalRepository.save(animal);
        }
        userRepository.delete(user);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public UserRequest createUser(UserCreateRequest userCreateRequest) {
        User user = new User();

        userCreateMapper.updateUserFromUserCreateRequest(userCreateRequest, user);

        user = userRepository.save(user);

        roleService.addUserForRole(2L, user.getId());

        UserRequest request = new UserRequest();
        userMapper.updateUserRequestFromUser(user, request);
        request.setLogin(user.getCredentials().getLogin());

        return request;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public UserRequest updateUser(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("User not found by id " + id));

        userMapper.updateUserFromUserRequest(userRequest, user);

        if (userRequest.getLogin() != null ) {
            Credentials loginUpdate = user.getCredentials();
            loginUpdate.setLogin(userRequest.getLogin());
            user.setCredentials(loginUpdate);
        }

        UserRequest request = new UserRequest();
        user = userRepository.save(user);
        userMapper.updateUserRequestFromUser(user, request);
        request.setLogin(user.getCredentials().getLogin());

        return request;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public Credentials updateCredentials(Long id, Credentials credentials) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("User not found by id " + id));

        Credentials forUpdate = user.getCredentials();
        if (credentials.getLogin() != null ) {
            forUpdate.setLogin(credentials.getLogin());
        }
        if (credentials.getPassword() != null ) {
            forUpdate.setPassword(credentials.getPassword());
        }
        user.setCredentials(forUpdate);
        userRepository.save(user);

        return forUpdate;
    }

    public List<UserRequest> findUserByName(String name) {
        List<User> users = userRepository.findByNameContainingIgnoreCase(name);

        if (users.isEmpty()) {
            throw new NoSuchEntityException("There is no users with similar names");
        }
        return wrapUsersListWithUserRequest(users);
    }

    public List<UserRequest> findUserByNameAndSurname(String name, String surname) {
        List<User> users = userRepository.findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCase(name, surname);

        if (users.isEmpty()) {
            throw new NoSuchEntityException("There is no users with similar names and surnames");
        }
        return wrapUsersListWithUserRequest(users);
    }

    public User findByLoginAndPassword(String login, String password) {
        Credentials credentials = new Credentials();
        credentials. setLogin(login);
        credentials.setPassword(password);
        return userRepository.findByCredentials(credentials)
                .orElseThrow(() -> new NoSuchEntityException("There is no user with such credentials"));
    }

    public User findByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new NoSuchEntityException("There is no user with such login"));
    }

    private List<UserRequest> wrapUsersListWithUserRequest (List<User> users) {
        List<UserRequest> result = new ArrayList<>();
        for (User user : users) {
            UserRequest request = new UserRequest();
            userMapper.updateUserRequestFromUser(user, request);
            request.setLogin(user.getCredentials().getLogin());
            result.add(request);
        }
        return result;
    }
}
