package ro.facultate.aplicatieHR.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ro.facultate.aplicatieHR.entity.app.AppRole;
import ro.facultate.aplicatieHR.entity.app.AppUser;
import ro.facultate.aplicatieHR.entity.dic.DicPerso;
import ro.facultate.aplicatieHR.repository.app.AppRoleRepository;
import ro.facultate.aplicatieHR.repository.app.AppUserRepository;
import ro.facultate.aplicatieHR.repository.dic.DicPersoRepository;


@Service
public class UserService {
    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private AppRoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private DicPersoRepository dicPersoRepository;


    public void saveCustomRole(AppUser user, String roleName) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<AppRole> customRole = new HashSet<>();

        customRole.add(roleRepository.findByroleName(roleName));
        user.setRoles(customRole);
        DicPerso appPerso = user.getPerso();
        appPerso.setUser(user);

        user.setPerso(appPerso);

        AppUser response = userRepository.save(user);


    }
    
    public void saveAllRoles(AppUser user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    public AppUser findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public AppUser findById(Long id) {
    	Optional<AppUser> optional = userRepository.findById(id);
    	AppUser appUser = optional.get();
        return appUser;
    }
    
    public AppUser editUser(AppUser appUser) {
    	AppUser temp = findById(appUser.getId());
    	appUser.setPassword(temp.getPassword());
    	return userRepository.save(appUser);
    	
    	
    }
    
    public void saveUser(AppUser user) {
    	userRepository.save(user);
    }
    

    
    public void deleteUser(Long id) {
    	userRepository.deleteById(id);;
    }
}
