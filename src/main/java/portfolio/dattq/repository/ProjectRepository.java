package portfolio.dattq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.dattq.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
