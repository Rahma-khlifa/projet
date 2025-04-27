package com.example.demo.services;

import com.example.demo.entities.Admin;
import com.example.demo.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AdminServiceImpl implements IAdminService {

    private final AdminRepository adminRepository;
    private static final int ADMIN_ID = 1; // ID fixe pour l'unique admin

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }


}