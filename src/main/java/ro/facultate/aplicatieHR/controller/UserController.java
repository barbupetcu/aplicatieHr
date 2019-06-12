package ro.facultate.aplicatieHR.controller;

import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ro.facultate.aplicatieHR.dto.AngajatiRegisterDTO;
import ro.facultate.aplicatieHR.dto.RegisterDTO;
import ro.facultate.aplicatieHR.entity.app.AppRole;
import ro.facultate.aplicatieHR.entity.app.AppUser;
import ro.facultate.aplicatieHR.entity.dic.DicPerso;
import ro.facultate.aplicatieHR.security.JWTFilter;
import ro.facultate.aplicatieHR.service.UserService;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
	private ModelMapper modelMapper;

    @Autowired
	private Gson gson;

    @RequestMapping(value = "/loadAngajati", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity downloadFile1()  {
		List<DicPerso> angajati = userService.getDicPersoAll();

		List<DicPerso> angNonUser = angajati.stream().filter(a ->  userService.findByMarca(a.getMarca()) == null).collect(Collectors.toList());

		Type listType = new TypeToken<List<AngajatiRegisterDTO>>(){}.getType();

		List<AngajatiRegisterDTO> ang = modelMapper.map(angNonUser, listType);

		if(!ang.isEmpty()){
			return ResponseEntity.ok().body(ang);

		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Lista de angajati nu poate fi incarcata"));

	}

    
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public HashMap<String, Object> checkLogin(@RequestParam String username, @RequestParam String password) throws IOException {
    	String token = null;
        AppUser appUser = userService.findByUsername(username);
    	HashMap<String, Object> tokenMap = new HashMap<String, Object>();
    	List<String> roles = new ArrayList<String>();
    	if (appUser != null && bCryptPasswordEncoder.matches(password, appUser.getPassword())) {
    		if (appUser.isEnabled()) {
    			
    			for (AppRole appRole : appUser.getRoles()) {
    	    		roles.add(appRole.getRoleName());
    	    	}
    			token = Jwts.builder().setSubject(username).claim(JWTFilter.AUTHORITIES_KEY, roles).setIssuedAt(new Date())
	                    .signWith(SignatureAlgorithm.HS256, JWTFilter.TOKEN_KEY).compact();
	            tokenMap.put("token", token);
	            tokenMap.put("success", true);
	            DicPerso appPerso = userService.getDicPersoByMarca(appUser.getMarca());
	            //adaugam in raspuns elementele care vor fi stocate permanent in partea de client
	            tokenMap.put("username", appUser.getUsername());
	            tokenMap.put("name", appPerso.getName());
	            tokenMap.put("lastName", appPerso.getLastName());
				tokenMap.put("marca", appUser.getMarca());
    		}
    		else {
    			tokenMap.put("message", "Contul nu este inca activat.");
    		}
        } else {
            tokenMap.put("message", "Credentialele introduse nu sunt corecte!");
        }
    	return tokenMap;
        
    }
    
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity createUser(@RequestBody RegisterDTO registerDTO) {


        if (userService.findByUsername(registerDTO.getUsername()) != null ||
				userService.findByMarca(registerDTO.getMarca()) != null) {
        	return	ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(gson.toJson("Utilizatorul deja exista"));
        } else {
			AppUser appUser = modelMapper.map(registerDTO, AppUser.class);
        	userService.saveCustomRole(appUser, "ROLE_USER");

        	return ResponseEntity
					.status(HttpStatus.OK).body(gson.toJson("Utilizatorul a fost creat"));
        }
    }
    
    @RequestMapping(value = "/getUser/{id}", method = RequestMethod.GET)
    public ResponseEntity getUserData(@PathVariable("id") String id){

    	try{
			Long idLong = Long.parseLong(id);

			AppUser appUser= userService.findByMarca(idLong);

			return ResponseEntity.ok().body(appUser);
		}
    	catch (Exception e){
    		return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(gson.toJson("Eroare la preluarea detaliilor userului"));
		}
    }
    
    @RequestMapping(value = "/editUser", method = RequestMethod.PUT)
    public ResponseEntity editUser(@RequestBody AppUser user) {
    	try{
			userService.editUser(user);
			return ResponseEntity.ok(gson.toJson("Utilizatorul a fost modificat"));
		}
    	catch (Exception e){
    		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(gson.toJson("Utilizatorul nu a putut fi modificat"));
    	}
    }
    
    @RequestMapping(value = "/changepassword", method = RequestMethod.PUT)
    public ResponseEntity changePw(@RequestParam Long id, @RequestParam String oldPw, @RequestParam String newPw) {

    	AppUser appUser = userService.findByMarca(id);
    	
    	if (appUser != null && bCryptPasswordEncoder.matches(oldPw, appUser.getPassword())) {
    		appUser.setPassword(bCryptPasswordEncoder.encode(newPw));
    		userService.saveUser(appUser);
    		return ResponseEntity
					.status(HttpStatus.OK)
					.body(gson.toJson("Parola a fost modificata"));
    	}

		return ResponseEntity
				.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(gson.toJson("Parola nu a putut fi schimbata"));
    }
    
//    @RequestMapping(value = "/api/disabledUsers", method = RequestMethod.GET)
//    public HashMap<String, Object> getDisabledUsers(@RequestParam Long dept){
//    	HashMap<String, Object> response = new HashMap<String, Object>();
//
//    	//obtinem numarul de angajati care nu sunt activati din departamentul managerului
//    	Long countDisable = userService.countDisabledUser(dept);
//    	response.put("countDisabled", countDisable);
//
//    	List<DisabledUsers> disabledUsers = userService.getDisabledUsers(dept);
//    	response.put("disabledUsers", disabledUsers);
//
//    	return response;
//    }
    
    @RequestMapping(value = "/api/deleteUser", method= RequestMethod.DELETE)
    public HashMap<String, Object> deleteUser(@RequestParam Long id) {
    	HashMap<String, Object> response = new HashMap<String, Object>();
    	
    	userService.deleteUser(id);
    	response.put("success", true);
    	return response;
    }
    
//    @RequestMapping(value = "/api/getusers", method = RequestMethod.GET)
//    public List<TeamUsers> getEnabledUsers(@RequestParam Long dept){
//
//    	List<TeamUsers> users = userService.getEnabledUsers(dept);
//
//    	return users;
//    }

}