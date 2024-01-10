create table public.investimento_classe (
	id bigserial primary key,
	nome varchar(100) not null,	
	classe varchar(4) not null UNIQUE		
);

ALTER TABLE public.investimento_tipo ADD CONSTRAINT fk_investimento_classe FOREIGN KEY (classe_id) REFERENCES public.investimento_classe(id);
