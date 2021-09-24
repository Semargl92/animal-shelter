package by.semargl.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "kennels")
@Data
@NoArgsConstructor
public class Kennel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer area;

    @Column(name = "is_outside")
    private Boolean isOutside;

    @OneToOne
    @JoinColumn(name = "animal_id")
    @JsonBackReference
    private Animal animal;
}
