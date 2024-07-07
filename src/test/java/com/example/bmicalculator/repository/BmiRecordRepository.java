package com.example.bmicalculator.repository;

import com.example.bmicalculator.entity.BmiRecord;
import com.example.bmicalculator.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BmiRecordRepository extends JpaRepository<BmiRecord, Long> {
    List<BmiRecord> findByUser(User user);
}
