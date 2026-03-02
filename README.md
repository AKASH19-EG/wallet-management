# Wallet Transaction System

A simple wallet-based transaction system built using **Spring Boot** where each client has a wallet.
Admins can credit or debit wallets, and clients can create orders using their wallet balance.
Each order triggers an external fulfillment API and stores the returned fulfillment ID.

---

## 🚀 Tech Stack

- Java 17
- Spring Boot
- PostgreSQL
- Maven

---

## 📦 Features

- Wallet maintained per client
- Admin APIs to credit and debit wallet
- Wallet ledger to track all transactions
- Order creation with atomic wallet deduction
- External fulfillment API integration
- Order details retrieval with fulfillment ID

---
