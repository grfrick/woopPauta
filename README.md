# Projeto - woopPauta
Teste para Desenvolvedor(a) Backend do Woop Sicredi

## Setup´s
==DOCKER + MONGO==

	...Docker (Windows) --> https://hub.docker.com/editions/community/docker-ce-desktop-windows
  		...Validar a instalação:
			docker version
			docker run hello-world
		...Subir um "Mongo", command´s:
			.docker pull mongo
				[Instalar o MongoDB]
			.docker images
				[Visualizar imagem baixada]
			.docker run --name sicredi-mongo -p 27017:27017 -d mongo
				[Subir um contêiner]
			.docker exec -it some-mongo mongo admin
				[Executar o Administrador do MongoDB]
			.docker ps -a
				[Verifica o estado atual dos contêineres]
			.docker stop sicredi-mongo
				[Parar o contêiner criado]
			.docker start sicredi-mongo
				[Iniciar com o comando]
			.docker rm sicredi-mongo
				[Remover um contêiner]

		...MongoDB
			http://localhost:27017 --> Verificar
			...command:
				.show dbs
					[Verificar os bancos criados]
				.use local
					[Determinado banco]
				.show collections
					[Coleções existentes deste banco]
				.exit
					[Sair do administrador]==SPRING INITIALIZR==

## Sites Uteis
	==SPRING INITIALIZR==
	https://start.spring.io/

	==GITIGNORE INITIALIZR==
	https://www.gitignore.io/
	
## Projeto
	Com o MongoDb rodando com sucesso...
	
	Rodar applicação: 
		Eclipse:
			.Pauta > Botão direitor em cima de "PautaApplication.java" e Selecionar: "Run As..." "Java Application"
			.Eleitor > Botão direitor em cima de "EleitorApplication.java" e Selecionar: "Run As..." "Java Application"
	
		Maven:
			.Pauta > No diretorio da aplicação rode a linha de commando: "mvn spring-boot:run"
			.Eleitor > No diretorio da aplicação rode a linha de commando: "mvn spring-boot:run"
		
		"No ar" em: 
			.Pauta > http://localhost:8090/
			.Eleitor > http://localhost:8091/

	Acessar Swagger:
		.Pauta: http://localhost:8090/swagger-ui.html
		.Eleitor: http://localhost:8091/swagger-ui.html
	
	Tecnologias: Docker, Mongo, Spring Boot, Spring Cloud, Java8, Rest, ...
		
## ++Para Melhorar++
	.Documentação Swagger
			http://springfox.github.io/springfox/docs/current/

	.Cliente Rest
		Melhorar tipo dados:
			.LocalDateTime
		Melhorar validações de imput de dados
			.Mensagens de tratamento de erro

	.Adicionar testes unitário
			Junit, Mockito, MockMvc,...
	
	.Adicionar Handler para melhorar o tratamento de exceções

## Considerações
	A decisão de usar algumas tecnologias foi baseado no cotidiano, isto é,  o que eu uso diáriamente no
	projeto e tambem no desejo de colocat em pratica algumas tecnologias vistas na Pós Graduação e outras
	lidas em matérias da área de TI.

	Optei por provar do Docker e MongoDb (no Window) para simplificar a crianção e manutenção de uma instancia
	de banco de dados e a opção do MongoDb pela sua simplicidade por não ser relacional, para fugir do tradicional
	H2 ou ate mesmo da abstração fornecida pela Spring.

	Usar conceito da arquitetura de microserviços com Spring Boot usando Rest para consumo, alem disso 
	usado Spring Cloud para acessar clientes externos, o que possibilitaria deixar ainda mais granular os projetos.
	
