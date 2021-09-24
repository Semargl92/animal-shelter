package by.semargl.requests;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiOperation("Class for creating and updating kennel entity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KennelRequest {

    private Integer area;

    private Boolean isOutside;
}
