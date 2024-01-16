package app.circle.repository;

import app.circle.entity.BanList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BanListRepository extends JpaRepository<BanList, Long> {

}
