package com.bezkoder.springjwt.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bezkoder.springjwt.models.DepartmentCount;
import com.bezkoder.springjwt.repository.ProcessRepository;

@Service
public class CountApprovedService {
    @Autowired
    private ProcessRepository processRepository;

    public List<DepartmentCount> countApprovedByDepartment() {
        List<Object[]> results = processRepository.countApprovedByProcessDpt();
        return results.stream()
                .map(result -> new DepartmentCount((String) result[0], (Long) result[1]))
                .collect(Collectors.toList());
    }

    public List<DepartmentCount> countForUpdateByDepartment() {
        List<Object[]> results = processRepository.countUpdateByProcessDpt();
        return results.stream()
                .map(result -> new DepartmentCount((String) result[0], (Long) result[1]))
                .collect(Collectors.toList());
    }


    public List<DepartmentCount> countReviewByDepartment() {
        List<Object[]> results = processRepository.countReviewByProcessDpt();
        return results.stream()
                .map(result -> new DepartmentCount((String) result[0], (Long) result[1]))
                .collect(Collectors.toList());
    }
  


    public List<DepartmentCount> countStrategyByProcessDpt() {
        List<Object[]> results = processRepository.countStrategyByProcessDpt();
        return results.stream()
                .map(result -> new DepartmentCount((String) result[0], (Long) result[1]))
                .collect(Collectors.toList());
    }

    public List<DepartmentCount> countGmByProcessDpt() {
        List<Object[]> results = processRepository.countGmByProcessDpt();
        return results.stream()
                .map(result -> new DepartmentCount((String) result[0], (Long) result[1]))
                .collect(Collectors.toList());
    }

    public List<DepartmentCount> countUpdateApprovedByDepartment() {
        List<Object[]> results = processRepository.countUpdateApproveByProcessDpt();
        return results.stream()
                .map(result -> new DepartmentCount((String) result[0], (Long) result[1]))
                .collect(Collectors.toList());
    }
}
