package com.gustavo.backend.model.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.gustavo.backend.model.entity.enums.StatusLancamento;
import com.gustavo.backend.model.entity.enums.TipoLancamento;

@Entity
@Table(name = "lancamento", schema = "financas")
public class Lancamento {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "mes")
	private Integer mes;

	@Column(name = "ano")
	private Integer ano;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@Column(name = "valor")
	private BigDecimal valor;

	@Column(name = "data_cadastro")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	private LocalDate data_cadastro;

	@Column(name = "tipo")
	@Enumerated(value = EnumType.STRING)
	private TipoLancamento tipo;

	@Column(name = "status")
	@Enumerated(value = EnumType.STRING)
	private StatusLancamento statusLancamento;

	public Lancamento() {
	}

	public Lancamento(Long id, String descricao, Integer mes, Integer ano, Usuario usuario, BigDecimal valor,
			LocalDate data_cadastro, TipoLancamento tipo, StatusLancamento statusLancamento) {
		this.id = id;
		this.descricao = descricao;
		this.mes = mes;
		this.ano = ano;
		this.usuario = usuario;
		this.valor = valor;
		this.data_cadastro = data_cadastro;
		this.tipo = tipo;
		this.statusLancamento = statusLancamento;
	}

	public Lancamento(String descricao, Integer mes, Integer ano, Usuario usuario, BigDecimal valor,
			LocalDate data_cadastro, TipoLancamento tipo, StatusLancamento statusLancamento) {
		this.descricao = descricao;
		this.mes = mes;
		this.ano = ano;
		this.usuario = usuario;
		this.valor = valor;
		this.data_cadastro = data_cadastro;
		this.tipo = tipo;
		this.statusLancamento = statusLancamento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public LocalDate getData_cadastro() {
		return data_cadastro;
	}

	public void setData_cadastro(LocalDate data_cadastro) {
		this.data_cadastro = data_cadastro;
	}

	public TipoLancamento getTipo() {
		return tipo;
	}

	public void setTipo(TipoLancamento tipo) {
		this.tipo = tipo;
	}

	public StatusLancamento getStatusLancamento() {
		return statusLancamento;
	}

	public void setStatusLancamento(StatusLancamento statusLancamento) {
		this.statusLancamento = statusLancamento;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ano, data_cadastro, descricao, id, mes, statusLancamento, tipo, usuario, valor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lancamento other = (Lancamento) obj;
		return Objects.equals(ano, other.ano) && Objects.equals(data_cadastro, other.data_cadastro)
				&& Objects.equals(descricao, other.descricao) && Objects.equals(id, other.id)
				&& Objects.equals(mes, other.mes) && statusLancamento == other.statusLancamento && tipo == other.tipo
				&& Objects.equals(usuario, other.usuario) && Objects.equals(valor, other.valor);
	}

	@Override
	public String toString() {
		return "Lancamento [id=" + id + ", descricao=" + descricao + ", mes=" + mes + ", ano=" + ano + ", usuario="
				+ usuario + ", valor=" + valor + ", data_cadastro=" + data_cadastro + ", tipo=" + tipo
				+ ", statusLancamento=" + statusLancamento + "]";
	}

}
