package lunna.school.service.impl;

import lunna.school.dto.*;
import lunna.school.exception.RecordNotFoundException;
import lunna.school.model.*;
import lunna.school.repository.FeeStructureRepository;
import lunna.school.service.FeeStructureService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Collins K. Sang
 * 5/19/22 9:55 AM
 * school
 * FeeStructureServiceImpl
 * IntelliJ IDEA
 **/
@Service
public class FeeStructureServiceImpl implements FeeStructureService {
    final FeeStructureRepository feeStructureRepository;
    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public FeeStructureServiceImpl(FeeStructureRepository feeStructureRepository) {
        this.feeStructureRepository = feeStructureRepository;
    }

    @Override
    public List<FeeStructure> listAll() {
        return feeStructureRepository.findAll();
    }

    @Override
    public FeeStructure getById(UUID id) {
        return feeStructureRepository.getById(id);
    }

    @Override
    public FeeStructure saveOrUpdate(FeeStructure feeStructure) {
        return feeStructureRepository.save(feeStructure);
    }

    @Override
    public Long count() {
        return feeStructureRepository.count();
    }

    @Override
    public Long count(UUID id) {
        return feeStructureRepository.count();
    }

    @Override
    public void delete(FeeStructure feeStructure) {
        feeStructureRepository.delete(feeStructure);
    }

    @Override
    public List<FeeStructure> findBySchoolId(UUID academic_year, UUID org_id) {
        return feeStructureRepository.findBySchoolId(academic_year, org_id);
    }

    @Override
    public List<FeeStructure> findBySchoolIdAndTerm(UUID academic_year, UUID term, UUID org_id) {
        return feeStructureRepository.findBySchoolIdAndTerm(academic_year, term, org_id);
    }

    @Override
    public List<FeeStructure> findBySchoolIdAndClassAndTerm(UUID academic_year, UUID term, UUID org_id, UUID class_id) {
        return feeStructureRepository.findBySchoolIdAndClassAndTerm(academic_year, term, org_id, class_id);

    }

    @Override
    public List<FeeStructure> findBySchoolIdAndClassAndYear(UUID academic_year, UUID org_id, UUID class_id) {
        return feeStructureRepository.findBySchoolIdAndClassAndYear(academic_year, org_id, class_id);

    }

    @Override
    public List<FeeStructure> findBySchoolIdAndYear(UUID academic_year, UUID org_id) {
        return feeStructureRepository.findBySchoolIdAndYear(academic_year, org_id);
    }

    @Override
    public void deletePerYear(UUID academic_year, UUID org_id) {
        feeStructureRepository.deletePerYear(academic_year, org_id);
    }

    @Override
    public void deletePerClass(UUID class_id, UUID org_id) {
        feeStructureRepository.deletePerClass(class_id, org_id);
    }

    @Override
    public List<FeeStructureDetailsDto> findSumFeePerSchool(UUID academic_year_id, UUID org_id) {
        return feeStructureRepository.findSumFeePerSchool(academic_year_id, org_id);
    }

    @Override
    public List<FeeStructureDtos> findBySchoolIdAndClass(UUID org_id, UUID class_id) {
        List<FeeStructure> feeStructureDtos = feeStructureRepository.findByClass(org_id, class_id);
        List<TermFeeDto> feeStructureDto1;
        List<FeeTermDto> feeStructureDtoList = null;
        FeeTermDto feeTermDto = new FeeTermDto();
        List<FeeStructureDtos> feeStructureDtosList;
        List<VoteHeadDetailsDto> voteHeadDetailsDtos = null;
//        List<VoteHeadDetailsDto> voteHeadDetailsDtos = feeStructureDtos.stream().map(
//                fvd -> new VoteHeadDetailsDto(
//                        modelMapper.map(fvd.getFee_vote(), FeeVoteHeadDto.class),
//                        fvd.getAmount()
//                )).collect(Collectors.toList());
        double term_fee = 0.0;
        for (VoteHeadDetailsDto f : voteHeadDetailsDtos) {
            term_fee += f.getAmount();
        }

        double termFeeTotal = term_fee;
        feeStructureDto1 = null;
//        feeStructureDto1 = feeStructureDtos.stream().map(
//                fs -> new TermFeeDto(
//                        new FeeTermDto(
//                                fs.getTerm_details().getTerms().getName(),
//                                voteHeadDetailsDtos
//                        ),
//                        termFeeTotal
//                )
//        ).collect(Collectors.toList());

        double total_fee = 0.0;
        for (TermFeeDto f : feeStructureDto1) {
            total_fee += f.getTerm_total_amount();
        }

        double feeTotal = total_fee;

        feeStructureDtosList = feeStructureDtos.stream().map(
                fs -> new FeeStructureDtos(
                        fs.getFee_structure_id(),
                        feeTotal,
                        feeStructureDto1,
                        modelMapper.map(fs.getClass_model(), ClassModelDto.class),
                        modelMapper.map(fs.getAcademic_year(), AcademicYearMiniDto.class)
                )
        ).collect(Collectors.toList());


        return feeStructureDtosList;
    }

