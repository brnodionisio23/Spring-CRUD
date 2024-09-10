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

# Camadas
Separamos nossa aplicação em diversas camadas agrupando as classes para facilitar o desenvolvimento e a manutenção da mesma.

* __MODEL__ - (Entity) Camada de representação da tabela do banco de dados.
* __REPOSITORY__ - Camada que funciona como ponte entre a aplicação e as tabelas do banco, utilizando JPA para realizar as interações.
* __SERVICE__ - Camada que contem a lógica de negócio "o código em si".
* __CONTROLLER__ - Responsável pelos endpoints (rotas) da aplicação.

## Model
O model (entities) é o modelo da nossa tabelo no banco

* Sugestão de implementação
  * Atributos basicos
  * Associações
  * Contrutores
  * Getters e Setters
  * hashCode & equals
  * Serializable


```java
@Entity
@Table(name = "To-do")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Todo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "Tarefa")
    String task;
    @Column(name = "Categoria")
    String category;
}
```

```java
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String name;

  @ManyToMany
  @JoinTable(
          name = "Category_Todo",
          joinColumns = @JoinColumn(name = "category_id"),
          inverseJoinColumns = @JoinColumn(name = "todo_id")
  )
  private Set<Todo> todo = new HashSet<>();
}
```

__Serializable__ tem a finalidade de permitir o trafego dos dados pela rede

__@Entity__ diz ao spring que essa classe sera a entidade do banco<br>
__@table__ define propriedades do banco, no nosso caso o nome<br>
__@Data__ anotação do Lombok para gerar nossos getter e setter<br>
__@AllArgsContructor__ gera um contrutor com todos os argumentos pelo Lombok<br>
__@NoArgsContructor__ gera um contrutor sem argumentos pelo Lombok<br>
__@JoinTable__ tem a função de definir uma tabela intermediária entre as tabela relacionadas ManyToMany<br>

__@Id__ diz qual atributo sera o id da nossa tabela<br>
__@GeneratedValue__ diz qual regra de geração de id sera utilizado (IDENTITY para ids sequenciais e UUID para geração aleatoria de String)<br>
__@Column__ define caracteristicas da coluna de nossa tabela<br>

### Modelo Entidade Relacionamento

__@OneToOne__ Esta anotação é usada para especificar um relacionamento unidirecional entre duas entidades, onde uma entidade tem uma referência para outra entidade. Por exemplo, um **usuário pode ter uma conta bancária**. <br> <br>
__@OneToMany__ Esta anotação é usada para especificar um relacionamento unidirecional de um para muitos entre duas entidades, onde uma entidade tem uma referência para várias entidades. Por exemplo, **um professor pode ter vários alunos**. <br><br>
__@ManyToOne__ Esta anotação é usada para especificar um relacionamento unidirecional de muitos para um entre duas entidades, onde várias entidades têm uma referência para uma entidade. Por exemplo, **vários alunos podem ter o mesmo professor**. <br><br>
__@ManyToMany__ Esta anotação é usada para especificar um relacionamento bidirecional de muitos para muitos entre duas entidades, onde várias entidades têm referências para várias entidades. Por exemplo, **vários alunos podem ter vários professores**. <br> <br>

## Repository
Essa camada tem a responsabilidade de ser a ponte entre a aplicação e o nosso banco de dados utilizando o JPA (Java Persistence Application) possuindo metodos de interação com a nossa tabela abstraindo toda a complexidade do JDBC

``` java
public interface TodoRepository extends JpaRepository<Todo, Long> {}
```

* Essa classe não necessita da anottation __@Service__ pois ´ja é herdada do JpaRepository

Para o JPA entrar em ação precisamos passar o __tipo do dado__, no caso nossa tabela, e o __tipo do identificador__ desse dado

## Service
Esta camada é responsável pela lógica da aplicação

```java
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class TodoService {

  @Autowired
  private TodoRepository repository;
}
```
* Criando uma nova instancia do repositório refente podemos utilizar os métodos JPA em nossa aplicação.

__@Autowired__ anotação do spring para injetar dependencias.

```java
public Todo save(Todo todo){
    return repository.save(todo);
}
```
O método __create__ salva um objeto na nossa tabela.

```java
public List<Todo> findAll(){
    return repository.findAll();
}
```
O método __findAll__ retorna uma List<> com todos os elementos da tabela.
```java
public Todo findById(Long id){
    Optional<Todo> obj = repository.findById(id);
    return obj.get();
}
```
O método __findById__ recebe o id selecionado e o retorna, devemos tratar a excessão caso a função receba um id inválido.
```java
public Todo update(Long id, Todo todo) {
    Optional<Todo> entity = repository.findById(id);

    if (entity.isPresent()) {
        Todo obj = entity.get();

        obj.setTask(todo.getTask);
        obj.setCategory(todo.getCategory);

        return repository.save(obj);
    } else {
        return null;
    }
}
```
Para atualizarmos um dado existente primeiro devemos receber em nossa função o id do campo que queremos atualizar e o objeto com os novos dados, apois isso devemos verificar se a entidade existe e passar os novos dados a ela, caso contrário, lançar uma excessão.
```java
public void delete(Long id){
    repository.findById(id).ifPresent(repository::delete);
}
```
O método __delete__ buscamos o elemento a ser deletado e deletamos.

## Controller
* Camada responsável pelos endpoints da nossa aplicação, são pontos de acesso para troca de dados na nossa aplicação entre diferentes serviços como por exemplo: cliente servidor.

```java
@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @PostMapping("/add")
    public ResponseEntity<Category> insert(@RequestBody Category obj) {
        Category newObj = service.insert(obj);
        return new ResponseEntity<>(newObj, HttpStatus.CREATED);
    }

}
```

__@RestController__ diz a aplicação que a nossa classe é um controlador Rest onde todo retorno será serializado e enviado pelo corpo das requisições HTTP <br><br>
__@RequestMapping__ tem a finalidade de mapear requisições web