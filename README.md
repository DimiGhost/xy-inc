# xy-inc
Este projeto é uma aplicação REST para gerenciamento e integração de POI.
O projeto foi implementado usando a framework Spring e SQL Lite.

### Compilando
A compilação é executada por Maven.
Para compilar a aplicação deve acessar a pasta raiz do projeto, e então executar o seguinte comando:
```
mvnw compile
```

### Execução
Ainda na pasta raiz, execute o seguinte comando:
```
mvnw spring-boot:run
```

### Testes
Para testar deve executar o comando:
```
mvnw test
```

### Uso
A aplicação é usada através de requisições GET e POST.

Para listar todos os POIs cadastrados, faça uma requisição GET para a URL seguinte.
```
http://localhost:8080/poi/listar
```

Para procurar os POIs mais próximos, faça uma requisição GET para a URL, informando os parâmetros, a coordenada X, coordenada Y e a distancia máxima igual o exemplo.
```
http://localhost:8080/poi/procurar?x=20&y=10&distancia=10
```

Para cadastrar um POI, faça uma requisição POST para a URL com o header `Content-Type: application/json` e o conteudo conforme o exemplo.
```
http://localhost:8080/poi/cadastrar

{
	"nome": "Hotel",
	"coordenadaX": 25,
	"coordenadaY": 15
}
```