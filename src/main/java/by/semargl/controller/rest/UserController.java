package by.semargl.controller.rest;

import java.util.List;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import by.semargl.domain.Credentials;
import by.semargl.domain.User;
import by.semargl.requests.UserCreateRequest;
import by.semargl.requests.UserRequest;
import by.semargl.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "find all users with credentials")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Users were successfully found")
    })
    @GetMapping("/admin")
    public Page<User> findAllWithCredentials() {
        return userService.findAllUsersWithCredentials();
    }

    @ApiOperation(value = "find all users")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true, dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Users were successfully found")
    })
    @GetMapping
    public List<UserRequest> findAll() {
        return userService.findAllUsersWithLogin();
    }

    @ApiOperation(value = "find one user with credentials")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "string", paramType = "path",
                    value = "id of user for search", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User was successfully found"),
            @ApiResponse(code = 500, message = "There is no user with such id")
    })
    @GetMapping("/admin/{userId}")
    public User findOneWithCredentials(@PathVariable("userId") Long id) {
        return userService.findOneUserWithCredentials(id);
    }

    @ApiOperation(value = "find one user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "string", paramType = "path",
                    value = "id of user for search", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User was successfully found"),
            @ApiResponse(code = 500, message = "There is no user with such id")
    })
    @GetMapping("/{userId}")
    public UserRequest findOne(@PathVariable("userId") Long id) {
        return userService.findOneUserWithLogin(id);
    }

    @ApiOperation(value = "remove user from the database")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "string", paramType = "path",
                    value = "id of user for deleting from database", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User was successfully deleted"),
            @ApiResponse(code = 500, message = "There is no user with such id")
    })
    @DeleteMapping("/admin/{userId}")
    public void delete(@PathVariable("userId") Long id) {
        userService.deleteUser(id);
    }


    @ApiOperation(value = "create one user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User was successfully created"),
            @ApiResponse(code = 500, message = "User with this login already exists, please try another option")
    })
    @PostMapping("/create")
    public UserRequest create(@RequestBody UserCreateRequest userCreateRequest) {
        return userService.createUser(userCreateRequest);
    }

    @ApiOperation(value = "update one user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "string", paramType = "path",
                    value = "id of user for update", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User was successfully updated"),
            @ApiResponse(code = 500, message = "There is no user with such id")
    })
    @PutMapping("/{userId}")
    public UserRequest update(@PathVariable("userId") Long id, @RequestBody UserRequest userRequest) {
        return userService.updateUser(id, userRequest);
    }

    @ApiOperation(value = "update credentials for user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", dataType = "string", paramType = "path",
                    value = "id of user for update", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Credentials were successfully updated"),
            @ApiResponse(code = 500, message = "There is no user with such id")
    })
    @PatchMapping("/update_credentials/{userId}")
    public Credentials updateCredentials(@PathVariable("userId") Long id, @RequestBody Credentials credentials) {
        return userService.updateCredentials(id, credentials);
    }

    @ApiOperation(value = "find users with name")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", dataType = "string", paramType = "query",
                    value = "name for search", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Users were successfully found"),
            @ApiResponse(code = 500, message = "There are no users with similar name")
    })
    @GetMapping("/name")
    public List<UserRequest> findByName(@RequestParam String name) {
       return userService.findUserByName(name);
    }

    @ApiOperation(value = "find users with name and surname")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", dataType = "string", paramType = "query",
                    value = "name for search", required = true),
            @ApiImplicitParam(name = "surname", dataType = "string", paramType = "query",
                    value = "surname for search", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Users were successfully found"),
            @ApiResponse(code = 500, message = "There are no users with similar name and surname")
    })
    @GetMapping("/name_and_surname")
    public List<UserRequest> findByNameAndSurname(@RequestParam String name, @RequestParam String surname) {
        return userService.findUserByNameAndSurname(name, surname);
    }

    @ApiOperation(value = "find user by login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "login", dataType = "string", paramType = "query",
                    value = "login for search", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "User was successfully found"),
            @ApiResponse(code = 500, message = "There is no user with such login")
    })
    @GetMapping("/admin/login")
    public User findByLogin(@RequestParam String login) {
        return userService.findByLogin(login);
    }
}
