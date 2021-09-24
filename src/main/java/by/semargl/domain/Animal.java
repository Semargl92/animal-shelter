package by.semargl.domain;

import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import by.semargl.domain.enums.Kind;

@Entity
@Table(name = "animals")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {
        "kennel"
})
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    @Enumerated(EnumType.STRING)
    private Kind kind = Kind.NOT_SELECTED;

    @Column(name = "has_breed")
    private Boolean hasBreed;

    @Column(name = "is_injured")
    private Boolean isInjured;

    @Column
    private String description;

    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    @Column(name = "date_of_receiving")
    private LocalDateTime dateOfReceiving;

    @Column
    private String photo;

    @ManyToOne
    @JoinColumn(name = "patron_id")
    @JsonBackReference
    private User user;

    @OneToOne(mappedBy = "animal_id", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Kennel kennel;
}
