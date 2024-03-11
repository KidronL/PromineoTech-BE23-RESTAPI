package pet.store.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {

	//Mapping for CRUD operations
	private PetStoreService petStoreService;
	
	@Autowired
	public PetStoreController(PetStoreService petStoreService) {
		this.petStoreService = petStoreService;
	}
	
    @RequestMapping(method = RequestMethod.POST)
    public PetStoreData createPetStore(@RequestBody PetStoreData petStoreData) {
        log.info("Received request to create pet store: {}", petStoreData);
        return petStoreService.savePetStore(petStoreData);
    }
    
    @PutMapping("/{storeId}")
    public PetStoreData updatePetStore(@PathVariable Long storeId, @RequestBody PetStoreData petStoreData) {
        log.info("Updating pet store with ID {}: {}", storeId, petStoreData);
        petStoreData.setPetStrId(storeId);
        return petStoreService.savePetStore(petStoreData);
    	
    }
    
    @PostMapping("/{petStrId}/employee")
    @ResponseStatus(HttpStatus.CREATED)
    public PetStoreEmployee addEmployee(@PathVariable Long petStrId, @RequestBody PetStoreEmployee employee) {
        log.info("Adding employee to pet store with ID {}: {}", petStrId, employee);
        return petStoreService.saveEmployee(petStrId, employee);
    }
    
    @PostMapping("/{petStrId}/customer")
    @ResponseStatus(HttpStatus.CREATED)
    public PetStoreCustomer addCustomer(@PathVariable Long petStrId, @RequestBody PetStoreCustomer customer) {
        log.info("Adding customer to pet store with ID {}: {}", petStrId, customer);
        return petStoreService.saveCustomer(petStrId, customer);
    }
    
    @GetMapping
    public List<PetStoreData> retrieveAllPetStores() {
    	return petStoreService.retrieveAllPetStores();
    }
    
    @GetMapping("/{petStrId}")
    public PetStoreData retrievePetStoreById(@PathVariable Long petStrId) {
    	return petStoreService.retrievePetStoreById(petStrId);
    }
    
    @DeleteMapping("/{petStrId}")
    public Map<String, String> deletePetStoreById(@PathVariable Long petStrId) {
    	petStoreService.deletePetStoreById(petStrId);
    	Map<String, String> response = new HashMap<>();
    	response.put("message", "Pet store has been deleted");
    	return response;
    }
    
}
