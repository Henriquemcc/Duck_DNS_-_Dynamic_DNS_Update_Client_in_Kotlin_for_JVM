[English Version](README.EN.md)

# DuckDNS para Linux em Kotlin/JVM

Este programa serve para executar o Duck DNS no Linux utilizando IPv6 e IPv4 em dual stack de forma automática.

## Instalação

Para instalar este programa:

### 1- Criando a pasta onde ficara instalado o programa para o Duck DNS

Para criar a pasta onde ficara instalado o programa, digite os seguintes comandos no terminal:

```
mkdir duckdns
cd duckdns
```

### 2- Baixando o executável na pasta

Baixe o executável .jar do programa da página das [releases](https://github.com/Henriquemcc/Duck_DNS_Kotlin_JVM/releases)
nesta pasta.

Depois que baixado será necessário tornar o binário como executável. Para isto, digite o seguinte comando no terminal (dentro da pasta):

```
chmod 700 <Nome_do_.jar>
```

em que '<Nome_do_.jar>' é substituído pelo nome do arquivo que foi baixado.

### 3- Configurando o Duck DNS

Nesta pasta, abra o terminal e digite o seguinte comando:

```
java -jar <Nome_do_.jar>
```

em que '<Nome_do_.jar>' é substituído pelo nome do arquivo que foi baixado.

O programa pedira para que informe o Subdomínio e o Token:

```
File Not Loaded
Please, type:
Subdomain: <Subdominio> 
Token: <Token>
```

#### Exemplo

```
exemplo
12345678-9012-3456-7890-123456789012
```

#### Subdomínio Duck DNS

Se o domínio que foi registrado for: exemplo.duckdns.org, então deve colocar apenas 'exemplo' (sem aspas) como
subdomínio.

#### Token do Duck DNS

Uma vez logado no site do Duck DNS, o seu token estará listado na [home page do Duck DNS](https://www.duckdns.org/).

### 4- Adicionando o programa ao crontab

Para verificar se o seu sistema operacional com kernel Linux possuí crontab, digite o seguinte comando no terminal:

```
ps -ef | grep cr[o]n
```

Caso não retorne nada, descubra como instalar o crontab na sua distribuição Linux.

Tendo o crontab instalado, digite o seguinte comando:

```
crontab -e
```

e copie o seguinte texto para a ultima linha:

```
*/5 * * * * cd ~/duckdns/ && java -jar ./<Nome_do_.jar> > ./duck.log
```

em que '<Nome_do_.jar>' é substituído pelo nome do arquivo que foi baixado.

### 6- Teste

Para testar se o programa está funcionando, digite o seguinte comando no terminal dentro da pasta onde o binário foi
instalado:

```
java -jar <Nome_do_.jar>
```

em que '<Nome_do_.jar>' é substituído pelo nome do arquivo que foi baixado.

## Pré-requisitos

Para que este programa rode é necessário estar rodando um Sistema Operacional com o Kernel Linux e com o openjdk 8 jre e
o Crontab instalados.

## Licença

Este programa está licenciado sobre a [European Union Public License 1.2](LICENSE).

## Dúvidas Frequentes

### O que é IPv6 e IPv4?

[![O que é o IPv6, em português claro](https://img.youtube.com/vi/_JbLr_C-HLk/0.jpg)](https://www.youtube.com/watch?v=_JbLr_C-HLk)

Mais informações: [IPv6.br](https://ipv6.br/).

### O que é DNS?

[![Como funciona a Internet? Parte 3: DNS](https://img.youtube.com/vi/ACGuo26MswI/0.jpg)](https://www.youtube.com/watch?v=ACGuo26MswI)