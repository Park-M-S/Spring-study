package com.map.controller;

import com.map.model.Hospital;
import com.map.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping
    public List<Hospital> getAllHospitals() {
        return hospitalService.getAllHospitals();
    }
    
    @GetMapping("/nearby")
    public List<Hospital> getNearbyHospitals(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(defaultValue = "5") double radiusKm // 기본 반경 5km
    ) {
        return hospitalService.getNearbyHospitals(lat, lng, radiusKm);
    }
}
