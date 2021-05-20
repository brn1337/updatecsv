package pl.silk.updatecsvapplication.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Csv;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.silk.updatecsvapplication.controller.model.CsvCriteria;
import pl.silk.updatecsvapplication.controller.model.CsvView;
import pl.silk.updatecsvapplication.service.CsvUserService;
import pl.silk.updatecsvapplication.service.FileService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/csv/")
@RequiredArgsConstructor
public class CsvController {

    private final CsvUserService csvUserService;
    private final FileService fileService;

    @GetMapping("/query-lastname")
    public ResponseEntity<Page<CsvView>> findCsvUsersByCriteria(CsvCriteria csvCriteria,
                                                                @PageableDefault(sort = "age", direction = Sort.Direction.DESC) Pageable pageQuery) {
        log.info("/csv/query-lastname GET  request, csvCriteria: {},pageQuery: {}", csvCriteria, pageQuery);
        return ResponseEntity.ok(csvUserService.findByCriteria(csvCriteria, pageQuery));
    }

    @PostMapping(value = "/upload-csv", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<List<CsvView>> uploadCsvFileToDb(@RequestParam("file")MultipartFile multipartFile) throws IOException {
        log.info("/csv/query-lastname POST request, multipartFile : {}", multipartFile.getOriginalFilename());
        return fileService.uploadedMultipartFileToDb(multipartFile);
    }

    @GetMapping("/count-users")
    public ResponseEntity<Long> showCountedUsers() {
        log.info("/csv/count-users GET request");
        return csvUserService.countById();
    }

    @DeleteMapping(value = "/delete-by-csv", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<List<CsvView>> deleteByCsvFromDb(MultipartFile multipartFile) throws IOException {
        log.info("csv/delete-by-csv DELETE request, multipartFile : {}", multipartFile.getOriginalFilename());
        return fileService.deleteFromDbByMultipart(multipartFile);
    }

    @DeleteMapping(value = "/delete-manual")
    public ResponseEntity<List<CsvView>> deleteByManualFromDb(List<CsvView> csvViewList) {
        log.info("csv/delete-manual DELETE request, csvViewList : {}", csvViewList);
        return csvUserService.deleteByManual(csvViewList);
    }
}
