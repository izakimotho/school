package lunna.school.controller;

import lunna.school.dto.ApiResponse;
import lunna.school.model.SchoolCluster;
import lunna.school.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 01. Jul 2021 8:33 AM
 **/
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class SchoolClusterController {

    final IService<SchoolCluster> clusterIService;

    @Autowired
    public SchoolClusterController(IService<SchoolCluster> clusterIService) {
        this.clusterIService = clusterIService;
    }
    @PostAuthorize("hasAuthority(\"can_view_school_cluster\")")
    @GetMapping("/schools/clusters")
    public ApiResponse getSchoolCluster() {

        return new ApiResponse(
                clusterIService.listAll(),
                "",
                HttpStatus.OK.value()
        );

    }
}
