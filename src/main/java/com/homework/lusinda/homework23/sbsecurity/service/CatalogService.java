package com.homework.lusinda.homework23.sbsecurity.service;

import com.homework.lusinda.homework23.sbsecurity.dao.InformationDAO;
import com.homework.lusinda.homework23.sbsecurity.entity.Information;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatalogService {
    @Autowired
    private InformationDAO informationDAO;

    public List<Information> getAllCatalog() {
        return informationDAO.getAllInformation();
    }
}
