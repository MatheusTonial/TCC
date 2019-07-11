INSERT INTO permissoes (permissao) values('ROLE_USER');
INSERT INTO permissoes (permissao) values('ROLE_ADMIN');

 insert into estados (sigla, nome) Values('AC','Acre');
 insert into estados (sigla, nome) Values('AL','Alagoas');
 insert into estados (sigla, nome) Values('AM','Amazonas');
 insert into estados (sigla, nome) Values('AP','Amapá');
 insert into estados (sigla, nome) Values('BA','Bahia');
 insert into estados (sigla, nome) Values('CE','Ceará');
 insert into estados (sigla, nome) Values('DF','Distrito Federal');
 insert into estados (sigla, nome) Values('ES','Espírito Santo');
 insert into estados (sigla, nome) Values('GO','Goiás');
 insert into estados (sigla, nome) Values('MA','Maranhão');
 insert into estados (sigla, nome) Values('MG','Minas Gerais');
 insert into estados (sigla, nome) Values('MS','Mato Grosso do Sul');
 insert into estados (sigla, nome) Values('MT','Mato Grosso');
 insert into estados (sigla, nome) Values('PA','Pará');
 insert into estados (sigla, nome) Values('PB','Paraíba');
 insert into estados (sigla, nome) Values('PE','Pernambuco');
 insert into estados (sigla, nome) Values('PI','Piauí');
 insert into estados (sigla, nome) Values('PR','Paraná');
 insert into estados (sigla, nome) Values('RJ','Rio de Janeiro');
 insert into estados (sigla, nome) Values('RN','Rio Grande do Norte');
 insert into estados (sigla, nome) Values('RO','Rondônia');
 insert into estados (sigla, nome) Values('RR','Roraima');
 insert into estados (sigla, nome) Values('RS','Rio Grande do Sul');
 insert into estados (sigla, nome) Values('SC','Santa Catarina');
 insert into estados (sigla, nome) Values('SE','Sergipe');
 insert into estados (sigla, nome) Values('SP','São Paulo');
 insert into estados (sigla, nome) Values('TO','Tocantins');

INSERT INTO cidades(nome, estado_id) VALUES ('São Paulo', 26);
INSERT INTO cidades(nome, estado_id) VALUES ('Curitiba', 18);
INSERT INTO cidades(nome, estado_id) VALUES ('Pato Branco', 18);
INSERT INTO cidades(nome, estado_id) VALUES ('Rio de Janeiro', 19);
INSERT INTO cidades(nome, estado_id) VALUES ('Jundiaí', 26);
INSERT INTO cidades(nome, estado_id) VALUES ('Maringá', 18);

INSERT INTO usuarios(bairro, cpf, email, nome, numero, rg, rua, senha, telefone, tipo, cidade_id) VALUES ('Centro', '888.888.888-88', 'admin@admin.com', 'Admin', '42', '77.777.777-7', 'rua1', '$2a$10$0QS34iuQv3Xh4ILpMygu4.ZHrUnstA6umr6XZv4UMDnOyndMMh1JC', '(46)32254-8523', 'Corretor', 1);
INSERT INTO usuarios(bairro, cpf, email, nome, numero, rg, rua, senha, telefone, tipo, cidade_id) VALUES ('Centro', '888.888.888-88', 'mateus_tonial@hotmail.com', 'Matheus', '42', '77.777.777-7', 'rua1', '$2a$10$0QS34iuQv3Xh4ILpMygu4.ZHrUnstA6umr6XZv4UMDnOyndMMh1JC', '(46)32254-8523', 'Cliente', 1);

-- INSERT INTO usuarios_permissoes(usuario_id, permissoes_id) VALUES (1, 1);
INSERT INTO usuarios_permissoes(usuario_id, permissoes_id) VALUES (1, 2);
INSERT INTO usuarios_permissoes(usuario_id, permissoes_id) VALUES (2, 1);

INSERT INTO marcas(descricao) VALUES ('Marca 1');
INSERT INTO marcas(descricao) VALUES ('Marca 2');
INSERT INTO marcas(descricao) VALUES ('Marca 3');
INSERT INTO marcas(descricao) VALUES ('Marca 4');
INSERT INTO marcas(descricao) VALUES ('Marca 5');
INSERT INTO marcas(descricao) VALUES ('Marca 6');
INSERT INTO marcas(descricao) VALUES ('Marca 7');
INSERT INTO marcas(descricao) VALUES ('Marca 8');
INSERT INTO marcas(descricao) VALUES ('Marca 9');
INSERT INTO marcas(descricao) VALUES ('Marca 10');

INSERT INTO veiculos(ano, modelo, placa, marca_id) VALUES (2010, 'Modelo 111', 'abc-1234', 1);
INSERT INTO veiculos(ano, modelo, placa, marca_id) VALUES (2018, 'Modelo 321', 'xyz-9876', 1);

INSERT INTO tipo_seguro(descricao) VALUES ('Veiculo');
INSERT INTO tipo_seguro(descricao) VALUES ('Imoveis');
INSERT INTO tipo_seguro(descricao) VALUES ('Vida');

