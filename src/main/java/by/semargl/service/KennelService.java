package by.semargl.service;

import java.sql.SQLException;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import by.semargl.domain.Kennel;
import by.semargl.exception.NoSuchEntityException;
import by.semargl.repository.KennelRepository;
import by.semargl.requests.KennelRequest;
import by.semargl.requests.mappers.KennelMapper;

@Service
@RequiredArgsConstructor
public class KennelService {

    private final KennelRepository kennelRepository;
    private final KennelMapper kennelMapper;

    public Page<Kennel> findAllKennel() {
        return kennelRepository.findAll(PageRequest.of(1, 10, Sort.by(Sort.Direction.ASC, "id")));
    }

    public Kennel findOneKennel(Long id) {
        return kennelRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("Kennel not found by id " + id));
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public void deleteKennel(Long id) {
        kennelRepository.deleteById(id);
    }

    public Kennel createKennel(KennelRequest kennelRequest) {
        Kennel kennel = new Kennel();

        kennelMapper.updateKennelFromKennelRequest(kennelRequest, kennel);

        return kennelRepository.save(kennel);
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = SQLException.class)
    public Kennel updateKennel(Long id, KennelRequest kennelRequest) {
        Kennel kennel = kennelRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException("Kennel not found by id " + id));

        kennelMapper.updateKennelFromKennelRequest(kennelRequest, kennel);


        return kennelRepository.save(kennel);
    }
}
