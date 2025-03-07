# security-tokens
Provides JWT tokens for authenticating and executing essential requests in my projects.

# Esse projeto está dividido em 1 branch.
* **tokens &rarr;** Serivirá de LIB externa para ser importado em outros projetos através do arquivo **pom.xml** para gerar e validar tokens JWT.

# Pre-requisitos para rodar o projeto
N/A

# Softwares de apoio
N/A

# Dependência externa
N/A

# Como preparar o JAR par executar em outros projetos
1. Abra um **Terminal** válido para rodar comandos do Docker, por exemplo, **PowerShell** ou **Git Bash**.
2. Clone o projeto para sua máquina. Exemplo: `git clone https://github.com/evertongodoy/security-tokens.git`
3. Acesse a branch desejada. Exemplo: `git checkout tokens`
4. Abra o projeto no IntelliJ e faça a configuração do JAVA:
    1. Procure no menu por **Project Structure**
    2. Em Project Settings, selecione **Project**
    3. Em Project SDK, selecione a versão do Java que você deseja utilizar, no caso, Corretto 17.
    4. Se ainda não estiver instalada, clique em **Download** e selecione a versão e o vendor. Diretório não precisa ser alterado.
5. No IntelliJ, abra a janela **Run Anything** tecla Control(2x) e execute o comando `mvn clean install`.


# Orientações para uso
1. Esse projeto vai ser uma **lib** para outros projetos que desejam trabalhar com Tokens.
2. Poderá ser realizado validação de Token e criação de Token.
3. Não vai ser um projeto que vai executar no Tomcat utilizando a dependência WEB do Spring Boot.
4. Possui um Use Case **GenerateTokenUseCaseImpl** para gerar tokens.
5. Possui também uma anotação personalizada **@EscopoNecessario** para validar Token, de modo que analise se o usuário possui um determinado scope, entre uma lista de scopos permitidos, no caso, `"listar-filmes"`.


# Orientações para uso em outros projetos
* Deverá ser importado através do arquivo **pom.xml**.
* **pom.xml**
```xml
<dependency>
   <groupId>br.senac.sp.security.tokens</groupId>
   <artifactId>security-tokens</artifactId>
   <version>0.0.1</version>
</dependency>
```

* Dado que o **pom.xml** do projeto **security-tokens** é o seguinte:
* **pom.xml**
```xml
<groupId>br.senac.sp.security.tokens</groupId>
<artifactId>security-tokens</artifactId>
<version>0.0.1</version>
<packaging>jar</packaging>
<name>security-tokens-jwt</name>
<description>Provides JWT tokens for authenticating and executing essential requests in my projects.</description>
```