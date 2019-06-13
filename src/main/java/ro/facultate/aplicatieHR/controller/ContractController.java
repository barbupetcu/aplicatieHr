package ro.facultate.aplicatieHR.controller;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.facultate.aplicatieHR.BO.*;
import ro.facultate.aplicatieHR.dto.AdeverintaDTO;
import ro.facultate.aplicatieHR.dto.ApproveUserDTO;
import ro.facultate.aplicatieHR.dto.HomeTable;
import ro.facultate.aplicatieHR.dto.OcurentaDTO;
import ro.facultate.aplicatieHR.entity.app.AppUser;
import ro.facultate.aplicatieHR.entity.dic.DicContract;
import ro.facultate.aplicatieHR.entity.dic.DicContracteIsto;
import ro.facultate.aplicatieHR.entity.dic.DicPerso;
import ro.facultate.aplicatieHR.projection.AngajatHeader;
import ro.facultate.aplicatieHR.repository.app.AppUserRepository;
import ro.facultate.aplicatieHR.service.ContractService;
import ro.facultate.aplicatieHR.service.DataService;
import ro.facultate.aplicatieHR.utils.ExcelGenerator;
import ro.facultate.aplicatieHR.utils.HrException;
import ro.facultate.aplicatieHR.utils.WordGenerator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
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
    DataService dataService;
    @Autowired
    private Gson gson;
    @Autowired
    private WordGenerator wordGenerator;

    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @RequestMapping(value = "/adaugaAngajat", method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody DicContracteIsto dto) {
        try{
            dto = contractService.createContract(dto);

            DicContracteIsto dic = contractService.getContract(dto.getId());

            ContractDoc contractDoc = new ContractDoc(
                    dic.getContract().getId().toString(),
                    dateFormat.format(new Date()),
                    dic.getContract().getPersoana().getName() + " "+ dic.getContract().getPersoana().getLastName(),
                    dataService.getOras(dic.getContract().getPersoana().getAddress().getCountyId(), dic.getContract().getPersoana().getAddress().getCityId()),
                    dic.getContract().getPersoana().getAddress().getStreet(),
                    dic.getContract().getPersoana().getAddress().getStreetNo(),
                    dataService.getJudet(dic.getContract().getPersoana().getAddress().getCountyId()),
                    dic.getTipContract().getName(),
                    dateFormat.format(dic.getContract().getStartDate()),
                    dic.getContract().getEndDate()!=null? dateFormat.format(dic.getContract().getEndDate()):"",
                    dic.getPost().getName(),
                    dic.getContract().getPersoana().getCnp().toString()
            );
            ByteArrayInputStream bt =wordGenerator.generateDocument(contractDoc, "contract");

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition",
                    "attachment; filename=Contract_"+
                            dic.getContract().getPersoana().getName()+"_"+ dic.getContract().getPersoana().getLastName() +".docx");
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .body(new InputStreamResource(bt));

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
                    dateFormat.format(dic.getContract().getStartDate()),
                    dic.getContract().getPersoana().getMarca()
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
                    dateFormat.format(dic.getContract().getStartDate()),
                    dic.getContract().getPersoana().getMarca()
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
                    dic.getContract().getEndDate() == null? "":dateFormat.format(dic.getContract().getEndDate()),
                    dic.getContract().getPersoana().getMarca()
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
                    appUser.getCreateDateTime() == null? "":dateTimeFormatter.format(appUser.getCreateDateTime()),
                    dic.getContract().getPersoana().getMarca()
            ));
        }

        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    @RequestMapping(value = "/loadApproveUser", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity loadUsers(@RequestParam(name = "username") String marca){

        List<DicContracteIsto> listaContracte = contractService.getNewUsers();

        DicContracteIsto contract = listaContracte.stream().filter(a -> a.getContract().getPersoana().getMarca() == Long.parseLong(marca)).findFirst().orElse(null);

        ApproveUserDTO approveUserDTO = new ApproveUserDTO(
                contract.getContract().getPersoana().getName(),
                contract.getContract().getPersoana().getLastName(),
                appUserRepository.findByMarca(Long.parseLong(marca)).getUsername(),
                contract.getContract().getPersoana().getEmail(),
                contract.getDept().getNumeDept(),
                contract.getPost().getName(),
                contract.getContract().getPersoana().getMarca()
        );

        return ResponseEntity.status(HttpStatus.OK).body(approveUserDTO);
    }

    @RequestMapping(value = "/exportRaport/{raport}", method = RequestMethod.GET)
    public ResponseEntity getTaskReport(@PathVariable(name = "raport") String raport) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=Lista_Angajati.xlsx");
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(this.getReport(raport)));
    }

    @RequestMapping(value = "/eliberareAdeverinta", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAdeverinta(@RequestBody AdeverintaDTO adeverintaDTO) throws IOException {

        DicContracteIsto dic = contractService.getLastContract(Long.parseLong(adeverintaDTO.getMarca()));

        AdeverintaDoc adeverintaDoc = new AdeverintaDoc(
                dic.getContract().getPersoana().getName() + ' '+ dic.getContract().getPersoana().getLastName(),
                dic.getContract().getPersoana().getCnp().toString(),
                dataService.getOras(dic.getContract().getPersoana().getAddress().getCountyId(), dic.getContract().getPersoana().getAddress().getCityId()),
                dic.getContract().getPersoana().getAddress().getStreet(),
                dic.getContract().getPersoana().getAddress().getStreetNo(),
                dataService.getJudet(dic.getContract().getPersoana().getAddress().getCountyId()),
                dic.getPost().getName(),
                dic.getContract().getStartDate() == null? "":dateFormat.format(dic.getContract().getStartDate()),
                adeverintaDTO.getMotiv(),
                dic.getSalariu().toString()

        );

        ByteArrayInputStream output =  wordGenerator.generateDocument(adeverintaDoc, adeverintaDTO.getTip());



        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition",
                "attachment; filename=Adeverinta_"+dic.getContract().getPersoana().getName()+"_"+
                        dic.getContract().getPersoana().getLastName()+".docx");
        return ResponseEntity
                .ok()
                .headers(headers)
                .body(new InputStreamResource(output));
    }



    @RequestMapping(value = "/approveUser", method = RequestMethod.POST)
    public ResponseEntity approveUser(@RequestBody ApproveUserDTO approveUserDTO) {

        AppUser appUser= appUserRepository.findByMarca(approveUserDTO.getMarca());
        appUser.setEnabled(true);
        appUserRepository.save(appUser);

        return ResponseEntity
                .ok()
                .body(null);
    }

    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public ResponseEntity deleteUser(@RequestBody ApproveUserDTO approveUserDTO) {

        AppUser appUser= appUserRepository.findByMarca(approveUserDTO.getMarca());
        appUserRepository.delete(appUser);

        return ResponseEntity
                .ok()
                .body(null);
    }

    private ByteArrayInputStream getReport(String reportType) throws IOException {

        ByteArrayInputStream output;
        List<DicContracteIsto> listaContracte;

        switch (reportType){
            case "1":
                listaContracte = contractService.getAllAngajati();
                List<Raport1> raport1 = new ArrayList<>();
                for (DicContracteIsto dic: listaContracte){
                    raport1.add(new Raport1(
                            dic.getContract().getPersoana().getMarca(),
                            dic.getContract().getPersoana().getName(),
                            dic.getContract().getPersoana().getLastName(),
                            dic.getDept().getNumeDept(),
                            dic.getPost().getName(),
                            dic.getSalariu(),
                            dic.getTipContract().getName(),
                            dic.getContract().getStartDate(),
                            dic.getDateEff())
                    );
                }
                output = ExcelGenerator.createExcel(raport1, false);
                break;
            case "2":
                listaContracte = contractService.getContracteNoi();
                List<Raport2> raport2 = new ArrayList<>();

                for (DicContracteIsto dic: listaContracte){
                    LocalDate now = LocalDate.now();
                    LocalDate startContract = dic.getContract().getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    Period period = Period.between(startContract, now);
                    String diferenta="";
                    if (period.getDays()>0){

                        if(period.getMonths()>2){
                            diferenta = period.getMonths() + " luni";
                            period.minusMonths(period.getMonths());
                            if (period.getDays()>2){
                                diferenta+=", " +period.getDays()+" zile";
                            }
                            else if (period.getDays()==1){
                                diferenta+=", 1 zi";
                            }
                        }
                        else if(period.getMonths()==1){
                            diferenta = "1 luna";
                            period.minusMonths(period.getMonths());
                            if (period.getDays()>2){
                                diferenta+=", " +period.getDays()+" zile";
                            }
                            else if (period.getDays()==1){
                                diferenta+=", 1 zi";
                            }
                        }
                        else {
                            if(period.getDays()>1){
                                diferenta = period.getDays()+ " zile";
                            }
                            else{
                                diferenta = "1 zi";
                            }

                        }
                    }


                    raport2.add(new Raport2(
                            dic.getContract().getPersoana().getMarca(),
                            dic.getContract().getPersoana().getName(),
                            dic.getContract().getPersoana().getLastName(),
                            dic.getDept().getNumeDept(),
                            dic.getPost().getName(),
                            dic.getSalariu(),
                            dic.getTipContract().getName(),
                            dic.getContract().getStartDate(),
                            dic.getPerioadaProbaData(),
                            diferenta
                            )
                    );
                }
                output = ExcelGenerator.createExcel(raport2, false);;
                break;
            case "3":
                listaContracte = contractService.getLeavingAng();

                List<Raport3> raport3 = new ArrayList<>();


                for (DicContracteIsto dic: listaContracte){
                    LocalDate endDate = dic.getContract().getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    LocalDate startContract = dic.getContract().getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    Period period = Period.between(startContract, endDate);
                    String diferenta="";

                    if (period.getMonths()>0){
                        if (period.getMonths() ==1){
                            diferenta = "1 luna";
                        }
                        else if (period.getMonths() < 12 ){
                            diferenta = period.getMonths()+ " luni";
                        }
                        else{
                            int months= period.getMonths();
                            if (months%12 == 0 ){
                                if (months / 12 ==1){
                                    diferenta = "1 an";
                                }
                                else{
                                    diferenta = period.getYears() + " ani";
                                }
                            }
                            else{
                                int luni = months%12;
                                String luniStr ="";
                                if (luni >1){
                                    luniStr = months%12+" luni";
                                    period.minusMonths(months%12);
                                }
                                else{
                                    luniStr = "1 luna";
                                    period.minusMonths(1);
                                }
                                String ani="";
                                if (period.getYears()==1){
                                    ani = "1 an";
                                }
                                else{
                                    ani = period.getYears() +" ani";
                                }
                                diferenta = ani + " " + luniStr;
                            }
                        }
                    }
                    if(period.getMonths()<1){
                        diferenta = period.getDays() + " zile";
                    }
                    else {
                        diferenta = period.getMonths()+ " luni";
                    }

                    raport3.add(new Raport3(
                                    dic.getContract().getPersoana().getMarca(),
                                    dic.getContract().getPersoana().getName(),
                                    dic.getContract().getPersoana().getLastName(),
                                    dic.getDept().getNumeDept(),
                                    dic.getPost().getName(),
                                    dic.getTipContract().getName(),
                                    dic.getContract().getStartDate(),
                                    dic.getContract().getEndDate(),
                                    diferenta
                            )
                    );
                }
                output = ExcelGenerator.createExcel(raport3, false);;
                break;
            case "4":
                listaContracte = contractService.getNewUsers();
                List<Raport4> raport4 = new ArrayList<>();
                for (DicContracteIsto dic: listaContracte){
                    AppUser appUser = appUserRepository.findByMarca(dic.getContract().getPersoana().getMarca());
                    Timestamp t = Timestamp.valueOf(appUser.getCreateDateTime());
                    Date createDate = new Date(t.getTime());
                    raport4.add(new Raport4(
                            dic.getContract().getPersoana().getMarca(),
                            dic.getContract().getPersoana().getName(),
                            dic.getContract().getPersoana().getLastName(),
                            dic.getDept().getNumeDept(),
                            dic.getPost().getName(),
                            dic.getContract().getStartDate(),
                            appUser.getUsername(),
                            createDate)
                    );
                }
                output = ExcelGenerator.createExcel(raport4, false);
                break;
            default: output = null;
        }

        return output;
    }




}
