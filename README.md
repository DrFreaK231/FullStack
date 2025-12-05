🚀 Full-Stack E-Commerce Demo (Next.js + Spring Boot + OAuth2 + PayPal + Docker)

This is a full-stack e-commerce demo project built as a portfolio piece, showcasing:

✅ Multi-layer Spring Boot backend
✅ Next.js 14 frontend
✅ OAuth2 login (Google, GitHub, Internal Auth Server)
✅ PayPal payments
✅ Three independent microservices
✅ Full Docker & Docker Swarm setup
✅ JWT + refresh tokens
✅ SQL Server database
✅ Clean environment variable setup (no secrets in GitHub)

📦 Tech Stack
Frontend
Next.js 14
React
TypeScript
JWT auth (HTTP-only cookies)
Backend

Three independent Spring Boot services:

Service	Description

Auth Server	Issues OAuth2 tokens / JWT
Client API	Main e-commerce REST API
RSS Server	Demo RSS microservice
Payments
PayPal Sandbox integration (Orders API + Captures API)
Database
Microsoft SQL Server (via Docker)
Security
OAuth2 Login (Google / GitHub / Internal Server)
JWT + Refresh Tokens
CSRF protection
Password hashing (BCrypt)
Deployment
Docker
Docker Compose

📁 Project Structure
project-root/
│
├─ backend/
│   ├─ as/          # Auth Server (Spring Boot)
│   ├─ client/      # Main API (Spring Boot)
│   └─ rss/         # RSS Microservice (Spring Boot)
│
├─ frontend/        # Next.js 14 App
│
├─ docker/          # Dockerfiles for each service
│
├─ docker-compose.yml
├─ docker-stack.yml  # For Docker Swarm
└─ .env.example

⚙️ Environment Setup

Secrets are NOT included in this repository.
Copy the example env file:
cp .env.example .env
Then fill in your values:

GOOGLE_CLIENT_ID=
GOOGLE_CLIENT_SECRET=
GITHUB_CLIENT_ID=
GITHUB_CLIENT_SECRET=

PAYPAL_CLIENT_ID=
PAYPAL_CLIENT_SECRET=

SPRING_DATASOURCE_URL=
SPRING_DATASOURCE_USERNAME=
SPRING_DATASOURCE_PASSWORD=


Each backend uses these placeholders inside application.properties, e.g.:
spring.security.oauth2.client.registration.google.client-id=${GOOGLE_CLIENT_ID}

🐳 Running with Docker (Local)
Build and start all services:

docker compose up --build


Frontend:
👉 http://localhost:3000

Client Backend:
👉 http://localhost:8080

Auth Server:
👉 http://localhost:8000

RSS Server:
👉 http://localhost:9000

SQL Server:
👉 localhost:1433

🖥️ Local Development (Without Docker)
Backend
cd backend/client
mvn spring-boot:run

Frontend
cd frontend
npm install
npm run dev

🔐 Security Features

OAuth2 login (Google / GitHub / custom auth server)
HTTPS-ready configuration
CSRF tokens (Next.js fetch wrapper)
Secure JWT & refresh token strategy
BCrypt password hashing
Strict CORS + cookie configuration

💳 PayPal Integration

Implemented features:
Create order
Capture payment
Save transaction to database
PayPal Webhook ready
Return/cancel pages

📚 What This Project Demonstrates
This project is designed as a portfolio showcase, demonstrating:

✔ Real full-stack architecture
✔ Multi-service backend
✔ OAuth2 + JWT authentication
✔ Secure payment workflow
✔ Dockerized production-ready stack
✔ Clean code & proper project structure

📝 Notes for Testers

This repository does not include secrets.

Before running:
Fill .env or application.properties
Add your own OAuth client IDs
Set your PayPal sandbox keys
Configure your own SQL Server credentials

🧑‍💻 Author

Tayza Thiha
Full-Stack Developer
(Singapore)
