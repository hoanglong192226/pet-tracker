FROM node:18.18-slim

WORKDIR /app

COPY package.json package-lock.json ./

RUN npm i --frozen-lockfile

COPY . .

RUN npm run build

EXPOSE 3000

CMD ["npm", "start"]