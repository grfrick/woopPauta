#Teste para Desenvolvedor(a) Backend do Woop Sicredi

# Projeto - woopPauta

## Setup´s
==DCOKER + MONGO==

	...Docker (Windows) --> https://hub.docker.com/editions/community/docker-ce-desktop-windows
  		...Validar a instalação:
			```docker version```
			```docker run hello-world```
		...Subir um "Mongo", command´s:
			.```docker pull mongo```
				[Instalar o MongoDB]
			.```docker images```
				[Visualizar imagem baixada]
			.```docker run --name sicredi-mongo -p 27017:27017 -d mongo```
				[Subir um contêiner]
			.```docker exec -it some-mongo mongo admin```
				[Executar o Administrador do MongoDB]
			.```docker ps -a```
				[Verifica o estado atual dos contêineres]
			.```docker stop sicredi-mongo```
				[Parar o contêiner criado]
			.```docker start sicredi-mongo```
				[Iniciar com o comando]
			.```docker rm sicredi-mongo```
				[Remover um contêiner]

		...MongoDB
			http://localhost:27017 --> Verificar
			...command:
				.```show dbs```
					[Verificar os bancos criados]
				.```use local```
					[Determinado banco]
				.```show collections```
					[Coleções existentes deste banco]
				.```exit```
					[Sair do administrador]
