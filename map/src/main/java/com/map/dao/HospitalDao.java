package com.map.dao;

import com.map.model.Hospital;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HospitalDao {

    private final JdbcTemplate jdbcTemplate;

    public HospitalDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Hospital> findAll() {
        String sql = "SELECT " +
                "hospital_name AS name, " +  // 병원 이름 추가
                "hospital_address AS address, " +
                "coordinate_x AS latitude, " +
                "coordinate_y AS longitude, " +
                "hospital_tel AS phone " +
                "FROM hospital_main";

        return jdbcTemplate.query(sql, hospitalRowMapper);
    }

    private final RowMapper<Hospital> hospitalRowMapper = (rs, rowNum) -> {
        Hospital hospital = new Hospital();
        hospital.setName(rs.getString("name"));  // 병원 이름 설정
        hospital.setAddress(rs.getString("address"));
        hospital.setLatitude(rs.getDouble("latitude"));
        hospital.setLongitude(rs.getDouble("longitude"));
        hospital.setPhone(rs.getString("phone"));
        return hospital;
    };
}
