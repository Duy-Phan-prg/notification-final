package com.example.demo.controller;

import com.example.demo.service.EmailLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/notification-service/dashboard")
public class DashboardController {
    //Done
    @Autowired
    private EmailLogService emailLogService;

    @GetMapping
    public String dashboard(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        
        model.addAttribute("logs", emailLogService.getAllLogs(pageable));
        model.addAttribute("statistics", emailLogService.getStatistics());
        model.addAttribute("currentPage", page);
        
        return "dashboard";
    }
}
