package ro.facultate.aplicatieHR.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.facultate.aplicatieHR.entity.dic.DicPerso;
import ro.facultate.aplicatieHR.repository.dic.DicPersoRepository;

@Service
public class ContractService {

    @Autowired
    DicPersoRepository dicPersoRepository;

    public void save(DicPerso dicPerso){
        dicPersoRepository.save(dicPerso);
    }
}
