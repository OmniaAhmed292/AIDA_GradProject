package com.example.aida.Repositories;

import com.example.aida.Entities.Vendor;
import jakarta.transaction.Transactional;

@Transactional
public interface VendorRepository extends UserRepository<Vendor> {
    // Additional methods specific to Vendor (if needed)
}