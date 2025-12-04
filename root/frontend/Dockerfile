# Step 1: Build the Next.js app
FROM node:20-alpine AS builder

WORKDIR /app

# Copy only package files first to leverage caching
COPY package*.json ./
RUN npm install

# Copy the rest of the project files
COPY . .

# Build for production
RUN npm run build

# Step 2: Run with a lightweight image
FROM node:20-alpine AS runner

WORKDIR /app

# Copy only necessary files from builder
COPY --from=builder /app/package*.json ./
COPY --from=builder /app/.next ./.next
COPY --from=builder /app/public ./public
COPY --from=builder /app/node_modules ./node_modules

# Expose port
EXPOSE 3000

# Run the app
CMD ["npm", "start"]
