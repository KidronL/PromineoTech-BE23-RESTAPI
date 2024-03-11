package pet.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;
import pet.store.entity.PetStore;

@Data
@NoArgsConstructor
public class PetStoreCustomer {

	public Long cstId;
	public String cstFrstNm;
	public String cstLstNm;
	public String cstEml;
	
	public Set<PetStore> petStores = new HashSet<>();
	
	public PetStoreCustomer(Customer customer){
		
		this.cstId = customer.getCstId();
		this.cstFrstNm = customer.getCstFrstNm();
		this.cstLstNm = customer.getCstLstNm();
		this.cstEml = customer.getCstEml();
		this.petStores = customer.getPetStores();
		
	}

}
