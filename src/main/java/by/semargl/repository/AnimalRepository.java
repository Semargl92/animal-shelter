package by.semargl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import by.semargl.domain.Animal;
import by.semargl.domain.User;

@Repository
public interface AnimalRepository extends CrudRepository<Animal, Long>, PagingAndSortingRepository<Animal, Long>, JpaRepository<Animal, Long> {

    List<Animal> findByPatron(User patron);
}
