package portfolio.dattq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.dattq.model.Certification;

public interface CertificationRepository extends JpaRepository<Certification, Long> {
}
