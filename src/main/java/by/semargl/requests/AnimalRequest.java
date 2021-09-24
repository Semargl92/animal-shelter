package by.semargl.requests;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import by.semargl.domain.Kennel;
import by.semargl.domain.User;
import by.semargl.domain.enums.Kind;

@ApiOperation("Class for creating and updating animal entity")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnimalRequest {

    private String name;

    private Kind kind;

    private Boolean hasBreed;

    private Boolean isInjured;

    private String description;

    private LocalDateTime birthDate;

    private String photo;

    private Long patronId;

    private Long kennelId;
}
