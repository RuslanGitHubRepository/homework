package com.homework.lusinda.homework23.sbsecurity.dao;

import com.homework.lusinda.homework23.sbsecurity.entity.Information;
import com.homework.lusinda.homework23.sbsecurity.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class InformationDAO {

    @Autowired
    private EntityManager entityManager;

    public List<Information> getAllInformation() {
        String sql = "Select i from " + Information.class.getName() + " i ";

        Query query = this.entityManager.createQuery(sql, Information.class);
        return (List<Information>)query.getResultList();
    }
}