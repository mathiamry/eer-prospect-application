package com.cbao.eerprospect.service.impl;

import com.cbao.eerprospect.domain.FamilyStatus;
import com.cbao.eerprospect.repository.FamilyStatusRepository;
import com.cbao.eerprospect.service.FamilyStatusService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.cbao.eerprospect.domain.FamilyStatus}.
 */
@Service
@Transactional
public class FamilyStatusServiceImpl implements FamilyStatusService {

    private static final Logger LOG = LoggerFactory.getLogger(FamilyStatusServiceImpl.class);

    private final FamilyStatusRepository familyStatusRepository;

    public FamilyStatusServiceImpl(FamilyStatusRepository familyStatusRepository) {
        this.familyStatusRepository = familyStatusRepository;
    }

    @Override
    public FamilyStatus save(FamilyStatus familyStatus) {
        LOG.debug("Request to save FamilyStatus : {}", familyStatus);
        return familyStatusRepository.save(familyStatus);
    }

    @Override
    public FamilyStatus update(FamilyStatus familyStatus) {
        LOG.debug("Request to update FamilyStatus : {}", familyStatus);
        return familyStatusRepository.save(familyStatus);
    }

    @Override
    public Optional<FamilyStatus> partialUpdate(FamilyStatus familyStatus) {
        LOG.debug("Request to partially update FamilyStatus : {}", familyStatus);

        return familyStatusRepository
            .findById(familyStatus.getId())
            .map(existingFamilyStatus -> {
                if (familyStatus.getCode() != null) {
                    existingFamilyStatus.setCode(familyStatus.getCode());
                }
                if (familyStatus.getLabel() != null) {
                    existingFamilyStatus.setLabel(familyStatus.getLabel());
                }

                return existingFamilyStatus;
            })
            .map(familyStatusRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FamilyStatus> findAll(Pageable pageable) {
        LOG.debug("Request to get all FamilyStatuses");
        return familyStatusRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FamilyStatus> findOne(Long id) {
        LOG.debug("Request to get FamilyStatus : {}", id);
        return familyStatusRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FamilyStatus : {}", id);
        familyStatusRepository.deleteById(id);
    }
}
