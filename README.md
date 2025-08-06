# Bank Account Kata

This project implements a bank account system based on the requirements and user stories provided.
It is developed with a focus on test-driven-development (TDD), clean code and simplicity.

# Requirements

- Deposit and Withdrawal
- Account statement (date, amount, balance)
- Statement printing

# User Stories

## US 1:

> In order to save money
> As a bank client
> I want to make a deposit in my account

## US 2:
> In order to retrieve some or all of my savings
> As a bank client
> I want to make a withdrawal from my account

# Technologies Used

- java 17
- nodeJS 16.20.2
- Angular 17
- [Docker](https://docs.docker.com/get-docker/) ≥ 20.x
- [Docker Compose](https://docs.docker.com/compose/install/) ≥ v2


# How to Run the Application

- Clone the repository: git clone https://github.com/souhaayadi/bank-kata 
- Build the project: mvn clean install
- Run backend :
   - open backend folder 
   - run the main spring boot class : BankAccountKataApplication 
   - the backend will be available at : http://localhost:8080
   - run tests:  mvn test 
- run frontend : 
   - npm install
   - npm start
   - run tests : ng test
