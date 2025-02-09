# Development stage
FROM node:20-alpine

WORKDIR /app

# Copy package files
COPY package*.json ./

# Install dependencies
RUN npm install -g @angular/cli
RUN npm install

# Copy project files
COPY . .

# Expose port 4200
EXPOSE 4200

# Start development server
CMD ["ng", "serve", "--host", "0.0.0.0", "--poll", "500"]
