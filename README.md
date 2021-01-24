# 3Rivers API Developer Interview "Assignment"

## Overview 

Hello and welcome to the developer challenge for microservices developer role on the 3rivers project. In our project we are writing spring boot APIs that each serve a given [BIAN Service Domain](https://www.bian.org). The data that the APIs exposed are sourced from Systems of Record to produce a realtime feed through [Kafka](https://kafka.apache.org). Our APIs are either written [on top of Kafka topics directly](https://kafka.apache.org/10/documentation/streams/developer-guide/interactive-queries.html) using [Event Sourcing](https://martinfowler.com/eaaDev/EventSourcing.html) or we use the traditional approach of sinking our data from Kafka to a database and write our API on of of the databases. 

The purpose of this challenge is to have you work through a problem where the expectation is for you to produce an API. We have specifically opted to make the problem statement as generic as possible to allow you to: 
1. Show us your thought process and creativity. That way the interview is fun.
2. Flex your technical muscles as much as you want on us.
2. Not get bored.

The *ONLY* requirement is that this API **MUST BE WRITTEN USING THE SPRING BOOT FRAMEWORK**.

## Problem Statement

**Before you forget**
- Take a look at: https://spring.io/guides/gs/rest-service/#scratch 
- Again, This API needs to use Spring Boot. 


Ok, back to the problem statement. 

### The Data Feeds

Let's assume you have the two following data feeds: 

#### Feed 1: The balances
This is a data feed, where each *event* represents a single balance update for a given account. 

A *single* record looks like this
```
{"accountNumber": "abc", "lastUpdateTimestamp": "2020-01-01T01:02:03.8Z", "balance": 89.1}
```

#### Feed 2: The transactions

This is a data feed representing transactions that are occuring. Each record will be a single transaction at the bank. Keep in mind that there are *two* types of transactions: 1. `DEPOSIT` and 2. `WITHDRAW`. 

A *single* record looks like this
For a `DEPOSIT`:
```
{"accountNumber": "abc", "transactionTs": "2020-01-03T01:02:03.8Z", "type": "DEPOSIT", "amount": 89.1}
```
Or a `WITHDRAW`
```
{"accountNumber": "abc", "transactionTs": "2020-01-03T01:02:03.8Z", "type": "WITHDRAW", "amount": 89.1}
```

### The API [ the actual meat of the assignment ;) ]

Please write an API that can serve the following queries: 

1. Given an `accountNumber`, return the latest balance. 
2. Given an `accountNumber` and a `time range` such as: `Today`, `Last 7 days`, `last Month` and the more general case of a range between `date X` and `date Y`. For example, I should be able to ask for all my transactions between `January 8th, 2019` and `November 28th, 2020`.
3. Repeat 2, but filter for `type`. I.E. Show me transactions with type `WITHDRAW`. 

#### Some hints: 
1. Make sure that any technical choice you are making is backed up by good reasoning. Meaning, if you decide to go with event sourcing? Why? If you choose to use a noSQL datbase? Why? 
2. Make reasonable assumptions about the problem. If any extra detail is left out, just ride the wave and make assumptions. There are no wrong answers here. 
3. (**Attention**) We will probably ask you what issues might come about if the size of the response from query 2 and 3 is too large. What if you had 1 million transactions in that time range range. Think about that and maybe implement a solution for it. 

## Admin

During the interview you will be expected to showcase your code and **hopefully** a demo. We would like to give you at least a couple of days to work on it. Let the recruiter know if you need more time, in case we mess up the schedule and your interview is scheduled before the 2 day mark. 

To keep things fair, we don't answer any additional questions regarding the assignment after you have received it. 