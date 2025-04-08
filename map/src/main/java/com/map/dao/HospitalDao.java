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
                "주소 AS address, " +
                "`좌표(X)` AS latitude, " +
                "`좌표(Y)` AS longitude, " +
                "전화번호 AS phone " +
                "FROM hospital_info";

        return jdbcTemplate.query(sql, hospitalRowMapper);
    }

    private final RowMapper<Hospital> hospitalRowMapper = (rs, rowNum) -> {
        Hospital hospital = new Hospital();
        hospital.setAddress(rs.getString("address"));
        hospital.setLatitude(rs.getDouble("latitude"));
        hospital.setLongitude(rs.getDouble("longitude"));
        hospital.setPhone(rs.getString("phone"));
        return hospital;
    };
}
