package pet.store.controller.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import pet.store.entity.PetStore;

@Data
@NoArgsConstructor
public class PetStoreEmployee {
	private Long empId;
	private String empFrstNm;
	private String empLstNm;
	private Long empPhn;
	private String empJbTtl;
	
	private PetStore petStore;
}
