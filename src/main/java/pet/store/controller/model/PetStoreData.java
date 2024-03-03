package pet.store.controller.model;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Data
@NoArgsConstructor
public class PetStoreData {
	public Long petStrId;
	public String petStrNm;
	public String petStrAddr;
	public String petStrCty;
	public String petStrSt;
	public Long petStrZip;
	public Long petStrPhn;
	
	private Set<PetStoreCustomer> customers = new HashSet<>();
	
	private Set<PetStoreEmployee> employees = new HashSet<>();
	
	public PetStoreData(PetStore petStore) {
		
		this.petStrId = petStore.petStrId;
		this.petStrNm = petStore.petStrNm;
		this.petStrAddr = petStore.petStrAddr;
		this.petStrCty = petStore.petStrCty;
		this.petStrSt = petStore.petStrSt;
		this.petStrZip = petStore.petStrZip;
		this.petStrPhn = petStore.petStrPhn;
		
		this.customers = new HashSet<>();
		this.employees = new HashSet<>();
		
		for (Customer customer : petStore.getCustomers()) {
            this.customers.add(new PetStoreCustomer());
        }
		
        for (Employee employee : petStore.getEmployees()) {
            this.employees.add(new PetStoreEmployee());
        }
		
	}


}
