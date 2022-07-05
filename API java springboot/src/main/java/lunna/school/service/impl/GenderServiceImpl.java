package lunna.school.service.impl;

import lunna.school.model.GenderEnum;
import lunna.school.model.UserType;
import lunna.school.service.GenderService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * IntelliJ IDEA
 * school
 * GenderServiceImpl
 *
 * @author Collins K. Sang
 * 2021/07/21 12:14
 **/
@Service
public class GenderServiceImpl implements GenderService {
    @Override
    public Map<String, String> getGenderTypes() {
        return Arrays.stream(GenderEnum.values())
                .collect(Collectors.toMap(GenderEnum::name, GenderEnum::getLabel));
    }
}
