package ro.facultate.aplicatieHR.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ro.facultate.aplicatieHR.entity.AppRole;
import ro.facultate.aplicatieHR.entity.AppUser;
import ro.facultate.aplicatieHR.entity.DicPerso;
import ro.facultate.aplicatieHR.security.JWTFilter;
import ro.facultate.aplicatieHR.service.UserService;

import java.io.IOException;
import java.util.*;


@RestController
public class UserController {
 
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    
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
	            
	            
	            DicPerso appPerso = appUser.getPerso();
	            
	            //adaugam in raspuns elementele care vor fi stocate permanent in partea de client
	            tokenMap.put("username", appUser.getUsername());
	            tokenMap.put("name", appPerso.getName());
	            tokenMap.put("lastName", appPerso.getLastName());
	            tokenMap.put("dept", appPerso.getDept().getDeptId());
	            tokenMap.put("id", appUser.getId());
	            
	            Set<String> rolesText = new HashSet<String>();
	            
	            Set<AppRole> appRoles = appUser.getRoles();
	            
	            for (AppRole appRole : appRoles) {
	            	rolesText.add(appRole.getRoleName());
	            }
	            
	            tokenMap.put("roles", rolesText);
	            

    		}
    		else {
    			tokenMap.put("message", "Contul nu este inca activat.");
    		}

        } else {
            tokenMap.put("message", "Credentialele introduse nu sunt corecte!");
  
        }
    	
    	return tokenMap;
        
       
    }
    
    
//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//    public HashMap<String, Object> createUser(@RequestBody AppUser appUser) {
//
//    	HashMap<String, Object> response = new HashMap<String, Object>();
//    	response.put("success", false);
//        if (userService.findByUsername(appUser.getUsername()) != null) {
//        	response.put("message", "Userul deja exista in baza de date");
//        } else {
//        	userService.saveCustomRole(appUser, "ROLE_USER");
//
//        	response.put("success", true);
//        }
//        return response;
//    }
    
    @RequestMapping(value = "/api/user", method = RequestMethod.GET)
    public AppUser getUserData(@RequestParam Long id){
    	  	   
    	
    	AppUser appUser= userService.findById(id);
    	
    	return appUser;
    }
    
    @RequestMapping(value = "/api/editUser", method = RequestMethod.PUT)
    public HashMap<String, Object> editUser(@RequestBody AppUser user) {
    	HashMap<String, Object> response = new HashMap<String, Object>();
    	response.put("success", false);
    	if(userService.editUser(user)!=null) {
    		response.put("success", true);
    	}
    	else {
    		response.put("message", "Datele utilizatorului nu au putut fi salvate");
    	}
    	return response;
    }
    
    @RequestMapping(value = "/api/changepassword", method = RequestMethod.PUT)
    public HashMap<String, Object> changePw(@RequestParam Long id, @RequestParam String oldPw, @RequestParam String newPw) {
    	HashMap<String, Object> response = new HashMap<String, Object>();
    	
    	response.put("success", false);
    	AppUser appUser = userService.findById(id);  	
    	
    	if (appUser != null && bCryptPasswordEncoder.matches(oldPw, appUser.getPassword())) {
    		appUser.setPassword(bCryptPasswordEncoder.encode(newPw));
    		userService.saveUser(appUser);
    		response.put("success", true);
    	}
    	
    	
    	return response;
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