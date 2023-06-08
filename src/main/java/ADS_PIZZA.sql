CREATE DATABASE ADS_PIZZA;
USE ADS_PIZZA;

CREATE TABLE MOTOBOY (
    placaMoto char(7) PRIMARY KEY,
    nomeEntregador varchar(30) not null
);

CREATE TABLE CLIENTE (
    idCliente int PRIMARY KEY AUTO_INCREMENT,
    nomeCliente varchar(30) not null,
    telefone varchar(15) not null,
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
VALUES ('ABC1234', 'João'),
       ('DEF5678', 'Maria'),
       ('GHI9012', 'Pedro');

INSERT INTO CLIENTE (nomeCliente, telefone, endereco, cidade)
VALUES ('João da Silva', '(XX) XXXX-XXXX', 'Rua A, 123', 'São Paulo'),
       ('Maria Oliveira', '(XX) XXXX-XXXX', 'Avenida B, 456', 'Rio de Janeiro'),
       ('Pedro Santos', '(XX) XXXX-XXXX', 'Travessa C, 789', 'Belo Horizonte');

INSERT INTO PEDIDOS (descricao, valor, clienteId, motoboyId)
VALUES ('Pizza de Calabresa', 30.00, 1, 'ABC1234'),
       ('Pizza de Mussarela', 25.00, 2, 'DEF5678'),
       ('Pizza de Frango com Catupiry', 35.00, 3, 'GHI9012');
