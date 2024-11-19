/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mahasiswa.service;

import com.mahasiswa.model.modelMahasiswa;
import com.mahasiswa.repository.mahasiswaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 *
 * @author ibnuk
 */
@Service
public class mahasiswaService {
    @Autowired
    private mahasiswaRepository repository;
    
    public void addMhs(modelMahasiswa mhs){
        repository.save(mhs);
    }
    
    public modelMahasiswa getMhs(int id){
        return repository.findById(id).orElse(null);
    }
    
    public void updateMhs(modelMahasiswa mhs){
        repository.save(mhs);
    }
    
    public void deleteMhs(int id){
        repository.deleteById(id);
    }
    
    public List<modelMahasiswa> getAllMahasiswa(){
        return repository.findAll();
    }
}
