package com.cbao.eerprospect.service.impl;

import com.cbao.eerprospect.domain.IncomePeriodicity;
import com.cbao.eerprospect.repository.IncomePeriodicityRepository;
import com.cbao.eerprospect.service.IncomePeriodicityService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.cbao.eerprospect.domain.IncomePeriodicity}.
 */
@Service
@Transactional
public class IncomePeriodicityServiceImpl implements IncomePeriodicityService {

    private static final Logger LOG = LoggerFactory.getLogger(IncomePeriodicityServiceImpl.class);

    private final IncomePeriodicityRepository incomePeriodicityRepository;

    public IncomePeriodicityServiceImpl(IncomePeriodicityRepository incomePeriodicityRepository) {
        this.incomePeriodicityRepository = incomePeriodicityRepository;
    }

    @Override
    public IncomePeriodicity save(IncomePeriodicity incomePeriodicity) {
        LOG.debug("Request to save IncomePeriodicity : {}", incomePeriodicity);
        return incomePeriodicityRepository.save(incomePeriodicity);
    }

    @Override
    public IncomePeriodicity update(IncomePeriodicity incomePeriodicity) {
        LOG.debug("Request to update IncomePeriodicity : {}", incomePeriodicity);
        return incomePeriodicityRepository.save(incomePeriodicity);
    }

    @Override
    public Optional<IncomePeriodicity> partialUpdate(IncomePeriodicity incomePeriodicity) {
        LOG.debug("Request to partially update IncomePeriodicity : {}", incomePeriodicity);

        return incomePeriodicityRepository
            .findById(incomePeriodicity.getId())
            .map(existingIncomePeriodicity -> {
                if (incomePeriodicity.getCode() != null) {
                    existingIncomePeriodicity.setCode(incomePeriodicity.getCode());
                }
                if (incomePeriodicity.getLabel() != null) {
                    existingIncomePeriodicity.setLabel(incomePeriodicity.getLabel());
                }

                return existingIncomePeriodicity;
            })
            .map(incomePeriodicityRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<IncomePeriodicity> findAll(Pageable pageable) {
        LOG.debug("Request to get all IncomePeriodicities");
        return incomePeriodicityRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<IncomePeriodicity> findOne(Long id) {
        LOG.debug("Request to get IncomePeriodicity : {}", id);
        return incomePeriodicityRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete IncomePeriodicity : {}", id);
        incomePeriodicityRepository.deleteById(id);
    }
}
