package com.customer.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
//import com.customer.Exception.MissingFieldsException;
import com.customer.entity.CusEntity;
import com.customer.repository.CusRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3002")
@RequestMapping("/api")
public class CusController {

	@Autowired
	CusRepository cusRepository;

	@PostMapping("/register")
	public String createNewCus(@RequestBody CusEntity customer) {
	    List<String> missingFields = new ArrayList<>();
	    
	    if (customer.getFirstName() == null) {
	        missingFields.add("firstName");
	    }

	    if (customer.getLastName() == null) {
	        missingFields.add("lastName");
	    }

	    if (customer.getCity() == null) {
	        missingFields.add("city");
	    }

	    if (customer.getMail() == null) {
	        missingFields.add("mail");
	    }
	    if (customer.getMobileNumber() == null) {
	        missingFields.add("mobileNumber");
	    }

	    if (customer.getAddress() == null) {
	        missingFields.add("address");
	    }

	    if (customer.getPostalCode() == null) {
	        missingFields.add("postalCode");
	    }

	    if (customer.getPassword() == null) {
	        missingFields.add("password");
	    }

	    if (customer.getConfirmPassword() == null) {
	        missingFields.add("confirmPassword");
	    }

//	    if (!missingFields.isEmpty()) {
//	        throw new MissingFieldsException("Required field(s) are missing", missingFields);
//	    }

	    if (customer.getPassword() == null || customer.getConfirmPassword() == null) {
	        return "Password or Confirm Password is null.";
	    }

	    if (!customer.getPassword().equals(customer.getConfirmPassword())) {
	        return "Password and Confirm Password are not same.";
	    }


	    boolean isMailExists = cusRepository.existsByMail(customer.getMail());

	    if (isMailExists) {
	        return "Mail ID Already Exists.";
	    }

	    cusRepository.save(customer);
	    return "Customer created successfully";
	}



	@GetMapping("/customer")
	public ResponseEntity<List<CusEntity>> getAllCustomers() {
		List<CusEntity> cusList = new ArrayList<>();
		cusRepository.findAll().forEach(cusList::add);
		return new ResponseEntity<>(cusList, HttpStatus.OK);
	}

	@GetMapping("/customer/{mail}")
	public ResponseEntity<?> getCustomerByMail(@PathVariable String mail) {
		Optional<CusEntity> cus = cusRepository.findByMail(mail);
		if (cus.isPresent()) {
			return new ResponseEntity<>(cus.get(), HttpStatus.FOUND);
		} else {
			String errorMessage = "Customer with mail " + mail + " does not exist.";
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
		}
	}

	@PutMapping("/customer/{mail}")
	public String updateCustomerByMail(@PathVariable String mail, @RequestBody CusEntity customer) {
		Optional<CusEntity> cus = cusRepository.findByMail(mail);
		if (cus.isPresent()) {
			CusEntity existCus = cus.get();
			existCus.setPassword(customer.getPassword());
			existCus.setConfirmPassword(customer.getConfirmPassword());
			cusRepository.save(existCus);
			return "Customer Details against Mail " + mail + " updated";
		} else {
			return "Customer Details do not exist for mail " + mail;
		}
	}
	
	@DeleteMapping("/customer/all")
    public ResponseEntity<String> deleteAllCustomers() {
        try {
            cusRepository.deleteAll();
            return ResponseEntity.ok("All customers deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred while deleting all customers");
        }
    }

    @DeleteMapping("/customer/{mail}")
    public ResponseEntity<String> deleteCustomerByMail(@PathVariable String mail) {
        Optional<CusEntity> cus = cusRepository.findByMail(mail);
        if (cus.isPresent()) {
            try {
                cusRepository.delete(cus.get());
                return ResponseEntity.ok("Customer with mail " + mail + " deleted successfully");
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting customer with mail " + mail);
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Customer with mail " + mail + " does not exist.");
        }
    }


}
