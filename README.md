# Spring-CRUD
Repositório destinado a mostrar o passo a passo de como criar um crud em Spring-Boot  3.3.2

### Configuração inicial
<https://start.spring.io/>

## Dependências utilizadas
* Spring Boot DevTools - realiza LiveReload para produtividade no desenvolvimento 

basta adicionar a linha no arquivo application.properties para ativar a funcionalidade.
```
spring.devtools.restart.enabled=true
```

* __Spring Web__ - Adiciona funcionalidades para trabalhar com requisições web
* __Spring Data JPA__ - ferramenta de persistencia e mapeamento objeto-relacional em java
* __H2 Database__ - "mini" banco de dados em memória para testes
* __Lombok__ - biblioteca de anotações para reduzir códigos boilerplate

## Configurações do banco de dados
Nesse projeto utilizamos o banco H2, um banco de teste que armazena os dados em memória, porém configuramos para persistir seus dados permanentemente.

As configurações foram feitas no arquivo *application.properties*    
```
spring.application.name=to-do-list

spring.devtools.restart.enabled=true

# DATABASE
spring.datasource.url=jdbc:h2:file:./h2-db
spring.datasource.username=sa
spring.datasource.password=

# H2 CLIENT
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.defer-datasource-initialization=true
spring.jpa.properties.hibernate.format_sql=true
```

* __DATABASE__ - definição de qual banco iremos utilizar, usuário e senha de acesso
* __H2 CLIENT__ - Ativação do cliente e definindo em browser para facilitar acesso
* __JPA__ - configuração do jpa onde gera tabelas automaticamente, configura para interagir com bando sql compativel com H2 e configuração para motrar os logs formatados de interações com o banco utilizado