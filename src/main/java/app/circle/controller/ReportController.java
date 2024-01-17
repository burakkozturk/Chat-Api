package app.circle.controller;

import app.circle.entity.ReportList;
import app.circle.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/report")
public class ReportController {


    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }


    @GetMapping()
    public ResponseEntity<List<ReportList>> getAllReport(){
        List<ReportList> list= reportService.getAllReport();
        return ResponseEntity.ok(list);
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReportList>> getReportByUser(@PathVariable UUID userId) {
        List<ReportList> reports = reportService.getReportByUser(userId);
        return ResponseEntity.ok(reports);
    }


    @GetMapping("/top-reported")
    public ResponseEntity<List<Object[]>> getTopReportedUsers() {
        List<Object[]> topReportedUsers = reportService.getTopReportedUsers(5);
        return ResponseEntity.ok(topReportedUsers);
    }


    @PostMapping("/{whoIsReported}/{userId}")
    public ResponseEntity<?> reportUser(@PathVariable UUID whoIsReported,@PathVariable UUID userId, @RequestParam Long reportId) {
        try {
            reportService.reportUser(whoIsReported,userId, reportId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




}
