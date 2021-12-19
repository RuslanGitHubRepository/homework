package com.homework.lusinda.homework23.sbsecurity.dao;

import com.homework.lusinda.homework23.sbsecurity.entity.Information;
import com.homework.lusinda.homework23.sbsecurity.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.StringJoiner;

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

    public Long insertInformation(Information info) {
        String sql = "Select max(i.id) from " + Information.class.getName() + " i ";
        Long id = this.entityManager.createQuery(sql, Long.class).getSingleResult();
        StringJoiner joiner = new StringJoiner(",");
        StringJoiner data = joiner.add("'"+info.getName()+"'").add("'"+info.getIsbn()+"'").add("'"+info.getAuthor()+"'").add("'"+info.getPublishingHouse()+"'");
        sql = "INSERT INTO " + Information.class.getSimpleName() + " values (" + ++id + "," + data + ")";

        Query query = this.entityManager.createNativeQuery(sql);
        query.executeUpdate();
        return id;
    }
}