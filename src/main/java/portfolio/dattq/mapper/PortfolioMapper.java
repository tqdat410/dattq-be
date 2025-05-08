package portfolio.dattq.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import portfolio.dattq.model.Certification;
import portfolio.dattq.model.Contact;
import portfolio.dattq.model.Project;
import portfolio.dattq.payload.request.CertificationRequest;
import portfolio.dattq.payload.request.ContactRequest;
import portfolio.dattq.payload.request.ProjectRequest;
import portfolio.dattq.payload.response.CertificationResponse;
import portfolio.dattq.payload.response.ContactResponse;
import portfolio.dattq.payload.response.ProjectResponse;

@Mapper(componentModel = "spring")
public interface PortfolioMapper {

    // Certification mappings
    Certification toCertification(CertificationRequest req);
    CertificationResponse toCertificationResponse(Certification cert);

    // Contact mappings
    Contact toContact(ContactRequest req);
    ContactResponse toContactResponse(Contact contact);

    // Project mappings
    @Mapping(target = "projectType", expression = "java(req.getProjectType() != null ? req.getProjectType() : \"Personal\")")
    Project toProject(ProjectRequest req);
    ProjectResponse toProjectResponse(Project project);
}
