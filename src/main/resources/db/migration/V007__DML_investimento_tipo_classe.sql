
insert into investimento_classe(nome, classe) values ('Renda Fixa', 'RF');
insert into investimento_classe(nome, classe) values ('Renda Variável', 'RV');

insert into investimento_tipo(nome, tipo, classe_id) 
	select 'Tesouro Direto', 'TD', id from investimento_classe where classe = 'RF';
	
insert into investimento_tipo(nome, tipo, classe_id) 
	select 'CDB', 'CDB', id from investimento_classe where classe = 'RF';

insert into investimento_tipo(nome, tipo, classe_id) 
	select 'LCI', 'LCI', id from investimento_classe where classe = 'RF';

insert into investimento_tipo(nome, tipo, classe_id)
	select 'LCA', 'LCA', id from investimento_classe where classe = 'RF';

insert into investimento_tipo(nome, tipo, classe_id)
	select 'Fundos de Investimento', 'FI', id from investimento_classe where classe = 'RV';

insert into investimento_tipo(nome, tipo, classe_id) 
	select 'Fundos Imobiliários', 'FII', id from investimento_classe where classe = 'RV';

insert into investimento_tipo(nome, tipo, classe_id)
	select 'Ações', 'AC', id from investimento_classe where classe = 'RV';

insert into investimento_tipo(nome, tipo, classe_id)
	select 'Criptomoedas', 'CRPT', id from investimento_classe where classe = 'RV';

