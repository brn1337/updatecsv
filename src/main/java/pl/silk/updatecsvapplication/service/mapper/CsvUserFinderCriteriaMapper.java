package pl.silk.updatecsvapplication.service.mapper;

import pl.silk.updatecsvapplication.controller.model.CsvView;
import pl.silk.updatecsvapplication.db.entity.CsvUser;

public interface CsvUserFinderCriteriaMapper {

    CsvView toCsvUserFinderCriteria(CsvUser csvUser);
}
