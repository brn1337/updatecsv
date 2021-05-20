package pl.silk.updatecsvapplication.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import pl.silk.updatecsvapplication.db.entity.CsvUser;

@Repository
public interface UserRepository extends JpaRepository<CsvUser, Long>, JpaSpecificationExecutor<CsvUser>{

}
