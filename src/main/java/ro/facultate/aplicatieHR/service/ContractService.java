package ro.facultate.aplicatieHR.service;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.facultate.aplicatieHR.entity.dic.DicContract;
import ro.facultate.aplicatieHR.entity.dic.DicContracteIsto;
import ro.facultate.aplicatieHR.entity.dic.DicPerso;
import ro.facultate.aplicatieHR.projection.AngajatHeader;
import ro.facultate.aplicatieHR.projection.Ocurente;
import ro.facultate.aplicatieHR.repository.app.AppUserRepository;
import ro.facultate.aplicatieHR.repository.dic.DicContractIstoRepository;
import ro.facultate.aplicatieHR.repository.dic.DicContractRepository;
import ro.facultate.aplicatieHR.repository.dic.DicPersoRepository;
import ro.facultate.aplicatieHR.utils.HrException;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ContractService {

    @Autowired
    DicPersoRepository dicPersoRepository;
    @Autowired
    DicContractIstoRepository dicContractIstoRepository;
    @Autowired
    DicContractRepository dicContractRepository;
    @Autowired
    AppUserRepository appUserRepository;


    public void save(DicPerso dicPerso){
        dicPersoRepository.save(dicPerso);
    }

    public void saveContract(DicContracteIsto dicContracteIsto){
        dicContractIstoRepository.save(dicContracteIsto);
    }

    public DicContracteIsto createContract(DicContracteIsto dicContracteIsto){

        dicContracteIsto.setId(null);
        dicContracteIsto.getContract().setId(null);
        dicContracteIsto.setEndDate(null);
        dicContracteIsto.getContract().setEndDate(null);
        dicContracteIsto.getContract().setPreaviz(false);
        dicContracteIsto.getContract().setPreavizDate(null);
        if(dicContracteIsto.getContract().getPersoana().getMarca()!=null){
            DicPerso dicPerso = dicPersoRepository.findByMarca(dicContracteIsto.getContract().getPersoana().getMarca());
            dicContracteIsto.getContract().setPersoana(dicPerso);

        }
        dicContracteIsto.getContract().getPersoana().setContractActiv(true);
        dicContracteIsto.setDateEff(dicContracteIsto.getContract().getStartDate());
        return dicContractIstoRepository.saveAndFlush(dicContracteIsto);
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
        List<DicContracteIsto> toateOcurentele = dicContractIstoRepository.findByContract_Persoana_MarcaOrderByDateEffDesc(marca);

        DicContracteIsto lastOccurence = toateOcurentele.stream()
                .max(Comparator.comparing(DicContracteIsto::getDateEff)).get();
        response.put("contractIsto", lastOccurence);
        Long nrContract = lastOccurence.getContract().getId();
        List<Ocurente> ocurente = dicContractIstoRepository.findByContract_Persoana_Marca(marca);

        response.put("ocurente", ocurente);
        return response;
    }

    public DicContracteIsto getLastContract(Long marca){
        List<DicContracteIsto> toateOcurentele = dicContractIstoRepository.findByContract_Persoana_MarcaOrderByDateEffDesc(marca);

        DicContracteIsto lastOccurence = toateOcurentele.stream()
                .max(Comparator.comparing(DicContracteIsto::getDateEff)).get();

        return lastOccurence;
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

    public List<DicContracteIsto> getAllAngajati(){
        return dicContractIstoRepository.findAngajatiLastOccurence();
    }
    public List<DicContracteIsto> getAllAngajatiInactive(){
        return dicContractIstoRepository.findAllAngajatiInactive();
    }




    public long getAngCount(){
        return dicPersoRepository.countAllByContractActiv(true);
    }

    public long getNewcontracts(){
        LocalDate ld = LocalDate.now().minusMonths(3);
        Date d = java.sql.Date.valueOf(ld);
        List<DicContracteIsto> angajati = getAllAngajati();
        return angajati.stream().filter(a -> a.getContract().getStartDate().after(d)).count();
    }

    public long getLeavingAngajati(){
        LocalDate ld = LocalDate.now().plusMonths(3);
        Date d = java.sql.Date.valueOf(ld);
        List<DicContracteIsto> angajati = getAllAngajatiInactive();
        return angajati.stream().filter(a -> a.getContract().getPreaviz() == Boolean.TRUE && a.getContract().getPreavizDate() != null && a.getContract().getPreavizDate().before(d)).count();

    }

    public long getApprovingUsers(){
        return appUserRepository.countAllByEnabled(false);
    }

    public List<DicContracteIsto> getContracteNoi(){
        LocalDate ld = LocalDate.now().minusMonths(3);
        Date d = java.sql.Date.valueOf(ld);
        List<DicContracteIsto> angajati = this.getAllAngajati();
        return angajati.stream().filter(a -> a.getContract().getStartDate().after(d)).collect(Collectors.toList());
    }

    public List<DicContracteIsto> getLeavingAng(){
        LocalDate ld = LocalDate.now().plusMonths(3);
        Date d = java.sql.Date.valueOf(ld);
        List<DicContracteIsto> angajati = this.getAllAngajatiInactive();
        return angajati.stream().filter(a -> a.getContract().getPreaviz() == Boolean.TRUE && a.getContract().getPreavizDate() != null && a.getContract().getPreavizDate().before(d)).collect(Collectors.toList());
    }

    public List<DicContracteIsto> getNewUsers(){
        List<DicContracteIsto> angajati = this.getAllAngajati();

        return angajati.stream().filter(a -> appUserRepository.countAllByEnabledFalseAndMarca(a.getContract().getPersoana().getMarca()) > 0).collect(Collectors.toList());

    }

    public DicContracteIsto getContract(Long id){
        return dicContractIstoRepository.findById(id).get();
    }


}
