# Quiller-cash-register

## Table of content
1. [Short description](#Short-description)
2. [Technologies](#Technologies)
3. [Launch](#Launch)
4. [UML diagrams](#UML-diagrams)
5. [Content description](#Content-description)
6. [GUI views](#GUI-views)

## Short description
Quiller is a software designed for self service cash registers in supermarket. It was created as the final assignment for Implementation Techniqes classes at uni.
The whole programm has robust documentation attached in the project files, but only fragment off all functionalities is implemented. Serialization instead of database.

```diff
- WARNING!
The code in the project needs serious refactoring: 
1. Some variables are named with Polish words as the documentation was created in Polish language.
2. Code could be better structured.
```

To check my newest project (with better quality code) open the [Battle-Arena repository](https://github.com/KubKill/Battle-Arena).

To check **full documentation** to the current project [click here](Projekt_s18482.pdf). (Or open Projekt_s18482.pdf)

## Technologies
* Java 17
* JavaFx 18
* Maven

## Launch
To start the project follow steps:
1. Load the project main library with your IDE.
2. Run the "Quiller" class.

## UML diagrams
**Class diagram**
It's rather extensive. This one is second. The final one, transformed for implementatiion in Java language.

![image](https://user-images.githubusercontent.com/66681683/189535815-a71893b9-94bf-4c68-bd01-dc46d7b8592a.png)

![image](https://user-images.githubusercontent.com/66681683/189535829-695a5925-64ef-4a32-8ef7-52c40cdf97f7.png)

**Activity diagram** This ones shows the choosen use case which I implemented with GUI.

![image](https://user-images.githubusercontent.com/66681683/189536044-70542e85-0b94-4358-a1e6-57966565d1e6.png)

## Content description
Majority of the project is implemented without GUI. Part is implemented with.

The projects code covers:
1. Products that can be bought.
2. Users of the system:
   - variusly priviledged workers
   - customers
3. Buying activity:
   - cash register setting
   - basket
   - product selection by code and by choosing icon
   
## GUI views
**New purchase view**

![image](https://user-images.githubusercontent.com/66681683/189536560-ace48519-2f2b-4b0c-9fb5-a9158c3b2740.png)

**Session settings view**

![image](https://user-images.githubusercontent.com/66681683/189536579-3e5187d5-d652-468c-8d3c-4133fc124e16.png)

**Basket view**

![image](https://user-images.githubusercontent.com/66681683/189536587-a72863c7-1b4e-4f69-bcc1-2572e8cd6ca0.png)

**Products by icons view**

![image](https://user-images.githubusercontent.com/66681683/189536601-b167fe85-4576-4f8b-b8c5-c0c7326d0f33.png)

**Measuring products weight view**

![image](https://user-images.githubusercontent.com/66681683/189536852-65a68694-e98d-4387-abfa-a60eb8d03d12.png)


**Full basket view**

![image](https://user-images.githubusercontent.com/66681683/189536627-7b7c180b-e376-4ff1-8504-e2fda9383b88.png)
