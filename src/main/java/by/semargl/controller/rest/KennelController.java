package by.semargl.controller.rest;

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

import by.semargl.domain.Kennel;
import by.semargl.requests.KennelRequest;
import by.semargl.service.KennelService;

@RestController
@RequestMapping("/kennel")
@RequiredArgsConstructor
public class KennelController {

    private final KennelService kennelService;

    @ApiOperation(value = "find all kennels")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Kennels were successfully found")
    })
    @GetMapping
    public Page<Kennel> findAll() {
        return kennelService.findAllKennel();
    }

    @ApiOperation(value = "find one kennel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "kennelId", dataType = "string", paramType = "path",
                    value = "id of kennel for search", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Kennel was successfully found"),
            @ApiResponse(code = 500, message = "There is no kennel with such id")
    })
    @GetMapping("/{kennelId}")
    public Kennel findOne(@PathVariable("kennelId") Long id) {
        return kennelService.findOneKennel(id);
    }

    @ApiOperation(value = "remove kennel from the database")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "kennelId", dataType = "string", paramType = "path",
                    value = "id of kennel for deleting from database", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Kennel was successfully deleted"),
            @ApiResponse(code = 500, message = "There is no kennel with such id")
    })
    @DeleteMapping("/admin/{kennelId}")
    public void delete(@PathVariable("kennelId") Long id) {
        kennelService.deleteKennel(id);
    }

    @ApiOperation(value = "create one kennel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Kennel was successfully created")
    })
    @PostMapping("/admin")
    public Kennel create(@RequestBody KennelRequest kennelRequest) {
        return kennelService.createKennel(kennelRequest);
    }

    @ApiOperation(value = "update one kennel")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "kennelId", dataType = "string", paramType = "path",
                    value = "id of kennel for update", required = true),
            @ApiImplicitParam(name = "X-Auth-Token", value = "token", required = true,
                    dataType = "string", paramType = "header")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Kennel was successfully updated"),
            @ApiResponse(code = 500, message = "There is no kennel with such id")
    })
    @PutMapping("/admin/{kennelId}")
    public Kennel update(@PathVariable("kennelId") Long id, @RequestBody KennelRequest kennelRequest) {
        return kennelService.updateKennel(id, kennelRequest);
    }
}
