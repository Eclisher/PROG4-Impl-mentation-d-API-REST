package com.bank.implementation.Controller;

public class SignInRequest {
        private String accountNumber;
        private String password;
        public SignInRequest() {
        }
        public String getAccountNumber() {
            return accountNumber;
        }
        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
    }


