package portfolio.dattq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import portfolio.dattq.model.Contact;
import portfolio.dattq.repository.ContactRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactRepository contactRepository;

    public List<Contact> getAll() {
        return contactRepository.findAll();
    }

    public Optional<Contact> getById(Long id) {
        return contactRepository.findById(id);
    }

    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    public void delete(Long id) {
        contactRepository.deleteById(id);
    }
}
