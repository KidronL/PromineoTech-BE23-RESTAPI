package pet.store.controller.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Data
@NoArgsConstructor
public class PetStoreEmployee {
	
	public PetStoreEmployee(Employee employee) {
		this.empId = employee.getEmpId();
		this.empFrstNm = employee.getEmpFrstNm();
		this.empLstNm = employee.getEmpLstNm();
		this.empPhn = employee.getEmpPhn();
		this.empJbTtl = employee.getEmpJbTtl();
		this.petStore = employee.getPetStore();
		
	}

	public Long empId;
	public String empFrstNm;
	public String empLstNm;
	public Long empPhn;
	public String empJbTtl;
	
	public PetStore petStore;
}
