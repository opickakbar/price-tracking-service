# 📊 Price Tracking Service

## 🚀 Overview
This project implements a backend service to **record price changes** and calculate the **average price over the last 30 seconds**.

## 🎯 Features
- ✅ Store price data via `POST /api/prices`
- ✅ Retrieve average prices via `GET /api/average-prices`
- ✅ **Constant time & memory efficiency** using `ConcurrentSkipListMap`
- ✅ Fully tested with **JUnit 5 & MockMvc**
- ✅ Uses **Spring Boot** for fast API development

---

## Tech Stack
- **Java 21** (Modern Java features)
- **Spring Boot** (For API development)
- **ConcurrentSkipListMap** (Efficient in-memory storage)
- **JUnit 5 + Mockito** (For unit & API tests)
- **Maven** (Build & dependency management)

---

## How to Run Locally

### **1️⃣ Clone the Repository**
```sh
git clone https://github.com/yourusername/your-repo.git
cd your-repo
```

### **2️⃣ Build the Project**
```sh
mvn clean install
```

### **3️⃣ Run the Application**
```sh
mvn spring-boot:run
```

---

## 🌐 API Endpoints

### **📌 1️⃣ POST `/api/prices`**
#### **Request:**
```json
{ "price": 123.45 }
```
#### **Response:**
`200 OK`

---

### **📌 2️⃣ GET `/api/average-prices`**
#### **Response:**
```json
[
  { "timestamp": "2025-01-01T12:01:31Z", "avg_price": 100.0 },
  { "timestamp": "2025-01-01T12:01:30Z", "avg_price": 200.0 }
]
```

---

## Running Tests
To run unit and integration tests:
```sh
mvn test
```
- ✅ **Unit tests** ensure that service logic is correct.
- ✅ **MockMvc tests** validate API responses.

---

## 📚 Project Structure
```
📞 takehometest
 ├ 📂 src
 ┃ ├ 📂 main
 ┃ ┃ ├ 📂 java/com/example/takehometest
 ┃ ┃ ┃ ├ 📂 controller  # REST API Layer
 ┃ ┃ ┃ ├ 📂 service     # Business Logic
 ┃ ┃ ┃ ├ 📂 dto         # Data Transfer Objects
 ┃ ┃ ┃ ├ 📂 exception   # Global Error Handling
 ┃ ┃ ├ 📂 resources     # Application configs
 ├ 📂 test              # Unit & API Tests
 ├ 📄 README.md         # Project documentation
 ├ 📄 pom.xml           # Maven dependencies
```

---

## 🔖 Additional Notes
- **Error Handling:** The API properly returns meaningful error messages for invalid input.
- **Scalability:** This implementation uses an **efficient, thread-safe** in-memory storage strategy.
- **Performance:** The system operates in **constant time (`O(1)`) for inserts & retrievals**.

---