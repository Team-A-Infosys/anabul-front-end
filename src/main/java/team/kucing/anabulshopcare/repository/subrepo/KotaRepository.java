package team.kucing.anabulshopcare.repository.subrepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.kucing.anabulshopcare.entity.subaddress.Kota;

import java.util.List;

@Repository
public interface KotaRepository extends JpaRepository<Kota, String> {
    List<Kota> findByIdStartingWith(String id);
}