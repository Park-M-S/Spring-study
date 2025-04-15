package com.hospital.service;

import com.hospital.dao.HospitalDAO;
import com.hospital.model.Hospital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalServiceImpl implements HospitalService {

    private final HospitalDAO hospitalDAO;

    @Autowired
    public HospitalServiceImpl(HospitalDAO hospitalDAO) {
        this.hospitalDAO = hospitalDAO;
    }

    @Override
    public List<Hospital> getHospitals() {
        return hospitalDAO.getAllHospitals(); // DAO에서 데이터 가져오기
    }
}