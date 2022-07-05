package lunna.school.service.impl;

import lunna.school.dto.SchoolDtoExt;
import lunna.school.exception.BadRequestException;
import lunna.school.model.Organization;
import lunna.school.repository.SchoolRepository;
import lunna.school.service.SchoolService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 29. Jun 2021 7:47 AM
 **/
@Service
public class SchoolServiceImpl implements SchoolService {
    private final SchoolRepository schoolRepository;
    ModelMapper modelMapper = new ModelMapper();

    public SchoolServiceImpl(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    @Override
    public Organization saveSchool(SchoolDtoExt schoolDto) {
        if (schoolDto.getOrg_name() == null) {
            throw new BadRequestException("School Name required");

        }

        if (schoolDto.getCode() == null) {
            throw new BadRequestException("School code required");
        }
        Organization organization = modelMapper.map(schoolDto, Organization.class);

        if (schoolDto.getParentSchool() != null) {
            Organization parent = schoolRepository.getById(schoolDto.getParentSchool().getOrg_id());
            organization.setParentSchool(parent);
        }
        try {
            return schoolRepository.saveAndFlush(organization);
        } catch (DataIntegrityViolationException e) {
            System.out.println(e.getMessage());
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public List<Organization> listSchools() {

        return schoolRepository.findAllByDeletedAtIsNull();
    }

    @Override
    public List<Organization> listSchools(UUID id) {
        return schoolRepository.findByOrgIdAndDeletedAtIsNull(id);
    }

    @Override
    public Organization getSchoolById(UUID id) {
        return schoolRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Invalid School Id:" + id));
    }

    @Override
    public Organization updateSchool(SchoolDtoExt schoolDto) {
        if (schoolDto.getOrg_name() == null) {
            throw new BadRequestException("School Name required");
        }

        if (schoolDto.getCode() == null) {
            throw new BadRequestException("School code required");
        }
        Organization organization = schoolRepository.getById(schoolDto.getOrg_id());
//        Organization organization = modelMapper.map(schoolDto, Organization.class);

        organization.setCode(schoolDto.getCode());
        organization.setEducation_system(schoolDto.getEducation_system());
        organization.setCounty(schoolDto.getCounty());
        organization.setEmail_address(schoolDto.getEmail_address());
        organization.setMobile_phone_numbers(schoolDto.getMobile_phone_numbers());
        organization.setOrg_name(schoolDto.getOrg_name());
        organization.setPostal_address(schoolDto.getPostal_address());
        organization.setSchool_category(schoolDto.getSchool_category());
        organization.setSchool_cluster(schoolDto.getSchool_cluster());
        organization.setSchool_gender(schoolDto.getSchool_gender());
        organization.setSchool_history(schoolDto.getSchool_history());
        organization.setSchool_level(schoolDto.getSchool_level());
        organization.setSchool_sponsor(schoolDto.getSchool_sponsor());
        organization.setSchool_type(schoolDto.getSchool_type());
        organization.setSub_county(schoolDto.getSub_county());
        organization.setWard(schoolDto.getWard());
        try{
            return schoolRepository.saveAndFlush(organization);
        }catch (DataIntegrityViolationException e){
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public void delete(Organization org) {
        try {
            schoolRepository.delete(org);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
    }

    @Override
    public void softDelete(Organization org) {
        schoolRepository.deleteSchool(org.getOrg_id());
    }
}
