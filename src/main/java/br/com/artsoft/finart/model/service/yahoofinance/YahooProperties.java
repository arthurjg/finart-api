package br.com.artsoft.finart.model.service.yahoofinance;

public class YahooProperties {
	
	public static final char		ORIGEM_BOVESPA										= 'B';
	public static final char		ORIGEM_MUNDO											= 'M';

	public static final String	LOCAL_BOVESPA											= "br";
	public static final String	LOCAL_MUNDO												= "download";
	public static final String	POSFIXO_ACAO_BOVESPA							= ".SA";
	public static final String	SEPARADOR_BOVESPA									= ";";
	public static final String	SEPARADOR_MUNDO										= ",";
	public static final String	INDICE_BOVESPA										= "^BVSP";
	public static final int			SIGLA_ACAO_INDICE									= 0;
	public static final int			ULTIMO_PRECO_DIA_ACAO_INDICE			= 1;
	public static final int			DATA_NEGOCIACAO_ACAO_INDICE				= 2;
	public static final int			HORA_NEGOCIACAO_ACAO_INDICE				= 3;
	public static final int			VARIACAO_DIA_ACAO_INDICE					= 4;
	public static final int			PRECO_ABERTURA_DIA_ACAO_INDICE		= 5;
	public static final int			MAIOR_PRECO_DIA_ACAO_INDICE				= 6;
	public static final int			MENOR_PRECO_DIA_ACAO_INDICE				= 7;
	public static final int			VOLUME_NEGOCIADO_DIA_ACAO_INDICE	= 8;

}
