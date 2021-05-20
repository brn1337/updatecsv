package pl.silk.updatecsvapplication.service.mapper;

import org.mapstruct.Mapper;
import pl.silk.updatecsvapplication.controller.model.CsvView;
import pl.silk.updatecsvapplication.db.entity.CsvUser;

import java.util.List;

@Mapper
public interface CsvViewMapper {

    CsvUser toCsvUser(CsvView csvView);

    List<CsvUser> toCsvUser(List<CsvView> csvViewList);
}
