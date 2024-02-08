create table public.investimento_movimento (
	id bigserial primary key,
	tipo varchar(2) not null,	
	valor numeric not null,
	data timestamp not null,
	investimento_id int4 not null
);

ALTER TABLE public.investimento_movimento ADD CONSTRAINT fk_investimento FOREIGN KEY (investimento_id) REFERENCES public.investimento(id) ON DELETE CASCADE;
