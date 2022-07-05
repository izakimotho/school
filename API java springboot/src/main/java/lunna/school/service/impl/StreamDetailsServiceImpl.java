package lunna.school.service.impl;

import lunna.school.exception.BadRequestException;
import lunna.school.model.StreamDetails;
import lunna.school.repository.StreamDetailsRepository;
import lunna.school.service.StreamDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 09. Jul 2021 8:49 AM
 **/
@Service
public class StreamDetailsServiceImpl implements StreamDetailsService {

    final StreamDetailsRepository streamDetailsRepository;

    @Autowired
    public StreamDetailsServiceImpl(StreamDetailsRepository streamDetailsRepository) {
        this.streamDetailsRepository = streamDetailsRepository;
    }

    @Override
    public List<StreamDetails> listAll() {
        return streamDetailsRepository.findAll();
    }


    @Override
    public StreamDetails getById(UUID id) {
        return streamDetailsRepository.getById(id);
    }


    @Override
    public StreamDetails saveOrUpdate(StreamDetails streamDetails) {
        return streamDetailsRepository.saveAndFlush(streamDetails);
    }

    @Override
    public Long count() {
        return null;
    }

    @Override
    public Long count(UUID id) {
        return null;
    }

    @Override
    public void delete(StreamDetails streamDetails) {
        try {
            streamDetailsRepository.delete(streamDetails);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
    }


    @Override
    public List<StreamDetails> getByClassStream(UUID school_stream_id) {
        return streamDetailsRepository.getStreamDetailsBySchool_stream(school_stream_id);
    }

    @Override
    public List<StreamDetails> getByStreamSchoolOrgId(UUID school_id) {
        return streamDetailsRepository.getStreamDetailsBySchool(school_id);
    }
}
