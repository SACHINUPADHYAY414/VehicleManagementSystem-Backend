# 🚗 VehicleManagementSystem

A Spring Boot-based vehicle management system with user authentication, dealer vehicle listing, and purchase functionality.

---

## 🔧 Tech Stack

- **Backend:** Spring Boot
- **Database:** PostgreSQL
- **Authentication:** JWT (JSON Web Token)
- **Validation:** Spring Validation

---

## 📚 Features

### 🔐 Authentication
- User **Login** & **Register**
- Secure access via **JWT Token**

### 🧑‍💼 Dealer Operations
- Dealer **Registration**
- Add and manage **Vehicles**

### 🛒 User Operations
- Browse available **Vehicles**
- **Buy** a Vehicle

---

## 🗂️ Project Structure

src/
├── controller
├── entity
├── repository
├── service
├── DTO
└── exception 


---

## 🛠️ Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/SACHINUPADHYAY414/VehicleManagementSystem.git
Navigate to project directory

cd VehicleManagementSystem


Configure PostgreSQL

Update application.properties with your DB credentials.

Build & Run the app

./mvnw spring-boot:run

| Endpoint                | Method | Description          |
| ----------------------- | ------ | -------------------- |
| `/api/auth/register`    | POST   | Register new user    |
| `/api/auth/login`       | POST   | Login user           |
| `/api/dealers`          | POST   | Add new dealer       |
| `/api/vehicles`         | POST   | Add vehicle (dealer) |
| `/api/payment/initiate` | POST   | Buy a vehicle (user) |


📝 Author

Sachin Upadhyay
GitHub Profile

Would you like me to auto-generate this file and add it to your GitHub repo as well?
