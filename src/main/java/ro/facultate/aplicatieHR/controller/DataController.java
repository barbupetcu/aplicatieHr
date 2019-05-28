package ro.facultate.aplicatieHR.controller;

import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.facultate.aplicatieHR.entity.data.Dept;
import ro.facultate.aplicatieHR.entity.data.Posturi;
import ro.facultate.aplicatieHR.entity.data.TipuriContracte;
import ro.facultate.aplicatieHR.projection.Judete;
import ro.facultate.aplicatieHR.projection.Orase;
import ro.facultate.aplicatieHR.service.DataService;

import java.util.List;

@RestController
@RequestMapping(value = "/data")
public class DataController{

	private static final Logger logger = Logger.getLogger(DataController.class);
	
	@Autowired
	private DataService dataService;
	@Autowired
	private Gson gson;
	
	@RequestMapping(value = "/depts", method = RequestMethod.GET)
	public ResponseEntity getDepts(){
		List<Dept> depts = dataService.getDeptAll();
		if (depts != null) {
			return ResponseEntity.ok().body(depts);
		}
		logger.error("Departamentele nu fost incarcate");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Lista de departamente nu poate fi incarcata"));
	}

	@RequestMapping(value = "/tipuricontracte", method = RequestMethod.GET)
	public ResponseEntity getTipuriContracte(){
		List<TipuriContracte> tipCnt = dataService.getTipuriCntAll();
		if (tipCnt != null) {
			return ResponseEntity.ok().body(tipCnt);
		}
		logger.error("Tipurile de contracte nu au fost incarcate");
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Lista de tipuri de contracte nu poate fi incarcata"));
	}

	@RequestMapping(value = "/posturi", method = RequestMethod.GET)
	public ResponseEntity getPosturi(@RequestParam(value = "deptId", required = false) String deptId){
		List<Posturi> posturi = dataService.getPosturiDept(Integer.parseInt(deptId));
		if (posturi != null) {
			return ResponseEntity.ok().body(posturi);
		}
		logger.error("Nu au fost gasite posturi pentru departamentul: " + deptId);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Lista de posturi nu poate fi incarcata"));
	}

	@RequestMapping(value = "/judete", method = RequestMethod.GET)
	public ResponseEntity getJudete(){
		List<Judete> judete = dataService.getAllJudete();
		if (judete != null) {
			return ResponseEntity.ok().body(judete);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Lista de judete nu poate fi incarcata"));
	}

	@RequestMapping(value = "/orase", method = RequestMethod.GET)
	public ResponseEntity getOrase(@RequestParam(value = "judet", required = false) String judet){
		List<Orase> orase = dataService.getAllOraseByJudet(Integer.parseInt(judet));
		if (orase != null) {
			return ResponseEntity.ok().body(orase);
		}
		logger.error("Nu au fost gasite orase pentru judetul: " + judet);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Lista de judete nu paote fi incarcata"));
	}

	
	
}