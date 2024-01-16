package app.circle.service;

import app.circle.entity.Report;
import app.circle.entity.ReportList;
import app.circle.entity.User;
import app.circle.repository.ReportListRepository;
import app.circle.repository.ReportRepository;
import app.circle.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ReportService {



    private final UserRepository userRepository;

    private final ReportListRepository reportListRepository;
    private final ReportRepository reportRepository;

    public ReportService(UserRepository userRepository, ReportListRepository reportListRepository, ReportRepository reportRepository) {
        this.userRepository = userRepository;
        this.reportListRepository = reportListRepository;
        this.reportRepository = reportRepository;
    }


    public List<ReportList> getAllReport(){
        return reportListRepository.findAll();
    }


    public List<ReportList> getReportByUser(UUID userId) {
        return reportListRepository.findByReportedUserId(userId);
    }


    public List<Object[]> getTopReportedUsers(int limit) {

        return reportListRepository.findTopReportedUsers().stream().limit(limit).collect(Collectors.toList());
    }


    public void reportUser(UUID reportedUserId, Long reportId) {

        User reportedUser = userRepository.findById(reportedUserId).orElseThrow(() -> new RuntimeException("User not found"));
        Report report = reportRepository.findById(reportId).orElseThrow(() -> new RuntimeException("Report type not found"));

        ReportList reportList = new ReportList();
        reportList.setReportedUserId(reportedUserId);
        reportList.setReport(report);
        reportListRepository.save(reportList);
    }
}