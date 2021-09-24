package by.semargl.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import by.semargl.domain.Animal;
import by.semargl.domain.Kennel;
import by.semargl.domain.User;
import by.semargl.exception.NoSuchEntityException;
import by.semargl.repository.AnimalRepository;
import by.semargl.repository.KennelRepository;
import by.semargl.repository.UserRepository;
import by.semargl.requests.AnimalRequest;
import by.semargl.requests.mappers.AnimalMapper;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;
    private final UserRepository userRepository;
    private final KennelRepository kennelRepository;
    private final AnimalMapper animalMapper;

    public Page<Animal> findAllAnimals() {
        return animalRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id")));
    }

    public Animal findOneAnimal(Long id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("Animal not found by id " + id));
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public void deleteAnimal(Long id) {
        Animal animal = findOneAnimal(id);
        animal.setKennel(null);
        animalRepository.save(animal);
        animalRepository.delete(animal);
    }

    public Animal createAnimal(AnimalRequest animalRequest) {
        Animal animal = new Animal();
        animal.setDateOfReceiving(LocalDateTime.now());

        animalMapper.updateAnimalFromAnimalRequest(animalRequest, animal);
        animal.setKennel(kennelRepository.findById(animalRequest.getKennelId())
                .orElseThrow(() -> new NoSuchEntityException("There is no such kennel for this animal")));

        animal.setPatron(userRepository.findById(animalRequest.getPatronId())
                .orElseThrow(() -> new NoSuchEntityException("There is no such patron for this animal")));

        return animalRepository.save(animal);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public Animal updateAnimal(Long id, AnimalRequest animalRequest) {
        Animal animal = animalRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("Animal not found by id " + id));

        animalMapper.updateAnimalFromAnimalRequest(animalRequest, animal);

        if (animalRequest.getPatronId() != null ) {
            animal.setPatron(userRepository.findById(animalRequest.getPatronId())
                    .orElseThrow(() -> new NoSuchEntityException("There is no such patron for this animal")));

        }

        if (animalRequest.getKennelId() != null ) {
            animal.setKennel(kennelRepository.findById(animalRequest.getKennelId())
                    .orElseThrow(() -> new NoSuchEntityException("There is no such kennel for this animal")));
        }

        return animalRepository.save(animal);
    }

    public List<Animal> findAnimalsByPatronId(Long id) {
        User patron = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("There is no such patron for this animal"));

        List<Animal> animals = animalRepository.findByPatron(patron);

        if (animals.isEmpty()) {
            throw new NoSuchEntityException("There is no animals for this patron id");
        }
        return animals;
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.REPEATABLE_READ, rollbackFor = SQLException.class)
    public void updateAnimalsKennel(Long animalId, Long kennelId) {

        Kennel kennel = kennelRepository.findById(kennelId)
                .orElseThrow(() -> new NoSuchEntityException("There is no such kennel for this animal"));

        findOneAnimal(animalId);
        animalRepository.updateAnimalsKennel(animalId, kennel);
    }
}
