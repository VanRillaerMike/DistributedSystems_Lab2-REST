package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class BankApplication {
	private Map<String, User> users = new HashMap<>();

	public synchronized static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}

	@GetMapping("/{userID}/getbalance")
	public synchronized String getBalance(@PathVariable String userID) {
		User user = users.get(userID);

		if (user == null) {
			return String.format("User not found, please first add money to the given ID.");
		}

		return String.format("%s, your balance is %s", userID, user.getBalance());
	}

	@PostMapping("/{userID}/postmoney")
	public synchronized String postMoney(@PathVariable String userID, @RequestParam(value = "amount", defaultValue = "0") String amount) {
		User user = users.get(userID);

		if(user == null){
			user = new User(userID, 0);
			users.put(userID, user);
		}
		return String.format("%s, your new balance is %s", userID, user.addBalance(Double.parseDouble(amount)));
	}

	@PutMapping("/{userID}/getmoney")
	public synchronized String getMoney(@PathVariable String userID, @RequestParam(value = "amount", defaultValue = "0") String amount) {
		User user = users.get(userID);

		if (user == null) {
			return String.format("User not found, please first add money to the given ID.");
		}

		if(user.withdrawBalance(Double.parseDouble(amount))){
			return String.format("%s, you withdrew %s!\nYour new balance is %s", userID, amount, user.getBalance());
		}

		return String.format("%s, you can't withdraw more than you have. Your current balance is %s", userID, user.getBalance());
	}

	@DeleteMapping("/{userID}/deletebalance")
	public synchronized String deleteBalance(@PathVariable String userID){
		User user = users.get(userID);

		if(user == null){
			return String.format("User not found, please first add money to the given ID.");
		}

		user.deleteBalance();
		return String.format("%s, you deleted your money..., it's balance is now %s", userID, user.getBalance());
	}
}