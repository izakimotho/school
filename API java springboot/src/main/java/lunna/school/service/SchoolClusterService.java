package lunna.school.service;

import lunna.school.model.SchoolCluster;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 14. Jul 2021 4:24 PM
 **/
public interface SchoolClusterService extends IService<SchoolCluster> {
    SchoolCluster getByName(String cluster_name);
}
