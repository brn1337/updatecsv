package pl.silk.updatecsvapplication.controller.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CsvCriteria {

    private String firstName;
    private String lastname;
    private Long age;
    private String phoneNumber;
}
