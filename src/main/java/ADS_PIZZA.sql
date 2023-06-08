CREATE DATABASE ADS_PIZZA;
USE ADS_PIZZA;

CREATE TABLE MOTOBOY (
    placaMoto char(7) PRIMARY KEY,
    nomeEntregador varchar(30) not null
);

CREATE TABLE CLIENTE (
    idCliente int PRIMARY KEY AUTO_INCREMENT,
    nomeCliente varchar(30) not null,
    telefone varchar(15) not null unique, 
    endereco varchar(100) not null,
    cidade varchar(30) not null
);

CREATE TABLE PEDIDOS (
    idPedido int PRIMARY KEY AUTO_INCREMENT,
    descricao varchar(100) not null,
    valor decimal(10,2) not null,
    clienteId int,
    motoboyId char(7),
    FOREIGN KEY (clienteId) REFERENCES CLIENTE(idCliente),
    FOREIGN KEY (motoboyId) REFERENCES MOTOBOY(placaMoto)
);

INSERT INTO MOTOBOY (placaMoto, nomeEntregador)
VALUES ('ABC1A23', 'Bruno'),
       ('DEF5B67', 'Carlos'),
       ('GHI9C01', 'Zeca');

INSERT INTO CLIENTE (nomeCliente, telefone, endereco, cidade)
VALUES ('João da Silva', '11 92131-1211', 'Rua A, 123', 'São Paulo'),
       ('Maria Oliveira', '11 91111-2321', 'Avenida B, 456', 'São Caetano'),
       ('Pedro Santos', '11 9245-2124', 'Travessa C, 789', 'Santo André');

INSERT INTO PEDIDOS (descricao, valor, clienteId, motoboyId)
VALUES ('Pizza de Calabresa', 30.00, 1, 'ABC1A23'),
       ('Pizza de Mussarela', 25.00, 2, 'DEF5B67'),
       ('Pizza de Frango com Catupiry', 35.00, 3, 'GHI9C01');
