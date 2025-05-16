package com.cbao.eerprospect.service.impl;

import com.cbao.eerprospect.domain.Civility;
import com.cbao.eerprospect.repository.CivilityRepository;
import com.cbao.eerprospect.service.CivilityService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.cbao.eerprospect.domain.Civility}.
 */
@Service
@Transactional
public class CivilityServiceImpl implements CivilityService {

    private static final Logger LOG = LoggerFactory.getLogger(CivilityServiceImpl.class);

    private final CivilityRepository civilityRepository;

    public CivilityServiceImpl(CivilityRepository civilityRepository) {
        this.civilityRepository = civilityRepository;
    }

    @Override
    public Civility save(Civility civility) {
        LOG.debug("Request to save Civility : {}", civility);
        return civilityRepository.save(civility);
    }

    @Override
    public Civility update(Civility civility) {
        LOG.debug("Request to update Civility : {}", civility);
        return civilityRepository.save(civility);
    }

    @Override
    public Optional<Civility> partialUpdate(Civility civility) {
        LOG.debug("Request to partially update Civility : {}", civility);

        return civilityRepository
            .findById(civility.getId())
            .map(existingCivility -> {
                if (civility.getCode() != null) {
                    existingCivility.setCode(civility.getCode());
                }
                if (civility.getLabel() != null) {
                    existingCivility.setLabel(civility.getLabel());
                }

                return existingCivility;
            })
            .map(civilityRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Civility> findAll(Pageable pageable) {
        LOG.debug("Request to get all Civilities");
        return civilityRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Civility> findOne(Long id) {
        LOG.debug("Request to get Civility : {}", id);
        return civilityRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Civility : {}", id);
        civilityRepository.deleteById(id);
    }
}
