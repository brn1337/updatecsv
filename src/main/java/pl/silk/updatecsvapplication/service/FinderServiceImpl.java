package pl.silk.updatecsvapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pl.silk.updatecsvapplication.controller.model.CsvCriteria;
import pl.silk.updatecsvapplication.controller.model.CsvView;
import pl.silk.updatecsvapplication.db.entity.CsvUser;
import pl.silk.updatecsvapplication.db.entity.CsvUser_;
import pl.silk.updatecsvapplication.db.repository.UserRepository;
import pl.silk.updatecsvapplication.service.mapper.CsvUserFinderCriteriaMapper;
import pl.silk.updatecsvapplication.service.mapper.CsvViewMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FinderServiceImpl implements FinderService {

    private final UserRepository userRepository;
    private final CsvUserFinderCriteriaMapper csvUserCriteriaMapper;
    private final CsvViewMapper csvViewMapper;

    @Override
    public List<CsvView> searchByCriteria(CsvCriteria csvCriteria) {
        Specification<CsvUser> csvUserSpecification = Specification.where(
                surnameEquals(csvCriteria.getLastname())
                .and(firstnameEquals(csvCriteria.getFirstName())
                .and(ageEquals(csvCriteria.getAge())
                .and(phoneNumberEquals(csvCriteria.getPhoneNumber())))));
        return findBySpec(csvUserSpecification);
    }

    @Override
    public List<CsvView> deleteByList(List<CsvView> csvViewList) {
        userRepository.deleteAll(csvViewMapper.toCsvUser(csvViewList));
        return csvViewList;
    }

    @Override
    public Long countAllUsers() {
        List<CsvUser> csvUser = userRepository.findAll();
        return (long) csvUser.size();
    }

    private List<CsvView> findBySpec(Specification<CsvUser> specification) {
        return userRepository.findAll(specification).stream()
                .map(csvUserCriteriaMapper::toCsvUserFinderCriteria)
                .collect(Collectors.toList());
    }

    private static Specification<CsvUser> surnameEquals(String lastname) {
        return (root, query, cb) -> cb.equal(root.get(CsvUser_.LAST_NAME), lastname);
    }

    private static Specification<CsvUser> firstnameEquals(String firstName) {
        return (root, query, cb) -> cb.equal(root.get(CsvUser_.FIRST_NAME), firstName);
    }

    private static Specification<CsvUser> ageEquals(Long ageEquals) {
        return (root, query, cb) -> cb.equal(root.get(CsvUser_.AGE), ageEquals);
    }

    private static Specification<CsvUser> phoneNumberEquals(String phoneNumber) {
        return (root, query, cb) -> cb.equal(root.get(CsvUser_.PHONE_NUMBER), phoneNumber);
    }
}
