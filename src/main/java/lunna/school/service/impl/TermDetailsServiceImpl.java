package lunna.school.service.impl;

import lunna.school.model.TermDetails;
import lunna.school.repository.TermDetailsRepository;
import lunna.school.service.TermDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Collins K. Sang
 * 5/18/22 2:16 PM
 * school
 * TermDetailsServiceImpl
 * IntelliJ IDEA
 **/
@Service
public class TermDetailsServiceImpl implements TermDetailsService {
    final TermDetailsRepository termDetailsRepository;

    @Autowired
    public TermDetailsServiceImpl(TermDetailsRepository termDetailsRepository) {
        this.termDetailsRepository = termDetailsRepository;
    }

    @Override
    public List<TermDetails> listAll() {
        return termDetailsRepository.findAll();
    }

    @Override
    public TermDetails getById(UUID id) {
        return termDetailsRepository.getById(id);
    }

    @Override
    public TermDetails saveOrUpdate(TermDetails termDetails) {
        return termDetailsRepository.save(termDetails);
    }

    @Override
    public Long count() {
        return termDetailsRepository.count();
    }

    @Override
    public Long count(UUID id) {
        return termDetailsRepository.count();
    }

    @Override
    public void delete(TermDetails termDetails) {
        termDetailsRepository.delete(termDetails);
    }

    @Override
    public List<TermDetails> findBySchoolId(UUID org_id) {
        return termDetailsRepository.findBySchoolId(org_id);
    }
}
