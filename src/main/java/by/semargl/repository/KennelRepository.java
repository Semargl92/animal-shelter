package by.semargl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import by.semargl.domain.Kennel;

@Repository
public interface KennelRepository extends CrudRepository<Kennel, Long>, PagingAndSortingRepository<Kennel, Long>, JpaRepository<Kennel, Long>{
}
