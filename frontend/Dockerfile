FROM node:18-alpine3.14

WORKDIR /code

COPY ["package.json", "package-lock.json*", "./"]

RUN  npm install

COPY . .

RUN npm run build

EXPOSE 5000

CMD ["npm", "start"]