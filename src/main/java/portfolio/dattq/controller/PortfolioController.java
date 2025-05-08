package portfolio.dattq.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import portfolio.dattq.mapper.PortfolioMapper;
import portfolio.dattq.model.Certification;
import portfolio.dattq.model.Contact;
import portfolio.dattq.model.Project;
import portfolio.dattq.payload.request.CertificationRequest;
import portfolio.dattq.payload.request.ContactRequest;
import portfolio.dattq.payload.request.ProjectRequest;
import portfolio.dattq.payload.request.CvurlRequest;
import portfolio.dattq.payload.response.CertificationResponse;
import portfolio.dattq.payload.response.ContactResponse;
import portfolio.dattq.payload.response.ProjectResponse;
import portfolio.dattq.payload.response.CvurlResponse;
import portfolio.dattq.service.CertificationService;
import portfolio.dattq.service.ContactService;
import portfolio.dattq.service.ProjectService;
import portfolio.dattq.service.ProfileService;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PortfolioController {
    private final CertificationService certificationService;
    private final ContactService contactService;
    private final ProjectService projectService;
    private final ProfileService profileService;
    private final PortfolioMapper portfolioMapper;

    // Certifications
    @GetMapping("/certifications")
    public List<CertificationResponse> getCertifications() {
        return certificationService.getAll().stream().map(portfolioMapper::toCertificationResponse).collect(Collectors.toList());
    }

    @PostMapping("/certifications")
    @PreAuthorize("hasRole('ADMIN')")
    public CertificationResponse createCertification(@RequestBody CertificationRequest request) {
        Certification cert = portfolioMapper.toCertification(request);
        return portfolioMapper.toCertificationResponse(certificationService.save(cert));
    }

    @PutMapping("/certifications/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CertificationResponse> updateCertification(@PathVariable Long id, @RequestBody CertificationRequest request) {
        return certificationService.getById(id)
                .map(existing -> {
                    Certification updated = portfolioMapper.toCertification(request);
                    updated.setId(id);
                    return ResponseEntity.ok(portfolioMapper.toCertificationResponse(certificationService.save(updated)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/certifications/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCertification(@PathVariable Long id) {
        if (certificationService.getById(id).isPresent()) {
            certificationService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Contacts
    @GetMapping("/contacts")
    public List<ContactResponse> getContacts() {
        return contactService.getAll().stream().map(portfolioMapper::toContactResponse).collect(Collectors.toList());
    }

    @PostMapping("/contacts")
    @PreAuthorize("hasRole('ADMIN')")
    public ContactResponse createContact(@RequestBody ContactRequest request) {
        Contact contact = portfolioMapper.toContact(request);
        return portfolioMapper.toContactResponse(contactService.save(contact));
    }

    @PutMapping("/contacts/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ContactResponse> updateContact(@PathVariable Long id, @RequestBody ContactRequest request) {
        return contactService.getById(id)
                .map(existing -> {
                    Contact updated = portfolioMapper.toContact(request);
                    updated.setId(id);
                    return ResponseEntity.ok(portfolioMapper.toContactResponse(contactService.save(updated)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/contacts/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        if (contactService.getById(id).isPresent()) {
            contactService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Projects
    @GetMapping("/projects")
    public List<ProjectResponse> getProjects() {
        return projectService.getAll().stream().map(portfolioMapper::toProjectResponse).collect(Collectors.toList());
    }

    @PostMapping("/projects")
    @PreAuthorize("hasRole('ADMIN')")
    public ProjectResponse createProject(@RequestBody ProjectRequest request) {
        Project project = portfolioMapper.toProject(request);
        return portfolioMapper.toProjectResponse(projectService.save(project));
    }

    @PutMapping("/projects/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProjectResponse> updateProject(@PathVariable Long id, @RequestBody ProjectRequest request) {
        return projectService.getById(id)
                .map(existing -> {
                    Project updated = portfolioMapper.toProject(request);
                    updated.setId(id);
                    return ResponseEntity.ok(portfolioMapper.toProjectResponse(projectService.save(updated)));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/projects/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        if (projectService.getById(id).isPresent()) {
            projectService.delete(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cvurl")
    public ResponseEntity<CvurlResponse> getCvurl() {
        CvurlResponse response = profileService.getCvurlOfUser1();
        if (response.getCvurl() != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/cvurl")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CvurlResponse> updateCvurl(@RequestBody CvurlRequest request) {
        CvurlResponse response = profileService.updateCvurlOfUser1(request.getCvurl());
        if (response.getCvurl() != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
