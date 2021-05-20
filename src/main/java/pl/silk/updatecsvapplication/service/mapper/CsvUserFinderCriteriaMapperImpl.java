package pl.silk.updatecsvapplication.service.mapper;

import org.springframework.stereotype.Service;
import pl.silk.updatecsvapplication.controller.model.CsvView;
import pl.silk.updatecsvapplication.db.entity.CsvUser;

@Service
public class CsvUserFinderCriteriaMapperImpl implements CsvUserFinderCriteriaMapper{
    @Override
    public CsvView toCsvUserFinderCriteria(CsvUser csvUser) {
        return CsvView.builder()
                .firstName(csvUser.getFirstName())
                .surname(csvUser.getLastName())
                .age(csvUser.getAge())
                .phoneNumber(csvUser.getPhoneNumber())
                .build();
    }
}
