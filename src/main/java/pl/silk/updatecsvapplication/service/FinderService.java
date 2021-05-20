package pl.silk.updatecsvapplication.service;

import pl.silk.updatecsvapplication.controller.model.CsvCriteria;
import pl.silk.updatecsvapplication.controller.model.CsvView;

import java.util.List;

public interface FinderService {

    List<CsvView> searchByCriteria(CsvCriteria csvCriteria);

    List<CsvView> deleteByList(List<CsvView> csvViewList);

    Long countAllUsers();
}
