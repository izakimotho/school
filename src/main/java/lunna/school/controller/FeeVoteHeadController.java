package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.dto.FeeVoteHeadDto;
import lunna.school.exception.BadRequestException;
import lunna.school.model.VoteHead;
import lunna.school.model.User;
import lunna.school.service.FeeVoteHeadService;
import lunna.school.service.TermsService;
import lunna.school.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Collins K. Sang
 * 5/18/22 8:55 AM
 * school
 * FeeVoteHeadController
 * IntelliJ IDEA
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class FeeVoteHeadController {
    final FeeVoteHeadService feeVoteHeadService;
    final UserService userService;
    final TermsService termsService;
    ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public FeeVoteHeadController(FeeVoteHeadService feeVoteHeadService, UserService userService, TermsService termsService) {
        this.feeVoteHeadService = feeVoteHeadService;
        this.userService = userService;
        this.termsService = termsService;
    }

    @PostAuthorize("hasAuthority(\"can_add_fee_vote\")")
    @PostMapping("/fee-vote")
    public ApiResponse createFeeVote(@RequestBody VoteHead voteHead, Principal principal) {
        String created_by = principal.getName();
        User user = userService.findByUsername(created_by);
        voteHead.setCreated_by(user);
        voteHead.setOrganization(user.getOrganization());
        VoteHead voteHead1;
        FeeVoteHeadDto feeVoteHeadDto;
        try {
            voteHead1 = feeVoteHeadService.saveOrUpdate(voteHead);
            feeVoteHeadDto = modelMapper.map(voteHead1, FeeVoteHeadDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
        return new ApiResponse(feeVoteHeadDto, "Fee saved successfully!", HttpStatus.OK.value());
    }

    @PostAuthorize("hasAuthority(\"can_edit_fee_vote\")")
    @PutMapping("/fee-vote")
    public ApiResponse updateFeeVote(@RequestBody VoteHead voteHead, Principal principal) {
        String created_by = principal.getName();
        User user = userService.findByUsername(created_by);
        voteHead.setCreated_by(user);
        voteHead.setOrganization(user.getOrganization());
        VoteHead voteHead1;
        FeeVoteHeadDto feeVoteHeadDto;

        try {
            voteHead1 = feeVoteHeadService.saveOrUpdate(voteHead);
            feeVoteHeadDto = modelMapper.map(voteHead1, FeeVoteHeadDto.class);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
        return new ApiResponse(feeVoteHeadDto, "Fee updated successfully!", HttpStatus.OK.value());
    }

    @PostAuthorize("hasAuthority(\"can_view_fee_vote\")")
    @GetMapping("/fee-vote")
    public ApiResponse getFeeVotes(Principal principal) {
        String created_by = principal.getName();
        User user = userService.findByUsername(created_by);
        List<FeeVoteHeadDto> feeVoteHeadDtos;

        try {
            feeVoteHeadDtos = feeVoteHeadService.findBySchoolId(user.getOrganization().getOrg_id())
                    .stream()
                    .map(feeVoteHead -> modelMapper.map(feeVoteHead, FeeVoteHeadDto.class))
                    .collect(Collectors.toList());

        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }
        return new ApiResponse(feeVoteHeadDtos, "School Fee Vote Heads", HttpStatus.OK.value()

        );
    }

    @PostAuthorize("hasAuthority(\"can_delete_fee_vote\")")
    @DeleteMapping("/fee-vote/{vote_id}")
    public ApiResponse deleteFeeVote(@PathVariable("vote_id") String vote_id) {
        VoteHead voteHead = feeVoteHeadService.getById(UUID.fromString(vote_id));
        try {
            feeVoteHeadService.delete(voteHead);
        }catch (DataIntegrityViolationException e){
            throw new BadRequestException(e.getMostSpecificCause().getMessage());
        }

        return new ApiResponse(
                voteHead,
                "FeeVoteHead Deleted Successfully",
                HttpStatus.OK.value()
        );
    }
}
