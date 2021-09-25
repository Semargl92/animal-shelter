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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.semargl.domain.Animal;
import by.semargl.requests.AnimalRequest;
import by.semargl.service.AnimalService;

@RestController
@RequestMapping("/animal")
@RequiredArgsConstructor
public class AnimalController {

    private final AnimalService animalService;

    @ApiOperation(value = "find all animals")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Animals were successfully found")
    })
    @GetMapping
    public Page<Animal> findAll() {
        return animalService.findAllAnimals();
    }

    @ApiOperation(value = "find one animal")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "animalId", dataType = "string", paramType = "path",
                    value = "id of animal for search", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Animal was successfully found"),
            @ApiResponse(code = 500, message = "There is no animal with such id")
    })
    @GetMapping("/{animalId}")
    public Animal findOne(@PathVariable("animalId") Long id) {
        return animalService.findOneAnimal(id);
    }

    @ApiOperation(value = "remove animal from the database")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "animalId", dataType = "string", paramType = "path",
                    value = "id of animal for deleting from database", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Animal was successfully deleted"),
            @ApiResponse(code = 500, message = "There is no animal with such id")
    })
    @DeleteMapping("/admin/{animalId}")
    public void delete(@PathVariable("animalId") Long id) {
        animalService.deleteAnimal(id);
    }

    @ApiOperation(value = "create one animal")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Animal was successfully created")
    })
    @PostMapping
    public Animal create(@RequestBody AnimalRequest animalRequest) {
        return animalService.createAnimal(animalRequest);
    }

    @ApiOperation(value = "update one animal")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "animalId", dataType = "string", paramType = "path",
                    value = "id of animal for update", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Animal was successfully updated"),
            @ApiResponse(code = 500, message = "There is no animal with such id")
    })
    @PutMapping("admin/{animalId}")
    public Animal update(@PathVariable("animalId") Long id, @RequestBody AnimalRequest animalRequest) {
        return animalService.updateAnimal(id, animalRequest);
    }

    @ApiOperation(value = "find all animals for patron")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "patronId", dataType = "string", paramType = "path",
                    value = "id of patron for search", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Animals were successfully found"),
            @ApiResponse(code = 500, message = "There are no animals for such id")
    })
    @GetMapping("/for_patron/{patronId}")
    public List<Animal> findAllAnimalsForPatron(@PathVariable("patronId") Long id) {
        return animalService.findAnimalsByPatronId(id);
    }
}
