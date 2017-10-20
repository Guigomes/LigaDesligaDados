package br.com.ggsoftware.ligadesligadados.util;


import br.com.ggsoftware.ligadesligadados.R;

public enum FrequenciaEnum {
	UMA_VEZ(1, R.string.uma_vez),
	DIARIAMENTE(2, R.string.diariamente);
	

	private Integer codigo;
	private Integer codigoTexto;

	
	private FrequenciaEnum(Integer codigo, Integer texto) {
		this.setCodigo(codigo);
		this.setCodigoTexto(texto);
	}

	public Integer getCodigoTexto() {
		return codigoTexto;
	}

	public void setCodigoTexto(Integer codigoTexto) {
		this.codigoTexto = codigoTexto;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
	public static Integer getTextoByCodigo(Integer codigo){
		
		FrequenciaEnum[] values = FrequenciaEnum.values();
		for (FrequenciaEnum FrequenciaEnum : values) {
			if(FrequenciaEnum.getCodigo().equals(codigo)){
				return FrequenciaEnum.getCodigoTexto();
			}
		}
		
		return 0;
		
	}
}

