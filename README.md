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
			.docker run --name sicrediMongo -p 27017:27017 -d mongo
				[Subir um contêiner]
			.winpty docker exec -it sicrediMongo mongo admin
				[Executar o Administrador do MongoDB]
			.docker ps -a
				[Verifica o estado atual dos contêineres]
			.docker stop sicrediMongo
				[Parar o contêiner criado]
			.docker start sicrediMongo
				[Iniciar com o comando]
			.docker rm sicrediMongo
				[Remover um contêiner]

		...MongoDB
			http://localhost:27017 --> Verificar se esta "no ar".
			...command´s:
				.show dbs
					[Verificar os bancos criados]
				.use local
					[Determinado banco]
				.show collections
					[Coleções existentes deste banco]
				.exit
					[Sair do administrador]

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
			.Associado > Botão direitor em cima de "AssociadoApplication.java" e Selecionar: "Run As..." "Java Application"
	
		Maven:
			.Pauta > No diretorio da aplicação rode a linha de commando: "mvn spring-boot:run"
			.Associado > No diretorio da aplicação rode a linha de commando: "mvn spring-boot:run"
		
		"No ar" em: 
			.Pauta > http://localhost:8090/
			.Associado > http://localhost:8091/

	Acessar Swagger:
		.Pauta: http://localhost:8090/swagger-ui.html
		.Associado: http://localhost:8091/swagger-ui.html
	
	Tecnologias do MVP: Docker, Mongo, Spring Boot, Spring Cloud, Java8, Rest, Maven, Junit, Mockito...
		
## ++Para Melhorar++
	.Documentação Swagger
			usar: http://springfox.github.io/springfox/docs/current/

	.Cliente Rest
		Melhorar tipo dados:
			.LocalDateTime
			
	.Adicionar Load Balancer - Ribbon/Feign e Spring Cloud Netflix

## Considerações
	A decisão de usar algumas tecnologias foi baseado no cotidiano, isto é,  o que eu uso diáriamente no
	projeto e tambem no desejo de colocat em pratica algumas tecnologias vistas na Pós Graduação e outras
	lidas em matérias da área de TI.

	Optei por provar do Docker e MongoDb (no Window) para simplificar a crianção e manutenção de uma instancia
	de banco de dados e a opção do MongoDb pela sua simplicidade por não ser relacional, para fugir do tradicional
	H2 ou ate mesmo da abstração fornecida pela Spring.

	Usar conceito da arquitetura de microserviços com Spring Boot usando Rest para consumo, alem disso 
	usado Spring Cloud para acessar clientes externos, o que possibilitaria deixar ainda mais granular os projetos.
	
## Rabisco de idéias
	MVP #1: Criar estrutura para suporte rest para a criação e listagem de: Associado, Pauta, Sessao e Voto. 
![alt text](https://github.com/grfrick/woopPauta/blob/master/rabisco%20inicia%20de%20ideias%20MVP.jpeg)
