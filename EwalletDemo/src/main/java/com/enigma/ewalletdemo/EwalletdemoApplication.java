package com.enigma.ewalletdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EwalletdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(EwalletdemoApplication.class, args);
        System.out.println("Wallet Start...");
    }

}
