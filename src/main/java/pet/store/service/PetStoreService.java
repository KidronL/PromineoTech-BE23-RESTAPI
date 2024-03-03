package pet.store.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pet.store.controller.model.PetStoreData;
import pet.store.dao.PetStoreDao;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {

	private PetStoreDao petStoreDao;

	@Autowired
	public PetStoreService(PetStoreDao petStoreDao) {
		this.petStoreDao = petStoreDao;
	}

	public PetStoreData savePetStore(PetStoreData petStoreData) {
		PetStore petStore = findOrCreatePetStore(petStoreData.getPetStrId());
		copyPetStoreFields(petStore, petStoreData);
		petStoreDao.save(petStore);
		return new PetStoreData(petStore);
	}

	private PetStore findOrCreatePetStore(Long petStoreId) {
		if (petStoreId == null) {
			return new PetStore();
		} else {
			return petStoreDao.findById(petStoreId)
					.orElseThrow(() -> new NoSuchElementException("Pet store with ID " + petStoreId + " not found"));
		}
	}

	private void copyPetStoreFields(PetStore petStore, PetStoreData petStoreData) {
		petStore.petStrNm = petStoreData.petStrNm;
		petStore.petStrAddr = petStoreData.petStrAddr;
		petStore.petStrCty = petStoreData.petStrCty;
		petStore.petStrSt = petStoreData.petStrSt;
		petStore.petStrZip = petStoreData.petStrZip;
		petStore.petStrPhn = petStoreData.petStrPhn;
	}
}
