package ro.facultate.aplicatieHR.controller;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.facultate.aplicatieHR.entity.dic.DicPerso;
import ro.facultate.aplicatieHR.service.ContractService;

@RestController
@RequestMapping(value = "/contract")
public class ContractController {

    private static final Logger logger = Logger.getLogger(DataController.class);
    @Autowired
    ContractService contractService;
    @Autowired
    private Gson gson;

    @RequestMapping(value = "/adaugaAngajat", method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody DicPerso dicPerso) {
        try{
            contractService.save(dicPerso);
            return ResponseEntity.status(HttpStatus.OK).body(gson.toJson("Datele au fost salvate"));
        }
        catch (Exception e){
            logger.error(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Eroare la salvarea datelor"));
        }
    }

}
