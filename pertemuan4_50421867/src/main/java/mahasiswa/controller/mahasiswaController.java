/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mahasiswa.controller;

import mahasiswa.model.hibernateUtil;
import mahasiswa.model.modelMahasiswa;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
/**
 *
 * @author ibnuk
 */
public class mahasiswaController {
    public void addMhs(modelMahasiswa mhs){
        Transaction trx = null;
        try (Session session = hibernateUtil.getSessionFactory().openSession()){
            trx = session.beginTransaction();
            session.save(mhs);
            trx.commit();
        }catch (Exception e){
            if (trx != null){
                trx.rollback();
            }
            e.printStackTrace();
        }
    }

    public void updateMhs(modelMahasiswa mhs) {
        Transaction trx = null;

        try (Session session = hibernateUtil.getSessionFactory().openSession()){
            trx = session.beginTransaction();
            session.update(mhs);
            trx.commit();
        } catch (Exception e){
            if (trx != null){
                trx.rollback();
            }
            e.printStackTrace();
        }

    }

    public void deleteMhs(int id) {
        Transaction trx = null;

        try (Session session = hibernateUtil.getSessionFactory().openSession()){
            trx = session.beginTransaction();
            modelMahasiswa mhs = session.get(modelMahasiswa.class, id);
            if(mhs != null){
                session.delete(mhs);
                System.out.println("Berhasil hapus");
            }
            trx.commit();
        } catch (Exception e){
            if (trx != null){
                trx.rollback();
            }
            e.printStackTrace();
        }

    }
    
    public List<modelMahasiswa> getAllMahasiswa() {
        Transaction trx = null;
        List<modelMahasiswa> listMhs = null;

        try (Session session = hibernateUtil.getSessionFactory().openSession()){
            trx = session.beginTransaction();
            // Using HQL (Hibernate Query Language) to fetch all records
            Query<modelMahasiswa> query = session.createQuery("from modelMahasiswa", modelMahasiswa.class);
            listMhs = query.list(); // Fetch all results

            trx.commit(); // Commit transaction
        } catch (Exception e) {
            if (trx != null) {
                trx.rollback(); // Rollback transaction in case of error
            }
            e.printStackTrace();
        }

        return listMhs;
    }
    
    public modelMahasiswa getMhs(int id) {
        throw new UnsupportedOperationException("Not supported yet.");

    }
}