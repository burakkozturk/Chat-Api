package app.circle.repository;

import app.circle.entity.ReportList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;
import java.util.List;

public interface ReportListRepository extends JpaRepository<ReportList, Long> {

    List<ReportList> findByReportedUserId(UUID reportedUserId);


    @Query("SELECT rl.reportedUserId, COUNT(rl) as reportCount FROM ReportList rl GROUP BY rl.reportedUserId ORDER BY reportCount DESC")
    List<Object[]> findTopReportedUsers();

}
