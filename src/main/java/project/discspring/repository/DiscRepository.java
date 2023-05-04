package project.discspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.discspring.model.Disc;

@Repository("discrepository")
public interface DiscRepository extends JpaRepository<Disc, Long> {
}
