package ro.facultate.aplicatieHR.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.facultate.aplicatieHR.entity.data.Dept;
import ro.facultate.aplicatieHR.entity.data.Posturi;
import ro.facultate.aplicatieHR.entity.data.TipuriContracte;
import ro.facultate.aplicatieHR.repository.data.DeptRepository;
import ro.facultate.aplicatieHR.repository.data.PosturiRepository;
import ro.facultate.aplicatieHR.repository.data.TipuriContracteRepository;

import java.util.List;

@Service
public class DataService {

    @Autowired
    DeptRepository deptRepository;
    @Autowired
    PosturiRepository posturiRepository;
    @Autowired
    TipuriContracteRepository tipuriContracteRepository;

    public List<Dept> getDeptAll(){
        return deptRepository.findAll();
    }

    public Dept getDept(Integer id){
        return deptRepository.findById(id).get();
    }

    public List<Posturi> getPosturiAll(){
        return posturiRepository.findAll();
    }

    public Posturi getPost(Integer id){
        return  posturiRepository.findById(id).get();
    }

    public List<Posturi> getPosturiDept(Integer deptId){
        return posturiRepository.findAllByDeptId(deptId);
    }

    public List<TipuriContracte> getTipuriCntAll(){
        return tipuriContracteRepository.findAll();
    }

    public TipuriContracte getTipCnt(String code){
        return   tipuriContracteRepository.getOne(code);
    }
}
