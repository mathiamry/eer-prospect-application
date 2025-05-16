package com.cbao.eerprospect.service.impl;

import com.cbao.eerprospect.domain.IncomeType;
import com.cbao.eerprospect.repository.IncomeTypeRepository;
import com.cbao.eerprospect.service.IncomeTypeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.cbao.eerprospect.domain.IncomeType}.
 */
@Service
@Transactional
public class IncomeTypeServiceImpl implements IncomeTypeService {

    private static final Logger LOG = LoggerFactory.getLogger(IncomeTypeServiceImpl.class);

    private final IncomeTypeRepository incomeTypeRepository;

    public IncomeTypeServiceImpl(IncomeTypeRepository incomeTypeRepository) {
        this.incomeTypeRepository = incomeTypeRepository;
    }

    @Override
    public IncomeType save(IncomeType incomeType) {
        LOG.debug("Request to save IncomeType : {}", incomeType);
        return incomeTypeRepository.save(incomeType);
    }

    @Override
    public IncomeType update(IncomeType incomeType) {
        LOG.debug("Request to update IncomeType : {}", incomeType);
        return incomeTypeRepository.save(incomeType);
    }

    @Override
    public Optional<IncomeType> partialUpdate(IncomeType incomeType) {
        LOG.debug("Request to partially update IncomeType : {}", incomeType);

        return incomeTypeRepository
            .findById(incomeType.getId())
            .map(existingIncomeType -> {
                if (incomeType.getCode() != null) {
                    existingIncomeType.setCode(incomeType.getCode());
                }
                if (incomeType.getLabel() != null) {
                    existingIncomeType.setLabel(incomeType.getLabel());
                }

                return existingIncomeType;
            })
            .map(incomeTypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IncomeType> findAll(Pageable pageable) {
        LOG.debug("Request to get all IncomeTypes");
        return incomeTypeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IncomeType> findOne(Long id) {
        LOG.debug("Request to get IncomeType : {}", id);
        return incomeTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete IncomeType : {}", id);
        incomeTypeRepository.deleteById(id);
    }
}
