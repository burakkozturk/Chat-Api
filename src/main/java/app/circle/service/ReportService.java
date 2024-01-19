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
    private final BlockListService blockListService;
    private final AdministrationService administrationService;

    public ReportService(UserRepository userRepository, ReportListRepository reportListRepository, ReportRepository reportRepository, BlockListService blockListService, AdministrationService administrationService) {
        this.userRepository = userRepository;
        this.reportListRepository = reportListRepository;
        this.reportRepository = reportRepository;
        this.blockListService = blockListService;
        this.administrationService = administrationService;
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


    public void reportUser(UUID whoIsReported, UUID reportedUserId, Long reportId) {

        User reportedUser = userRepository.findById(reportedUserId).orElseThrow(() -> new RuntimeException("User not found"));
        Report report = reportRepository.findById(reportId).orElseThrow(() -> new RuntimeException("Report type not found"));

        // Mevcut reportScore'u al
        int currentReportScore = reportedUser.getReportScore();

        // Raporun skorunu kullanıcının mevcut rapor skoruna ekle
        reportedUser.setReportScore(currentReportScore + report.getScore());

        // Güncellenmiş kullanıcıyı veritabanında güncelle
        userRepository.save(reportedUser);

        // Güncellenmiş reportScore değerini konsola yazdır
        System.out.println("Updated reportScore for user " + reportedUserId + " is: " + reportedUser.getReportScore());

        if (reportedUser.getReportScore() >= 10) {
            administrationService.banUser(reportedUserId);
            System.out.println("User " + reportedUserId + " has been banned due to high report score.");
        }

        ReportList reportList = new ReportList();
        reportList.setReportedUserId(reportedUserId);
        reportList.setWhoIsReported(whoIsReported);
        reportList.setReport(report);

        reportListRepository.save(reportList);

        // Block the user if necessary
        try {
            blockListService.blockUser(whoIsReported, reportedUserId);
        } catch (Exception e) {
            // Exception handling, log if necessary
            System.out.println("Error during blocking process: " + e.getMessage());
        }
    }

}