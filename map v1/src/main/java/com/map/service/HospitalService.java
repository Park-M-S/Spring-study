package com.map.service;

import com.map.dao.HospitalDao;
import com.map.model.Hospital;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HospitalService {

    private final HospitalDao hospitalDao;

    public HospitalService(HospitalDao hospitalDao) {
        this.hospitalDao = hospitalDao;
    }

    public List<Hospital> getAllHospitals() {
        return hospitalDao.findAll();
    }

    public List<Hospital> getNearbyHospitals(double lat, double lng, double radiusKm) {
        return getHospitalsByDistance(lat, lng, radiusKm);
    }

    public List<Hospital> getHospitalsByDistance(double lat, double lng, double radiusKm) {
        List<Hospital> all = hospitalDao.findAll();
        return all.stream()
                .map(h -> {
                    double distance = calculateDistance(lat, lng, h.getLatitude(), h.getLongitude());
                    h.setDistance(distance);
                    return h;
                })
                .filter(h -> h.getDistance() <= radiusKm)
                .sorted(Comparator.comparingDouble(Hospital::getDistance))
                .collect(Collectors.toList());
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // 지구 반지름 (단위: km)
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
