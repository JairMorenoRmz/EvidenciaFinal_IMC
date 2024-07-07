package com.example.bmicalculator.controller;

import com.example.bmicalculator.entity.BmiRecord;
import com.example.bmicalculator.entity.User;
import com.example.bmicalculator.service.UserService;
import com.example.bmicalculator.service.BmiRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/bmi")
public class BmiController {
    @Autowired
    private UserService userService;

    @Autowired
    private BmiRecordService bmiRecordService;

    @GetMapping("/calculate")
    public String showBmiForm(Model model) {
        model.addAttribute("bmiRecord", new BmiRecord());
        return "calculateBmi";
    }

    @PostMapping("/calculate")
    public String calculateBmi(@RequestParam double weight, Model model) {
        if (weight <= 0) {
            model.addAttribute("error", "Invalid weight");
            return "calculateBmi";
        }

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = userDetails.getUsername();
        User user = userService.findByUsername(username).orElseThrow();

        BmiRecord bmiRecord = bmiRecordService.addBmiRecord(user, weight);
        model.addAttribute("bmiRecord", bmiRecord);

        List<BmiRecord> bmiRecords = bmiRecordService.getBmiRecords(user);
        model.addAttribute("bmiRecords", bmiRecords);

        return "bmiResult";
    }
}
