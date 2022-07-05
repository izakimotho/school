package lunna.school.service.impl;

import lunna.school.model.Sms;
import lunna.school.repository.SmsRepository;
import lunna.school.repository.StudentRepository;
import lunna.school.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 18. Aug 2021 9:59 AM
 **/
@Service
public class SmsServiceImpl implements SmsService {
    @Autowired
    SmsRepository smsRepository;
    @Autowired
    StudentRepository studentRepository;
    @Override
    public List<Sms> listAll() {
        return smsRepository.findAll();
    }

    @Override
    public Sms getById(UUID id) {
        return smsRepository.getById(id);
    }

    @Override
    public Sms saveOrUpdate(Sms sms) {
        return smsRepository.saveAndFlush(sms);
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
    public void delete(Sms sms) {
        smsRepository.delete(sms);

    }

    @Override
    public List<String> recipient(UUID school_id) {
        return studentRepository.smsRecipient(school_id);
    }
}
