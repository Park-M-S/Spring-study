package com.map.controller;

import com.map.model.Hospital;
import com.map.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api/hospitals")  // 이 경로로 요청 받음
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    // 전체 병원 목록 반환
    @GetMapping
    public List<Hospital> getAllHospitals() {
        return hospitalService.getAllHospitals();
    }

    // 주변 병원 목록 반환
    @GetMapping("/nearby")
    public List<Hospital> getNearbyHospitals(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(defaultValue = "5") double radiusKm
    ) {
        return hospitalService.getNearbyHospitals(lat, lng, radiusKm);
    }
}


