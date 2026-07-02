package com.harish.eimsspringbootbackend.service;

import com.harish.eimsspringbootbackend.dto.SupplierRequestDTO;
import com.harish.eimsspringbootbackend.entity.Supplier;
import com.harish.eimsspringbootbackend.repository.SupplierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public Supplier getSupplier(Long supplierId) {
        return supplierRepository.findById(supplierId).orElseThrow(() -> new RuntimeException("Supplier not found with id: " + supplierId));
    }

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier registerSupplier(SupplierRequestDTO dto) {
        if (supplierRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Supplier with this email already exists");
        }
        if (supplierRepository.existsByPhone(dto.getPhone())) {
            throw new RuntimeException("Supplier with this phone number already exists");
        }
        Supplier supplier = new Supplier();
        supplier.setName(dto.getName());
        supplier.setEmail(dto.getEmail());
        supplier.setPhone(dto.getPhone());
        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(Long supplierId, SupplierRequestDTO dto) {
        Supplier existing = getSupplier(supplierId);
        existing.setEmail(dto.getEmail());
        existing.setPhone(dto.getPhone());
        return supplierRepository.save(existing);
    }

//    public void deleteSupplier(Long supplierId) {
//        supplierRepository.deleteById(supplierId);
//    }

    // should not delete Supplier, what if we need details of deletedSupplier ??
}
