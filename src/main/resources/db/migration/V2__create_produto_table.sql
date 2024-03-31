create table products.produto (
id bigserial primary key,
produto_identifier varchar not null,
nome varchar(100) not null,
descricao varchar not null,
preco float not null,
categoria_id bigint REFERENCES products.categoria(id)
);