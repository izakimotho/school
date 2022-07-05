package lunna.school.service.impl;

import lunna.school.model.Terms;
import lunna.school.repository.TermsRepository;
import lunna.school.service.TermsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Collins K. Sang
 * 5/18/22 10:31 AM
 * school
 * TermsServiceImpl
 * IntelliJ IDEA
 **/
@Service
public class TermsServiceImpl implements TermsService {
    final TermsRepository termsRepository;

    @Autowired
    public TermsServiceImpl(TermsRepository termsRepository) {
        this.termsRepository = termsRepository;
    }

    @Override
    public List<Terms> listAll() {
        return termsRepository.findAll();
    }

    @Override
    public Terms getById(UUID id) {
        return termsRepository.getById(id);
    }

    @Override
    public Terms saveOrUpdate(Terms terms) {
        return termsRepository.save(terms);
    }

    @Override
    public Long count() {
        return termsRepository.count();
    }

    @Override
    public Long count(UUID id) {
        return termsRepository.findById(id).stream().count();
    }

    @Override
    public void delete(Terms terms) {
        termsRepository.delete(terms);
    }

    @Override
    public List<Terms> findBySchoolId(UUID org_id) {
        return termsRepository.findBySchoolId(org_id);
    }
}
