--create or replace function validaChavePessoaFornecedor() 
--	returns trigger
--	language plpgsql
--	as $$
	
--	declare existe integer;

-- begin 
	
--	existe = (select count(1) from pessoa_fisica where id = NEW.pessoa_fornecedor_id);
--	if (existe <= 0) then
--	existe = (select count(1) from pessoa_juridica where id = NEW.pessoa_fornecedor_id);
--	if (existe <= 0) then
--		raise exception 'Não foi encontrado nehum id ou fk para pessoa para associção';
--		end if;
--	end if;
--	return new;
--end;
--$$