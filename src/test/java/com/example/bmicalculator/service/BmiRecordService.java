package com.example.bmicalculator.service;

import com.example.bmicalculator.entity.BmiRecord;
import com.example.bmicalculator.entity.User;
import com.example.bmicalculator.repository.BmiRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BmiRecordService {
    @Autowired
    private BmiRecordRepository bmiRecordRepository;

    public BmiRecord addBmiRecord(User user, double weight) {
        double heightInMeters = user.getHeight();
        double bmi = weight / (heightInMeters * heightInMeters);

        BmiRecord bmiRecord = new BmiRecord();
        bmiRecord.setUser(user);
        bmiRecord.setWeight(weight);
        bmiRecord.setBmi(bmi);
        bmiRecord.setDateTime(LocalDateTime.now());

        return bmiRecordRepository.save(bmiRecord);
    }

    public List<BmiRecord> getBmiRecords(User user) {
        return bmiRecordRepository.findByUser(user);
    }
}