INSERT INTO emails(texto, prazo1, prazo2, titulo) VALUES ('Prezado CLIENTE, seu seguro de TIPO ira vencer em DATA', 5, 10, 'Vencimento Seguro');
INSERT INTO emails(texto, prazo1, prazo2, titulo) VALUES ('Prezado CLIENTE, a proxima parcela de seu seguro de TIPO ira vencer em DATA', 5, 10, 'Vencimento Parcela Seguro');


INSERT INTO seguros(data_seg, n_parcelas, valor, email_id, tipo_id, usuario_id, veiculo_id, parcelas_pagas, vencimento) VALUES ('11-08-2019', 2, 3500, 1, 1, 1, 1, 1, '11-08-2020');
INSERT INTO parcelas(data_pago, data_vencimento, tamanho, status, seguro_id) VALUES ('10-09-2019', '11-09-2019', '1/2', 'pago', 1);
INSERT INTO parcelas(data_pago, data_vencimento, tamanho, status, seguro_id) VALUES (null, '11-10-2019', '2/2', 'aberto', 1);

INSERT INTO seguros(data_seg, n_parcelas, valor, email_id, tipo_id, usuario_id, veiculo_id, parcelas_pagas, vencimento) VALUES ('06-04-2019', 4, 5000, 1, 2, 2, 1, 2, '06-04-2020');
INSERT INTO parcelas(data_pago, data_vencimento, tamanho, status, seguro_id) VALUES ('05-05-2019', '06-05-2019', '1/4', 'pago', 2);
INSERT INTO parcelas(data_pago, data_vencimento, tamanho, status, seguro_id) VALUES ('04-06-2019', '06-06-2019', '2/4', 'pago', 2);
INSERT INTO parcelas(data_pago, data_vencimento, tamanho, status, seguro_id) VALUES (null, '06-07-2019', '3/4', 'aberto', 2);
INSERT INTO parcelas(data_pago, data_vencimento, tamanho, status, seguro_id) VALUES (null, '06-08-2019', '4/4', 'aberto', 2);


INSERT INTO seguros(data_seg, n_parcelas, valor, email_id, tipo_id, usuario_id, veiculo_id, parcelas_pagas, vencimento, situacao) VALUES ('01-01-2019', 1, 6650, 1, 2, 2, 1, 1, '01-01-2020', 'terminado');
INSERT INTO parcelas(data_pago, data_vencimento, tamanho, status, seguro_id) VALUES ('01-02-2019', '01-02-2019', '1/1', 'pago', 3);

INSERT INTO seguros(data_seg, n_parcelas, valor, email_id, tipo_id, usuario_id, veiculo_id, parcelas_pagas, vencimento, situacao) VALUES ('03-02-2019', 2, 5900, 1, 3, 2, 1, 2, '03-02-2020', 'terminado');
INSERT INTO parcelas(data_pago, data_vencimento, tamanho, status, seguro_id) VALUES ('01-03-2019', '03-03-2019', '1/2', 'pago', 4);
INSERT INTO parcelas(data_pago, data_vencimento, tamanho, status, seguro_id) VALUES ('03-04-2019', '03-04-2019', '2/2', 'pago', 4);

INSERT INTO seguros(data_seg, n_parcelas, valor, email_id, tipo_id, usuario_id, veiculo_id, parcelas_pagas, vencimento) VALUES ('19-07-2018', 2, 5000, 1, 1, 2, 1, 1, '19-07-2019');
INSERT INTO parcelas(data_pago, data_vencimento, tamanho, status, seguro_id) VALUES ('04-06-2018', '19-08-2018', '1/2', 'pago', 5);
INSERT INTO parcelas(data_pago, data_vencimento, tamanho, status, seguro_id) VALUES (null, '19-09-2018', '2/2', 'aberto', 5);

INSERT INTO seguros(data_seg, n_parcelas, valor, email_id, tipo_id, usuario_id, veiculo_id, parcelas_pagas, vencimento, situacao) VALUES ('12-02-2019', 1, 5000, 1, 2, 1, 1, 1, '12-02-2020', 'terminado');
INSERT INTO parcelas(data_pago, data_vencimento, tamanho, status, seguro_id) VALUES ('10-03-2019', '12-03-2019', '1/1', 'pago', 6);

INSERT INTO seguros(data_seg, n_parcelas, valor, email_id, tipo_id, usuario_id, veiculo_id, parcelas_pagas, vencimento) VALUES ('01-04-2018', 3, 5000, 1, 2, 2, 1, 2, '01-04-2019');
INSERT INTO parcelas(data_pago, data_vencimento, tamanho, status, seguro_id) VALUES ('01-05-2018', '01-05-2018', '1/3', 'pago', 7);
INSERT INTO parcelas(data_pago, data_vencimento, tamanho, status, seguro_id) VALUES ('01-06-2018', '01-06-2018', '2/3', 'pago', 7);
INSERT INTO parcelas(data_pago, data_vencimento, tamanho, status, seguro_id) VALUES (null, '01-07-2018', '3/3', 'aberto', 7);


