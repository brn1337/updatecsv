package pl.silk.updatecsvapplication.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import pl.silk.updatecsvapplication.controller.model.CsvView;

import java.io.IOException;
import java.util.List;

public interface FileService {

    ResponseEntity<List<CsvView>> uploadedMultipartFileToDb(MultipartFile multipartFile) throws IOException;

    ResponseEntity<List<CsvView>> deleteFromDbByMultipart(MultipartFile multipartFile) throws IOException;
}
