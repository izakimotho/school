package lunna.school.service.impl;

import lunna.school.model.VoteHead;
import lunna.school.repository.FeeVoteHeadRepository;
import lunna.school.service.FeeVoteHeadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Collins K. Sang
 * 5/18/22 9:34 AM
 * school
 * FeeVoteHeadServiceImpl
 * IntelliJ IDEA
 **/

@Service
public class FeeVoteHeadServiceImpl implements FeeVoteHeadService {
    final FeeVoteHeadRepository feeVoteHeadRepository;

    @Autowired
    public FeeVoteHeadServiceImpl(FeeVoteHeadRepository feeVoteHeadRepository) {
        this.feeVoteHeadRepository = feeVoteHeadRepository;
    }

    @Override
    public List<VoteHead> listAll() {
        return feeVoteHeadRepository.findAll();
    }

    @Override
    public VoteHead getById(UUID id) {
        return feeVoteHeadRepository.getById(id);
    }

    @Override
    public VoteHead saveOrUpdate(VoteHead voteHead) {
        return feeVoteHeadRepository.save(voteHead);
    }

    @Override
    public Long count() {
        return feeVoteHeadRepository.count();
    }

    @Override
    public Long count(UUID id) {
        return (long) feeVoteHeadRepository.findAllById(Collections.singleton(id)).size();
    }

    @Override
    public void delete(VoteHead voteHead) {
        feeVoteHeadRepository.delete(voteHead);
    }

    @Override
    public List<VoteHead> findBySchoolId(UUID org_id) {
        return feeVoteHeadRepository.findBySchoolId(org_id);
    }

//    @Override
//    public List<FeeVoteHead> findByTermId(UUID term_id) {
//        return feeVoteHeadRepository.findByTermId(term_id);
//
//    }
}
