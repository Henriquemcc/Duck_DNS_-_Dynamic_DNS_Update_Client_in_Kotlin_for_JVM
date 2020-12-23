[Versão em Português](README.md)

# DuckDNS for Linux in Java

This program is aimed to run Duck DNS on Linux with IPv6 and IPv4 in dual stack automatically.

## Installation

To install this program:

### 1- Creating the folder where the program for Duck DNS will be installed

To create the folder where will be installed the program, type the following command on the terminal:

```
mkdir duckdns
cd duckdns
```

### 2- Downloading the program binary on the folder

Download the program binary from the page [releases](https://github.com/Henriquemcc/Duck_DNS_Java/releases) on this
folder.

After downloaded you will have to make the binary runnable. For it, type the following command on the terminal (inside
the folder):

```
chmod 700 <.jar_Binary_Name>
```

in which '<.jar_Binary_Name>' is replaced by the name of the file which has been downloaded.

### 3- Configuring Duck DNS

On this folder, open the terminal and type the following command:

```
java -jar <.jar_Binary_Name>
```

in which '<.jar_Binary_Name>' is replaced by the name of the file which has been downloaded.

The program will ask you to enter the Subdomain and Token:

```
File Not Loaded
Please, type:
Subdomain: <Subdomain> 
Token: <Token>
```

#### Example

```
example
12345678-9012-3456-7890-123456789012
```

#### Subdomain Duck DNS

If your registered domain is: example.duckdns.org, so you should insert just 'example' (without quotation marks) as your
subdomain.

#### Duck DNS token

Once logged in the Duck DNS website, your token will be listed in the [Duck DNS home page](https://www.duckdns.org/).

### 4- Adding the program to the crontab

To verify if your Linux Kernel operating system has crontab, type the following command on the terminal:

```
ps -ef | grep cr[o]n
```

If nothing appears, find out how to install crontab in your Linux distribution.

Having corontab installed, type the following command:

```
crontab -e
```

and copy the following text to the last line:

```
*/5 * * * * cd ~/duckdns/ && java -jar ./<.jar_Binary_Name> > ./duck.log
```

in which '<.jar_Binary_Name>' is replaced by the name of the file which was downloaded.

### 6- Test

To test if the program is working, type the following command on the terminal inside the folder where the binary was
installed

```
java -jar <.jar_Binary_Name>
```

in which '<.jar_Binary_Name>' is replaced by the name of the file which was downloaded.

## System requirements

To run this program is required to have a Operating System with Linux Kernel with openjdk 8 jre and Crontab installed.

## License

This program is licensed under the [MIT License](LICENSE).

## Documentation

The documentation is available in [html](./JavaDoc.zip).