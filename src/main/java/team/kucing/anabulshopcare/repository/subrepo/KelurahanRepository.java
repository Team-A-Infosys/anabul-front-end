package team.kucing.anabulshopcare.repository.subrepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.kucing.anabulshopcare.entity.subaddress.Kelurahan;

import java.util.List;

@Repository
public interface KelurahanRepository extends JpaRepository<Kelurahan, String> {
    List<Kelurahan> findByIdStartingWith(String id);
}