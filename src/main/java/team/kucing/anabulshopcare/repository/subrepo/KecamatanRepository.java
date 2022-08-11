package team.kucing.anabulshopcare.repository.subrepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.kucing.anabulshopcare.entity.subaddress.Kecamatan;

import java.util.List;

@Repository
public interface KecamatanRepository extends JpaRepository<Kecamatan, String> {
    List<Kecamatan> findByIdStartingWith(String id);
}