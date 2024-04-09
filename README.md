<div align="center">

# Desafio Engenharia de Software - Itaú

![](https://img.shields.io/badge/Autor-Rodrigo%20Sartori-brightgreen)
![](https://img.shields.io/badge/Language-Java-brightgreen)
![](https://img.shields.io/badge/Framework-Spring%20Boot-brightgreen)
<br><br>
![GitHub Release Date](https://img.shields.io/badge/Release%20Date-Abril%202024-yellowgreen)
![](https://img.shields.io/badge/Status-Em%20Desenvolvimento-orange)

</div>

<div align="center">

# Bank Transfer - Itaú
O objetivo do desafio é desenhar e desenvolver uma solução que permita que os clientes do Itaú consigam realizar Transferência entre contas.
<br>Essa solução precisa ser resiliente, ter alta disponibilidade e de fácil evolução/manutenção.

</div>

## 💻 Sobre o projeto

Serviço responsável por realizar transferências bancárias validando informações na "API Cadastro" e "API Contas" e por 
fim, notificar o Bacen por meio da "API Bacen"

## 🚀 Como executar o projeto

### Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina as seguintes ferramentas:
[Java](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html), [Maven](https://maven.apache.org/download.cgi) e [Docker](https://www.docker.com/products/docker-desktop/)

#### 🎲 Rodando o Microserviço
```bash
# Clone este repositório
$ git clone https://github.com/sartori00/bank-transfer.git

# Acesse a pasta do projeto no terminal/cmd
$ cd bank-transfer

#Certifique de que o Docker está em execução

Execute a aplicação na sua IDE por meio da classe BankTransferApplication.

# Ao iniciar a Aplicação será criado um container no Docker com o MongoDB na porta 27017.
# Será criado também um container no Docker com uma instância do wiremock com mocks dos microserviços que iremos nos utilizar.
# A aplicação será aberta na porta:8080 - acesse http://localhost:8080
```
