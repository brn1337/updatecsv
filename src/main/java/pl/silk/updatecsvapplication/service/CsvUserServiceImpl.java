package pl.silk.updatecsvapplication.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import pl.silk.shared.converter.CollectionToPageConverter;
import pl.silk.updatecsvapplication.controller.model.CsvCriteria;
import pl.silk.updatecsvapplication.controller.model.CsvView;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CsvUserServiceImpl implements CsvUserService {

    private final FinderService finderService;

    @Override
    public Page<CsvView> findByCriteria(CsvCriteria csvCriteria, Pageable pageQuery) {
        List<CsvView> searchResult = finderService.searchByCriteria(csvCriteria);
        return CollectionToPageConverter.generatePage(pageQuery, searchResult);
    }

    @Override
    public ResponseEntity<Long> countById() {
        return ResponseEntity.ok(finderService.countAllUsers());
    }

    @Override
    public ResponseEntity<List<CsvView>> deleteByManual(List<CsvView> csvViewList) {
        return ResponseEntity.ok(finderService.deleteByList(csvViewList));
    }
}
