package by.semargl.domain;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import by.semargl.domain.enums.Gender;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {
        "animals",  "roles"
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @Column
    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.NOT_SELECTED;

    @Column(name = "birth_date")
    private LocalDateTime birthDate;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "login", column = @Column(name = "login")),
            @AttributeOverride(name = "password", column = @Column(name = "password"))
    })
    private Credentials credentials;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Animal> animals = Collections.emptySet();

    @ManyToMany(mappedBy = "users", cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("users")
    private Set<Role> roles = Collections.emptySet();
}
