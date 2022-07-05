package lunna.school.service;

import lunna.school.model.Ward;

import java.util.List;

/**
 * @author Korir HAron <koryrh@gmail.com>
 * @copyright (C) 2021
 * Created : 14. Jul 2021 4:03 PM
 **/

public interface WardService extends IService<Ward> {
    Ward getById(Long id);
    List<Ward> getByCode(Long code);
    Ward getByName(String ward_name);
}
