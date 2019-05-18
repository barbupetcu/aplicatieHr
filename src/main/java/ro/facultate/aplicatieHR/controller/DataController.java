package ro.facultate.aplicatieHR.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.facultate.aplicatieHR.entity.data.Dept;
import ro.facultate.aplicatieHR.entity.data.Posturi;
import ro.facultate.aplicatieHR.entity.data.TipuriContracte;
import ro.facultate.aplicatieHR.service.DataService;

import java.util.List;

@RestController
@RequestMapping(value = "/data")
public class DataController{

	private static final Logger logger = Logger.getLogger(DataController.class);
	
	@Autowired
	private DataService dataService;
	
	@RequestMapping(value = "/depts", method = RequestMethod.GET)
	public ResponseEntity<?> getDepts(){
		List<Dept> depts = dataService.getDeptAll();
		if (depts != null) {
			return ResponseEntity.ok().body(depts);
		}
		logger.error("Departamentele nu fost incarcate");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/tipuricontracte", method = RequestMethod.GET)
	public ResponseEntity<?> getTipuriContracte(){
		List<TipuriContracte> tipCnt = dataService.getTipuriCntAll();
		if (tipCnt != null) {
			return ResponseEntity.ok().body(tipCnt);
		}
		logger.error("Tipurile de contracte nu au fost incarcate");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/posturi/{dept}", method = RequestMethod.GET)
	public ResponseEntity<?> getPosturi(@PathVariable("dept") Integer deptId){
		List<Posturi> posturi = dataService.getPosturiDept(deptId);
		if (posturi != null) {
			return ResponseEntity.ok().body(posturi);
		}
		logger.error("Nu au fost gasite posturi pentru departamentul: " + deptId);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	
	
}