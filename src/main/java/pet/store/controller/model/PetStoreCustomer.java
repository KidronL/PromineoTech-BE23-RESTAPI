package pet.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.PetStore;

@Data
@NoArgsConstructor
public class PetStoreCustomer {

	private Long cstId;
	private String cstFrstNm;
	private String cstLstNm;
	private String cstEml;
	
	private Set<PetStore> petStores = new HashSet<>();	

}
