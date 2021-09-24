package by.semargl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import by.semargl.domain.Animal;
import by.semargl.domain.Kennel;
import by.semargl.domain.User;

@Repository
public interface AnimalRepository extends CrudRepository<Animal, Long>, PagingAndSortingRepository<Animal, Long>, JpaRepository<Animal, Long> {

    List<Animal> findByPatron(User patron);

    @Modifying
    @Query(value = "update Animal s set s.kennel = :kennel where s.id = :animalId")
    void updateAnimalsKennel(@Param("animalId") Long animalId, @Param("kennel") Kennel kennel);
}
