package pl.silk.updatecsvapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.silk.updatecsvapplication.controller.model.CsvView;
import pl.silk.updatecsvapplication.db.entity.CsvUser;
import pl.silk.updatecsvapplication.db.repository.UserRepository;
import pl.silk.updatecsvapplication.service.mapper.CsvViewMapper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements  FileService {

    private static final String DELIMITER = ";";
    private final CsvViewMapper csvViewMapper;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<List<CsvView>> uploadedMultipartFileToDb(MultipartFile multipartFile) throws IOException {
        List<CsvView> csvViewList = parseCsv(multipartFile.getResource());
        uploadOrDeleteUserToDb(csvViewList, true);
        return ResponseEntity.ok(csvViewList);
    }

    @Override
    public ResponseEntity<List<CsvView>> deleteFromDbByMultipart(MultipartFile multipartFile) throws IOException {
        List<CsvView> csvViewList = parseCsv(multipartFile.getResource());
        uploadOrDeleteUserToDb(csvViewList, false);
        return ResponseEntity.ok(csvViewList);
    }

    private void uploadOrDeleteUserToDb(List<CsvView> csvViewList, boolean update) {
        List<CsvUser> entityList = csvViewMapper.toCsvUser(csvViewList);
        if(update) {
            userRepository.saveAll(entityList);
        } else {
            userRepository.deleteAll(entityList);
        }
    }

    private List<CsvView> parseCsv(Resource resource) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(resource.getFile()));
        String line;
        int firstLine = 0;
        List<CsvView> csvViewList = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            if(firstLine != 0) {
                String[] userCsv = line.split(DELIMITER);
                csvViewList.add(buildCsvView(userCsv[0], userCsv[1], userCsv[2], userCsv[3]));
            }
            firstLine++;
        }
        br.close();
        return csvViewList;
    }

    private CsvView buildCsvView(String firstName, String lastName, String birthDate, String phoneNumber) {
        return CsvView.builder()
                .firstName(firstName)
                .surname(lastName)
                .age(birthDateToAge(birthDate))
                .phoneNumber(phoneNumber)
                .build();
    }

    private Long birthDateToAge(String birthDate) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
            LocalDate birthDateLocalDate = LocalDate.parse(birthDate, formatter);
            LocalDate now = LocalDate.now();
            return ChronoUnit.YEARS.between(birthDateLocalDate, now);
        }
}
