package com.cbao.eerprospect.service.impl;

import com.cbao.eerprospect.domain.Prospect;
import com.cbao.eerprospect.repository.ProspectRepository;
import com.cbao.eerprospect.service.ProspectService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.cbao.eerprospect.domain.Prospect}.
 */
@Service
@Transactional
public class ProspectServiceImpl implements ProspectService {

    private static final Logger LOG = LoggerFactory.getLogger(ProspectServiceImpl.class);

    private final ProspectRepository prospectRepository;

    public ProspectServiceImpl(ProspectRepository prospectRepository) {
        this.prospectRepository = prospectRepository;
    }

    @Override
    public Prospect save(Prospect prospect) {
        LOG.debug("Request to save Prospect : {}", prospect);
        return prospectRepository.save(prospect);
    }

    @Override
    public Prospect update(Prospect prospect) {
        LOG.debug("Request to update Prospect : {}", prospect);
        return prospectRepository.save(prospect);
    }

    @Override
    public Optional<Prospect> partialUpdate(Prospect prospect) {
        LOG.debug("Request to partially update Prospect : {}", prospect);

        return prospectRepository
            .findById(prospect.getId())
            .map(existingProspect -> {
                if (prospect.getLastName() != null) {
                    existingProspect.setLastName(prospect.getLastName());
                }
                if (prospect.getFirstName() != null) {
                    existingProspect.setFirstName(prospect.getFirstName());
                }
                if (prospect.getDateOfBirth() != null) {
                    existingProspect.setDateOfBirth(prospect.getDateOfBirth());
                }
                if (prospect.getCityOfBirth() != null) {
                    existingProspect.setCityOfBirth(prospect.getCityOfBirth());
                }
                if (prospect.getCountryOfBirth() != null) {
                    existingProspect.setCountryOfBirth(prospect.getCountryOfBirth());
                }
                if (prospect.getNationality() != null) {
                    existingProspect.setNationality(prospect.getNationality());
                }
                if (prospect.getMotherLastName() != null) {
                    existingProspect.setMotherLastName(prospect.getMotherLastName());
                }
                if (prospect.getMotherFirstName() != null) {
                    existingProspect.setMotherFirstName(prospect.getMotherFirstName());
                }
                if (prospect.getWifeLastName() != null) {
                    existingProspect.setWifeLastName(prospect.getWifeLastName());
                }
                if (prospect.getWifeFirstName() != null) {
                    existingProspect.setWifeFirstName(prospect.getWifeFirstName());
                }
                if (prospect.getFamilyStatusLabel() != null) {
                    existingProspect.setFamilyStatusLabel(prospect.getFamilyStatusLabel());
                }
                if (prospect.getCountryOfResidence() != null) {
                    existingProspect.setCountryOfResidence(prospect.getCountryOfResidence());
                }
                if (prospect.getCity() != null) {
                    existingProspect.setCity(prospect.getCity());
                }
                if (prospect.getAddressLine() != null) {
                    existingProspect.setAddressLine(prospect.getAddressLine());
                }
                if (prospect.getPhoneNumber() != null) {
                    existingProspect.setPhoneNumber(prospect.getPhoneNumber());
                }
                if (prospect.getEmail() != null) {
                    existingProspect.setEmail(prospect.getEmail());
                }
                if (prospect.getIdPaperType() != null) {
                    existingProspect.setIdPaperType(prospect.getIdPaperType());
                }
                if (prospect.getIdPaperNumber() != null) {
                    existingProspect.setIdPaperNumber(prospect.getIdPaperNumber());
                }
                if (prospect.getIdPaperDeliveryDate() != null) {
                    existingProspect.setIdPaperDeliveryDate(prospect.getIdPaperDeliveryDate());
                }
                if (prospect.getIdPaperDeliveryPlace() != null) {
                    existingProspect.setIdPaperDeliveryPlace(prospect.getIdPaperDeliveryPlace());
                }
                if (prospect.getIdPaperValidityDate() != null) {
                    existingProspect.setIdPaperValidityDate(prospect.getIdPaperValidityDate());
                }
                if (prospect.getProfessionCategory() != null) {
                    existingProspect.setProfessionCategory(prospect.getProfessionCategory());
                }
                if (prospect.getProfession() != null) {
                    existingProspect.setProfession(prospect.getProfession());
                }
                if (prospect.getEmployer() != null) {
                    existingProspect.setEmployer(prospect.getEmployer());
                }
                if (prospect.getIncomeAmount() != null) {
                    existingProspect.setIncomeAmount(prospect.getIncomeAmount());
                }

                return existingProspect;
            })
            .map(prospectRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Prospect> findAll(Pageable pageable) {
        LOG.debug("Request to get all Prospects");
        return prospectRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Prospect> findOne(Long id) {
        LOG.debug("Request to get Prospect : {}", id);
        return prospectRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Prospect : {}", id);
        prospectRepository.deleteById(id);
    }
}
