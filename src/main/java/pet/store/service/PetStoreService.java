package pet.store.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import pet.store.controller.model.PetStoreCustomer;
import pet.store.controller.model.PetStoreData;
import pet.store.controller.model.PetStoreEmployee;
import pet.store.dao.CustomerDao;
import pet.store.dao.EmployeeDao;
import pet.store.dao.PetStoreDao;
import pet.store.entity.Customer;
import pet.store.entity.Employee;
import pet.store.entity.PetStore;

@Service
public class PetStoreService {

	private PetStoreDao petStoreDao;
	private EmployeeDao employeeDao;
	private CustomerDao customerDao; 

	//Declarations to  DAO interfaces. 
	@Autowired
	public PetStoreService(PetStoreDao petStoreDao) {
		this.petStoreDao = petStoreDao;
	}
	
	@Autowired
	public void EmployeeService(EmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}
	
	@Autowired
	public void CustomerService(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	//Methods for creating and finding Pet Stores
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

	//Methods for creating and finding Employees
	@Transactional(readOnly = false)
	public PetStoreEmployee saveEmployee(Long petStrId, PetStoreEmployee petStoreEmployee) {
		PetStore petStore = findPetStoreById(petStrId);
		Long employeeId = petStoreEmployee.getEmpId();
		Employee employee = findOrCreateEmployee(employeeId);
		
		copyEmployeeFields(employee, petStoreEmployee);
		
		employee.setPetStore(petStore);
		petStore.employees.add(employee);
		
		employee = employeeDao.save(employee);
		
		return new PetStoreEmployee(employee);
		
	}

	private void copyEmployeeFields(Employee employee, PetStoreEmployee petStoreEmployee) {
		
		employee.empId = petStoreEmployee.empId;
		employee.empFrstNm = petStoreEmployee.empFrstNm;
		employee.empLstNm = petStoreEmployee.empLstNm;
		employee.empPhn = petStoreEmployee.empPhn;
		employee.empJbTtl = petStoreEmployee.empJbTtl;
		employee.petStore = petStoreEmployee.petStore;
		
	}

	private Employee findOrCreateEmployee(Long employeeId) {
		
		if (employeeId == null) {
			return new Employee();
		} else {
			return findEmployeeById(employeeId);
		}
	}

	private Employee findEmployeeById(Long employeeId) {
		return employeeDao.findById(employeeId)
				.orElseThrow(() -> new NoSuchElementException("Employee with ID " + employeeId + " not found"));
	}

	private PetStore findPetStoreById(Long petStrId) {
		return petStoreDao.findById(petStrId)
				.orElseThrow(() -> new NoSuchElementException("Pet store with ID " + petStrId + " not found"));
	}
	
	//Methods for creating and finding Customers
	@Transactional(readOnly=false)
	public PetStoreCustomer saveCustomer(Long petStrId, PetStoreCustomer petStoreCustomer) {
		PetStore petStore = findPetStoreById(petStrId);
		Long customerId = petStoreCustomer.getCstId();
		Customer customer = findOrCreateCustomer(customerId, petStrId);
		
		copyCustomerFields(customer, petStoreCustomer);
		
		customer.petStores.add(petStore);
		petStore.customers.add(customer);
		
		customer = customerDao.save(customer);
		
		return new PetStoreCustomer(customer);
	}
	
	private void copyCustomerFields(Customer customer, PetStoreCustomer petStoreCustomer) {
		
		customer.cstId = petStoreCustomer.cstId;
		customer.cstFrstNm = petStoreCustomer.cstFrstNm;
		customer.cstLstNm = petStoreCustomer.cstLstNm;
		customer.cstEml = petStoreCustomer.cstEml;
		customer.petStores = petStoreCustomer.petStores;
		
	}

	private Customer findOrCreateCustomer(Long customerId, Long petStoreId) {
		
		if (customerId == null) {
			return new Customer();
		} else {
			return findCustomerById(customerId, petStoreId);
		}
	}

	private Customer findCustomerById(Long customerId, Long petStoreId) {
		Customer customer = customerDao.findById(customerId)
				.orElseThrow(() -> new NoSuchElementException("Customer with ID " + customerId + " not found"));
		
		boolean petStoreFound = false;
		for (PetStore petStore : customer.getPetStores()) {
			if (petStore.getPetStrId().equals(petStoreId)) {
				petStoreFound = true;
				break;
			}
		}
		
		if (!petStoreFound) {
			throw new IllegalArgumentException("PetStore with ID " + petStoreId + " is not associated with Customer with ID " + customerId);	
		}
		
		return customer;
	}

	//Methods for get operations
	@Transactional(readOnly = true)
	public List<PetStoreData> retrieveAllPetStores() {
		List<PetStore> petStores = petStoreDao.findAll();

        return petStores.stream()
                .map(this::convertToPetStoreData)
                .collect(Collectors.toList());
    }
	
    @Transactional(readOnly = true)
	public PetStoreData retrievePetStoreById(Long petStrId) {
            PetStore petStore = petStoreDao.findById(petStrId)
                    .orElseThrow(() -> new NoSuchElementException("Pet store with ID " + petStrId + " not found"));

            return convertToPetStoreData(petStore);
	}

    private PetStoreData convertToPetStoreData(PetStore petStore) {
        PetStoreData petStoreData = new PetStoreData();
        petStoreData.setPetStrId(petStore.getPetStrId());
        petStoreData.setPetStrNm(petStore.getPetStrNm());
        petStoreData.setPetStrAddr(petStore.getPetStrAddr());
        petStoreData.setPetStrCty(petStore.getPetStrCty());
        petStoreData.setPetStrSt(petStore.getPetStrSt());
        petStoreData.setPetStrZip(petStore.getPetStrZip());
        petStoreData.setPetStrPhn(petStore.getPetStrPhn());

        return petStoreData;
	}

    //Method used to delete
	public void deletePetStoreById(Long petStrId) {
		PetStore petStore = findPetStoreById(petStrId);
		petStoreDao.delete(petStore);
	}


}
