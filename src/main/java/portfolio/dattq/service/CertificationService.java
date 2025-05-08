package portfolio.dattq.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import portfolio.dattq.model.Certification;
import portfolio.dattq.repository.CertificationRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CertificationService {
    private final CertificationRepository certificationRepository;

    public List<Certification> getAll() {
        return certificationRepository.findAll();
    }

    public Optional<Certification> getById(Long id) {
        return certificationRepository.findById(id);
    }

    public Certification save(Certification certification) {
        return certificationRepository.save(certification);
    }

    public void delete(Long id) {
        certificationRepository.deleteById(id);
    }
}
