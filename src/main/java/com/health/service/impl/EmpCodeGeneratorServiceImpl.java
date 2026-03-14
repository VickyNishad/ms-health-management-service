package com.health.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;




@Service
@Transactional
public class EmpCodeGeneratorServiceImpl {


    private static final Map<String, String> PREFIX_MAP = Map.of(
            "DOCTOR", "DOC",
            "NURSE", "NUR",
            "ASSISTANT", "AST",
            "RECEPTIONIST", "REC",
            "ADMIN", "ADM"
    );

//    public String generateEmpCode(String roleCode) {
//
////        EmpCodeSequence sequence = sequenceRepo.findByRoleCode(roleCode)
////                .orElseGet(() -> {
////                    EmpCodeSequence s = new EmpCodeSequence();
////                    s.setRoleCode(roleCode);
////                    s.setLastNumber(0L);
////                    return s;
////                });
////
////        long nextNumber = sequence.getLastNumber() + 1;
////        sequence.setLastNumber(nextNumber);
////        sequenceRepo.save(sequence);
//
//        String prefix = PREFIX_MAP.get(roleCode);
//
////        return prefix + String.format("%03d", nextNumber);
//    }
}
