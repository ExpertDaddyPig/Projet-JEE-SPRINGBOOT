package com.main.service;

import com.main.model.Payslip;
import com.main.repository.PayslipRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PayslipService {

    private final PayslipRepository payslipRepository;

    public PayslipService(PayslipRepository payslipRepository) {
        this.payslipRepository = payslipRepository;
    }

    public List<Payslip> findAll() {
        return payslipRepository.findAll();
    }

    public List<Payslip> findByEmployeId(int employeId) {
        return payslipRepository.findByEmployeId(employeId);
    }

    public Optional<Payslip> findById(int id) {
        return payslipRepository.findById(id);
    }

    public Payslip save(Payslip payslip) {
        return payslipRepository.save(payslip);
    }

    public void deleteById(int id) {
        payslipRepository.deleteById(id);
    }
}