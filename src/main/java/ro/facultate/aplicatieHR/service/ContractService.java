package ro.facultate.aplicatieHR.service;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.facultate.aplicatieHR.entity.dic.DicContract;
import ro.facultate.aplicatieHR.entity.dic.DicContracteIsto;
import ro.facultate.aplicatieHR.entity.dic.DicPerso;
import ro.facultate.aplicatieHR.projection.AngajatHeader;
import ro.facultate.aplicatieHR.projection.Ocurente;
import ro.facultate.aplicatieHR.repository.dic.DicContractIstoRepository;
import ro.facultate.aplicatieHR.repository.dic.DicContractRepository;
import ro.facultate.aplicatieHR.repository.dic.DicPersoRepository;
import ro.facultate.aplicatieHR.utils.HrException;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class ContractService {

    @Autowired
    DicPersoRepository dicPersoRepository;
    @Autowired
    DicContractIstoRepository dicContractIstoRepository;
    @Autowired
    DicContractRepository dicContractRepository;


    public void save(DicPerso dicPerso){
        dicPersoRepository.save(dicPerso);
    }

    public void saveContract(DicContracteIsto dicContracteIsto){
        dicContractIstoRepository.save(dicContracteIsto);
    }

    public void createContract(DicContracteIsto dicContracteIsto){

        dicContracteIsto.setId(null);
        dicContracteIsto.getContract().setId(null);
        dicContracteIsto.setEndDate(null);
        dicContracteIsto.getContract().setEndDate(null);
        if(dicContracteIsto.getContract().getPersoana().getMarca()!=null){
            DicPerso dicPerso = dicPersoRepository.findByMarca(dicContracteIsto.getContract().getPersoana().getMarca());
            dicContracteIsto.getContract().setPersoana(dicPerso);
        }
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

    public List<AngajatHeader> getAngajatiHeaderByStatus(Boolean contractActiv){
        return dicPersoRepository.findAllByContractActiv(contractActiv);
    }

    public HashMap<String, Object> getLastOccurence(Long marca){
        HashMap<String, Object> response = new HashMap<>();
        DicContracteIsto dicContracteIsto = dicContractIstoRepository.findFirstByContract_Persoana_MarcaOrderByDateEffDesc(marca);
        response.put("contractIsto", dicContracteIsto);
        Long nrContract = dicContracteIsto.getContract().getId();
        List<Ocurente> ocurente = dicContractIstoRepository.findByContract_Persoana_Marca(marca);

        response.put("ocurente", ocurente);
        return response;
    }

    public DicContracteIsto getOccurence(Date dateEff, Long marca){
        return dicContractIstoRepository.findByDateEffAndContract_Persoana_Marca(dateEff, marca);
    }

    public void addOcurenta(DicContracteIsto dicContracteIsto) throws HrException{
        //obtinem datele scrise in baza de date
        DicContracteIsto temp = dicContractIstoRepository.findById(dicContracteIsto.getId()).get();
        if(dicContracteIsto.equals(temp)){
            throw new HrException("Nicio data nu a fost modificata!");
        }

        Long idContract =  dicContracteIsto.getContract().getId();
        Long idVechi = dicContracteIsto.getId();
        dicContracteIsto.setId(null);
        DicContracteIsto ocurentaVeche = dicContractIstoRepository.findById(idVechi).get();
        if (ocurentaVeche.getDateEff().compareTo(dicContracteIsto.getDateEff()) >= 0){
            throw new HrException("Data aleasa trebuie sa fie mai mare decat ocurenta existenta");

        }

        DicContract dicContract = dicContractRepository.findById(idContract).get();
        dicContracteIsto.setContract(dicContract);
        dicContractIstoRepository.save(dicContracteIsto);

        Date endDate = DateUtils.addDays(dicContracteIsto.getDateEff(), -1);

        ocurentaVeche.setEndDate(endDate);

        dicContractIstoRepository.save(ocurentaVeche);

    }

}
