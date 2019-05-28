package ro.facultate.aplicatieHR.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.facultate.aplicatieHR.entity.dic.DicContracteIsto;
import ro.facultate.aplicatieHR.entity.dic.DicPerso;
import ro.facultate.aplicatieHR.projection.AngajatHeader;
import ro.facultate.aplicatieHR.repository.dic.DicContractIstoRepository;
import ro.facultate.aplicatieHR.repository.dic.DicPersoRepository;

import java.util.List;

@Service
public class ContractService {

    @Autowired
    DicPersoRepository dicPersoRepository;
    @Autowired
    DicContractIstoRepository dicContractIstoRepository;

    public void save(DicPerso dicPerso){
        dicPersoRepository.save(dicPerso);
    }

    public void saveContract(DicContracteIsto dicContracteIsto){
        dicContractIstoRepository.save(dicContracteIsto);
    }

    public void createContract(DicContracteIsto dicContracteIsto){
        dicContracteIsto.getContract().getPersoana().setContractActiv(true);
        dicContracteIsto.setDateEff(dicContracteIsto.getContract().getStartDate());
        dicContractIstoRepository.save(dicContracteIsto);
    }

    public void closeContract(DicContracteIsto dicContracteIsto){
        dicContracteIsto.getContract().getPersoana().setContractActiv(false);
        dicContracteIsto.setEndDate(dicContracteIsto.getContract().getEndDate());
        dicContractIstoRepository.save(dicContracteIsto);
    }

    public void updatePerson(DicPerso dicPerso){
        dicPersoRepository.save(dicPerso);
    }

    public DicPerso getDicPerso(Long marca){
        return dicPersoRepository.findByMarca(marca);
    }

    public List<AngajatHeader> getAngajatiHeader(){
        return dicPersoRepository.findAllBy();
    }
}
