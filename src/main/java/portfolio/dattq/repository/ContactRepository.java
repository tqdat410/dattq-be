package portfolio.dattq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.dattq.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
