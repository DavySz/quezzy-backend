# Quezzy Backend

<h1 align="center">
  <img alt="quezzy" title="quezzy" src=".github/images/cover.png" />
</h1>

Aplicação educacional que utiliza gamificação para transformar o aprendizado em uma experiência interativa, promovendo engajamento com questões lúdicas e conteúdos sobre cidadania e sustentabilidade.

## 🚀 Getting started

Para rodar o projeto no ambiente local siga os passos a seguir:

```bash
# clone o projeto para a sua máquina

$ git clone git@github.com:DavySz/quezzy-backend.git

```

Abra o projeto na sua IDE de preferência e crie na raiz do projeto o arquivo .env:


```bash
SPRING_PROFILES_ACTIVE=dev
DATABASE_URL=jdbc:postgresql://java_db:5432/postgres
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=postgres

```

Em seguida, no terminal rode o comando:

```bash

$ docker-compose up --build

```

</br>

## 🧪 Teste a API

Após rodar o projeto localmente, você pode acessar a documentação do projeto no Swagger, abrindo esta URL no seu navegador:

```bash

$ http://localhost:8080/swagger-ui/index.html

```

</br>

## ✨ Tecnologias

- [x] Spring Boot
- [x] Java
- [x] Hibernate
- [x] Postgresql

</br>

---
<p align="center">Made with ❤️ by Davy de Souza</p>
