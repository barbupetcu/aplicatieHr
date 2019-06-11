package ro.facultate.aplicatieHR.controller;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.facultate.aplicatieHR.dto.HomeTable;
import ro.facultate.aplicatieHR.dto.OcurentaDTO;
import ro.facultate.aplicatieHR.entity.app.AppUser;
import ro.facultate.aplicatieHR.entity.dic.DicContract;
import ro.facultate.aplicatieHR.entity.dic.DicContracteIsto;
import ro.facultate.aplicatieHR.entity.dic.DicPerso;
import ro.facultate.aplicatieHR.projection.AngajatHeader;
import ro.facultate.aplicatieHR.repository.app.AppUserRepository;
import ro.facultate.aplicatieHR.service.ContractService;
import ro.facultate.aplicatieHR.utils.HrException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/contract")
public class ContractController {

    private static final Logger logger = Logger.getLogger(DataController.class);
    @Autowired
    ContractService contractService;
    @Autowired
    AppUserRepository appUserRepository;
    @Autowired
    private Gson gson;

    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @RequestMapping(value = "/adaugaAngajat", method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody DicContracteIsto dicContracteIsto) {
        try{
            contractService.createContract(dicContracteIsto);
            return ResponseEntity.status(HttpStatus.OK).body(gson.toJson("Datele au fost salvate"));
        }
        catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Eroare la salvarea datelor"));
        }
    }

    @RequestMapping(value = "/updatePerson", method = RequestMethod.PUT)
    public ResponseEntity updatePerson(@RequestBody DicPerso dicPerso){
        try{
            contractService.updatePerson(dicPerso);
            return ResponseEntity.status(HttpStatus.OK).body(gson.toJson("Datele au fost salvate cu succes"));
        }
        catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Eroare la salvarea datelor"));
        }
    }

    @RequestMapping(value = "/loadPerson", method = RequestMethod.GET)
    public ResponseEntity updatePerson(@RequestParam(value = "marca", required = false) String marca){
        try{
            DicPerso dicPerso = contractService.getDicPerso(Long.parseLong(marca));
            return ResponseEntity.status(HttpStatus.OK).body(dicPerso);
        }
        catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Eroare la preluarea datelor"));
        }
    }

    @RequestMapping(value = "/loadHeader", method = RequestMethod.GET)
    public ResponseEntity getHeaderList(@RequestParam(name = "contracActiv", required = false) String contractActiv){
        try{
            List<AngajatHeader> header = new ArrayList<>();
            if (StringUtils.isNotEmpty(contractActiv)){
                Boolean ctrActiv = Boolean.parseBoolean(contractActiv);
                header = contractService.getAngajatiHeaderByStatus(ctrActiv);
            }
            else{
                header = contractService.getAngajatiHeader();
            }
            return ResponseEntity.status(HttpStatus.OK).body(header);
        }
        catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Eroare la incarcarea listei de angajati"));
        }
    }

    @RequestMapping(value = "/loadContract", method = RequestMethod.GET)
    public ResponseEntity loadContract(@RequestParam(value = "marca", required = false) String marca){
        try{
            HashMap<String, Object> response= contractService.getLastOccurence(Long.parseLong(marca));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }
        catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Eroare la preluarea datelor"));
        }
    }

    @RequestMapping(value = "/loadOcurenta", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity loadOcurenta(@RequestBody OcurentaDTO ocurentaDTO){
        try{
            DicContracteIsto dicContracteIsto = contractService.getOccurence(ocurentaDTO.getDateEff(), ocurentaDTO.getMarca());
            return ResponseEntity.status(HttpStatus.OK).body(dicContracteIsto);
        }
        catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Eroare la preluarea datelor"));
        }
    }


    @RequestMapping(value = "/addOcurenta", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addOcurenta(@RequestBody DicContracteIsto dicContracteIsto){
        try{
            contractService.addOcurenta(dicContracteIsto);
            return ResponseEntity.status(HttpStatus.OK).body(gson.toJson("Datele au fost salvate cu succes!"));
        }
        catch (HrException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson(e.getMessage()));
        }
        catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Eroare la salvarea datelor"));
        }
    }

    @RequestMapping(value = "/closeContract", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity closeContract(@RequestBody DicContracteIsto dicContracteIsto){
        try{
            contractService.closeContract(dicContracteIsto);
            return ResponseEntity.status(HttpStatus.OK).body(gson.toJson("Datele au fost salvate cu succes!"));
        }
        catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Eroare la salvarea datelor"));
        }
    }

    @RequestMapping(value = "/loadHome", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity loadHome(){

        HashMap<String, Long> counts = new HashMap<>();
        counts.put("rep1", contractService.getAngCount());
        counts.put("rep2", contractService.getNewcontracts());
        counts.put("rep3", contractService.getLeavingAngajati());
        counts.put("rep4", contractService.getApprovingUsers());



        return ResponseEntity.status(HttpStatus.OK).body(counts);

    }

    @RequestMapping(value = "/personalActiv", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getPersonalActiv(){

        List<DicContracteIsto> listaContracte = contractService.getAllAngajati();
        List<HomeTable> result = new ArrayList<>();

        for (DicContracteIsto dic : listaContracte){
            result.add(new HomeTable(dic.getContract().getPersoana().getName()+' '+ dic.getContract().getPersoana().getLastName(),
                    dic.getDept().getNumeDept(),
                    dic.getPost().getName(),
                    dateFormat.format(dic.getContract().getStartDate())
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    @RequestMapping(value = "/contracteNoi", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getContracteNoi(){

        List<DicContracteIsto> listaContracte = contractService.getContracteNoi();
        List<HomeTable> result = new ArrayList<>();

        for (DicContracteIsto dic : listaContracte){
            result.add(new HomeTable(dic.getContract().getPersoana().getName()+' '+ dic.getContract().getPersoana().getLastName(),
                    dic.getPost().getName(),
                    dic.getTipContract().getName(),
                    dateFormat.format(dic.getContract().getStartDate())
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    @RequestMapping(value = "/leavingAngajati", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getLeavingAngajati(){

        List<DicContracteIsto> listaContracte = contractService.getLeavingAng();
        List<HomeTable> result = new ArrayList<>();

        for (DicContracteIsto dic : listaContracte){
            result.add(new HomeTable(dic.getContract().getPersoana().getName()+' '+ dic.getContract().getPersoana().getLastName(),
                    dic.getDept().getNumeDept(),
                    dic.getPost().getName(),
                    dic.getContract().getEndDate() == null? "":dateFormat.format(dic.getContract().getEndDate())
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    @RequestMapping(value = "/newUsers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getNewUsers(){

        List<DicContracteIsto> listaContracte = contractService.getNewUsers();
        List<HomeTable> result = new ArrayList<>();

        for (DicContracteIsto dic : listaContracte){
            AppUser appUser = appUserRepository.findByMarca(dic.getContract().getPersoana().getMarca());
            result.add(new HomeTable(dic.getContract().getPersoana().getName()+' '+ dic.getContract().getPersoana().getLastName(),
                    appUser.getUsername(),
                    dic.getDept().getNumeDept(),
                    appUser.getCreateDateTime() == null? "":dateTimeFormatter.format(appUser.getCreateDateTime())
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);

    }




}
