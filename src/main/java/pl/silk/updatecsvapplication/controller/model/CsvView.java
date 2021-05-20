package pl.silk.updatecsvapplication.controller.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class CsvView {

    String firstName;
    String surname;
    Long age;
    String phoneNumber;
}
