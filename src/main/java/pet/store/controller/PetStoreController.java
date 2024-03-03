package pet.store.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import pet.store.controller.model.PetStoreData;
import pet.store.service.PetStoreService;

@RestController
@RequestMapping("/pet_store")
@Slf4j
public class PetStoreController {

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
    
}
