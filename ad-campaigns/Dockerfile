# Etap 1: Budowanie aplikacji Angular
FROM node:20.18 AS build

# Ustawiamy katalog roboczy
WORKDIR /app

# Instalujemy npm w wersji 11.1
RUN npm install -g npm@11.1

# Kopiujemy pliki package.json oraz package-lock.json
COPY package*.json ./

# Instalujemy zależności
RUN npm install

# Kopiujemy resztę aplikacji
COPY . .

# Budujemy aplikację
RUN npm run build --prod

# Etap 2: Uruchomienie aplikacji
FROM node:20.18

# Ustawiamy katalog roboczy
WORKDIR /app

# Kopiujemy aplikację z poprzedniego etapu
COPY --from=build /app /app

# Instalujemy zależności (jeśli jakiekolwiek są)
RUN npm install

# Otwieramy port 4200 (domyślny port Angulara)
EXPOSE 4200

# Uruchamiamy aplikację Angular
CMD ["npm", "start"]
