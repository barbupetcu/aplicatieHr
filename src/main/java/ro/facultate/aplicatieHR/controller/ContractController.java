package ro.facultate.aplicatieHR.controller;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.facultate.aplicatieHR.entity.dic.DicContracteIsto;
import ro.facultate.aplicatieHR.entity.dic.DicPerso;
import ro.facultate.aplicatieHR.projection.AngajatHeader;
import ro.facultate.aplicatieHR.service.ContractService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "/contract")
public class ContractController {

    private static final Logger logger = Logger.getLogger(DataController.class);
    @Autowired
    ContractService contractService;
    @Autowired
    private Gson gson;

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
    public ResponseEntity getHeaderList(){
        try{
            List<AngajatHeader> header = contractService.getAngajatiHeader();
            return ResponseEntity.status(HttpStatus.OK).body(header);
        }
        catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Eroare la incarcarea listei de angajati"));
        }
    }

}