    @Override
    public FeeStructureDTO getBySchoolAndClass(UUID org_id, UUID class_id) {
        List<FeeStructure> feeStructures = feeStructureRepository.findByClass(org_id, class_id);

        Map<UUID, FeeTermDTO> termsMap = new HashMap<>();
        ClassModel classModel = new ClassModel();
        AcademicYear academicYear = new AcademicYear();

        for (FeeStructure feeStructure : feeStructures) {

//            FeeVoteHeadDTO feeVoteHeadDTO = new FeeVoteHeadDTO(
//                    feeStructure.getFee_structure_id().toString(),
//                    feeStructure.getFee_vote().getVote_head_name(),
//                    feeStructure.getAmount()
//            );
//            FeeTermDTO feeTermDTO = new FeeTermDTO();
//
//            if (termsMap.containsKey(feeStructure.getTerm_details().getTerms().getTerm_id())){
//                FeeTermDTO feeTermDTOList = termsMap.get(feeStructure.getTerm_details().getTerms().getTerm_id());
//                feeTermDTOList.getVote_heads().add(feeVoteHeadDTO);
//
//            }else {
//                List<FeeVoteHeadDTO> feeVoteHeadDTOS = new ArrayList<>();
//                feeVoteHeadDTOS.add(feeVoteHeadDTO);
//                feeTermDTO.setTerm_name(feeStructure.getTerm_details().getTerms().getName());
//                feeTermDTO.setVote_heads(feeVoteHeadDTOS);
//                termsMap.put(feeStructure.getTerm_details().getTerms().getTerm_id(),feeTermDTO);
//            }
            classModel = feeStructure.getClass_model();
            academicYear = feeStructure.getAcademic_year();


        }
        List<FeeTermDTO> feeTermDTOList = new ArrayList<>();
        termsMap.forEach((key, value) -> feeTermDTOList.add(value));

        FeeStructureDTO feeStructureDTO = new FeeStructureDTO(
                feeTermDTOList,
                modelMapper.map(classModel, ClassModelDto.class),
                modelMapper.map(academicYear, AcademicYearDto.class)
        );


        return feeStructureDTO;
    }

    @Override
    public FeeStructureDTO getByFeeStructureId(UUID feeStructureId) {
        FeeStructure feeStructures = feeStructureRepository.findByFeeStructureId(feeStructureId);

        if (feeStructures == null) {
            throw new RecordNotFoundException("Fee Structure NOT FOUND");
        }

        Map<UUID, FeeTermDTO> termsMap = new HashMap<>();
        for (VoteHeadFee voteHeadFee : feeStructures.getVoteHeadFees()) {
            FeeVoteHeadDTO feeVoteHeadDTO = new FeeVoteHeadDTO(
                    voteHeadFee.getFeeStructure().getFee_structure_id().toString(),
                    voteHeadFee.getVoteHead().getVote_head_name(),
                    voteHeadFee.getAmount()
            );
            FeeTermDTO feeTermDTO = new FeeTermDTO();

            if (termsMap.containsKey(voteHeadFee.getTermDetails().getTerms().getTerm_id())) {
                FeeTermDTO feeTermDTOList = termsMap.get(voteHeadFee.getTermDetails().getTerms().getTerm_id());
                feeTermDTOList.getVote_heads().add(feeVoteHeadDTO);

            } else {
                List<FeeVoteHeadDTO> feeVoteHeadDTOS = new ArrayList<>();
                feeVoteHeadDTOS.add(feeVoteHeadDTO);
                feeTermDTO.setTerm_name(voteHeadFee.getTermDetails().getTerms().getName());
                feeTermDTO.setVote_heads(feeVoteHeadDTOS);
                termsMap.put(voteHeadFee.getTermDetails().getTerms().getTerm_id(), feeTermDTO);
            }
        }
        List<FeeTermDTO> feeTermDTOList = new ArrayList<>();
        termsMap.forEach((key, value) -> feeTermDTOList.add(value));

        FeeStructureDTO feeStructureDTO = new FeeStructureDTO(
                feeTermDTOList,
                modelMapper.map(feeStructures.getClass_model(), ClassModelDto.class),
                modelMapper.map(feeStructures.getAcademic_year(), AcademicYearDto.class)
        );


        return feeStructureDTO;
    }

    @Override
    public FeeStructureDto saveFeeStructure(FeeStructureReq feeStructureReq, User user) {
        FeeStructure feeStructure = null;
        feeStructure = feeStructureRepository.findByAcademicYearAndClassModel(
                feeStructureReq.getAcademicYear().getAcademic_year_id(),
                feeStructureReq.getClassModel().getClass_id());

        if (feeStructure == null) {
            feeStructure = new FeeStructure();
            feeStructure.setCreated_by(user);
            feeStructure.setAcademic_year(feeStructureReq.getAcademicYear());
            feeStructure.setClass_model(feeStructureReq.getClassModel());

        }
        System.out.println(feeStructure.getFee_structure_id());

        Set<VoteHeadFee> voteHeadFees = new HashSet<>();
        VoteHeadFee voteHeadFee = new VoteHeadFee();
        voteHeadFee.setFeeStructure(feeStructure);
        voteHeadFee.setVoteHead(feeStructureReq.getVoteHead());
        voteHeadFee.setTermDetails(feeStructureReq.getTermDetails());
        voteHeadFee.setAmount(feeStructureReq.getAmount());
        feeStructure.getVoteHeadFees().add(voteHeadFee);

        FeeStructure feeStructure1 = feeStructureRepository.saveAndFlush(feeStructure);

        return modelMapper.map(feeStructure1, FeeStructureDto.class);
    }
}
