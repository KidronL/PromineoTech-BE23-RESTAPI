package pet.store.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

//Class declaration for Pet Store and values to be stored with Objects
@Entity
@Data
public class PetStore {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long petStrId;
	public String petStrNm;
	public String petStrAddr;
	public String petStrCty;
	public String petStrSt;
	public Long petStrZip;
	public Long petStrPhn;
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "pet_store_customer", joinColumns = @JoinColumn(name = "pet_store_id"), inverseJoinColumns = @JoinColumn(name = "customer_id"))
	public Set<Customer> customers = new HashSet<>();
	
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@OneToMany(mappedBy = "petStore", cascade = CascadeType.ALL, orphanRemoval = true)
	public Set<Employee> employees = new HashSet<>();

}
