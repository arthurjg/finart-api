create table public.investimento_tipo (
	id bigserial primary key,
	nome varchar(100) not null,
	tipo varchar(4) not null UNIQUE,
	classe_id int4 not null		
);

ALTER TABLE public.investimento ADD CONSTRAINT fk_investimento_tipo FOREIGN KEY (tipo_id) REFERENCES public.investimento_tipo(id);
