package com.example.UnitechProject.auth;

import com.example.UnitechProject.config.UserDetailsServiceImpl;
import com.example.UnitechProject.dto.CustomerDTO;
import com.example.UnitechProject.dto.LoginDto;
import com.example.UnitechProject.entity.Customer;
import com.example.UnitechProject.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody CustomerDTO customerDTO) {
        if (customerRepository.existsByPin(customerDTO.getPin())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        customer.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        customerRepository.save(customer);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
        String pin = loginDto.getPin();
        String password = loginDto.getPassword();

        UserDetails userDetails = userDetailsService.loadUserByUsername(pin);

        if (userDetails != null && passwordEncoder.matches(password, userDetails.getPassword())) {
            return new ResponseEntity<>("Login successful", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }
    }
    }

