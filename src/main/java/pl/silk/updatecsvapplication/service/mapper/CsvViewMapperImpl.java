package pl.silk.updatecsvapplication.service.mapper;

import org.springframework.stereotype.Service;
import pl.silk.updatecsvapplication.controller.model.CsvView;
import pl.silk.updatecsvapplication.db.entity.CsvUser;

import java.util.ArrayList;
import java.util.List;

@Service
public class CsvViewMapperImpl implements CsvViewMapper {

    @Override
    public CsvUser toCsvUser(CsvView csvView) {
        if(csvView == null) {
            return null;
        }
        return CsvUser.builder()
                .firstName(csvView.getFirstName())
                .lastName(csvView.getSurname())
                .age(csvView.getAge())
                .phoneNumber(csvView.getPhoneNumber())
                .build();
    }

    @Override
    public List<CsvUser> toCsvUser(List<CsvView> csvViewList) {
        if ( csvViewList == null ) {
            return null;
        }

        List<CsvUser> list = new ArrayList<>(csvViewList.size());
        for ( CsvView csvView : csvViewList ) {
            list.add(toCsvUser(csvView) );
        }

        return list;
    }
}
