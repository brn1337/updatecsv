package pl.silk.updatecsvapplication.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import pl.silk.updatecsvapplication.controller.model.CsvCriteria;
import pl.silk.updatecsvapplication.controller.model.CsvView;

import java.util.List;

public interface CsvUserService {

    Page<CsvView> findByCriteria(CsvCriteria csvCriteria, Pageable pageQuery);

    ResponseEntity<Long> countById();

    ResponseEntity<List<CsvView>> deleteByManual(List<CsvView> csvViewList);
}
