package com.transportadora.model.auth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.transportadora.enums.Status;
import com.transportadora.enums.converters.StatusConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "Pedidos")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @NotBlank
    @Length(min = 5, max = 100)
    @Column(length = 100, nullable = false)
    private String nomePedido;

    @Column(length = 100, nullable = false)
    private String razaoSocial;

    @Column(length = 20, nullable = false)
    private String cpfcnpjPedido;

    @Column(nullable = false)
    private String tipoPgto;

    @Column(nullable = false)
    private String cepPedido;

    @Column(nullable = false)
    private String logradouroPedido;

    @Column(nullable = false)
    private String numeroPedido;

    @Column(nullable = false)
    private String complementoPedido;

    @Column(nullable = false)
    private String bairroPedido;

    @Column(nullable = false)
    private String cidadePedido;

    @Column(nullable = false)
    private String estadoPedido;

    @Column(nullable = false)
    private String sfobras;

    @Column(nullable = false)
    private String cno;

    @Column(nullable = false)
    private String ie;

    @Column(length = 15, nullable = false)
    private String mangueira;

    @Column(length = 10, nullable = false)
    private String volume;

    @Column(length = 10, nullable = false)
    private String precoCx5;

    @Column(length = 10, nullable = false)
    private String precoCx10;

    @Column(length = 10, nullable = false)
    private String precoCx15;

    @Column(length = 10, nullable = false)
    private String precoLv5;

    @Column(length = 10, nullable = false)
    private String precoLv10;

    @Column(length = 10, nullable = false)
    private String precoLv15;

    @Column(nullable = false)
    private String ajudanteHora;

    @Column(nullable = false)
    private String observacao;

    @NotNull
    @Column(length = 15, nullable = false)
    @Convert(converter = StatusConverter.class)
    private Status status = Status.EMITIDO;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "cliente_id", nullable = false)
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    private Cliente cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomePedido() {
        return nomePedido;
    }

    public void setNomePedido(String nomePedido) {
        this.nomePedido = nomePedido;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCpfcnpjPedido() {
        return cpfcnpjPedido;
    }

    public void setCpfcnpjPedido(String cpfcnpjPedido) {
        this.cpfcnpjPedido = cpfcnpjPedido;
    }

    public String getTipoPgto() {
        return tipoPgto;
    }

    public void setTipoPgto(String tipoPgto) {
        this.tipoPgto = tipoPgto;
    }

    public String getCepPedido() {
        return cepPedido;
    }

    public void setCepPedido(String cepPedido) {
        this.cepPedido = cepPedido;
    }

    public String getLogradouroPedido() {
        return logradouroPedido;
    }

    public void setLogradouroPedido(String logradouroPedido) {
        this.logradouroPedido = logradouroPedido;
    }

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getComplementoPedido() {
        return complementoPedido;
    }

    public void setComplementoPedido(String complementoPedido) {
        this.complementoPedido = complementoPedido;
    }

    public String getBairroPedido() {
        return bairroPedido;
    }

    public void setBairroPedido(String bairroPedido) {
        this.bairroPedido = bairroPedido;
    }

    public String getCidadePedido() {
        return cidadePedido;
    }

    public void setCidadePedido(String cidadePedido) {
        this.cidadePedido = cidadePedido;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public String getSfobras() {
        return sfobras;
    }

    public void setSfobras(String sfobras) {
        this.sfobras = sfobras;
    }

    public String getCno() {
        return cno;
    }

    public void setCno(String cno) {
        this.cno = cno;
    }

    public String getIe() {
        return ie;
    }

    public void setIe(String ie) {
        this.ie = ie;
    }

    public String getMangueira() {
        return mangueira;
    }

    public void setMangueira(String mangueira) {
        this.mangueira = mangueira;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getPrecoCx5() {
        return precoCx5;
    }

    public void setPrecoCx5(String precoCx5) {
        this.precoCx5 = precoCx5;
    }

    public String getPrecoCx10() {
        return precoCx10;
    }

    public void setPrecoCx10(String precoCx10) {
        this.precoCx10 = precoCx10;
    }

    public String getPrecoCx15() {
        return precoCx15;
    }

    public void setPrecoCx15(String precoCx15) {
        this.precoCx15 = precoCx15;
    }

    public String getPrecoLv5() {
        return precoLv5;
    }

    public void setPrecoLv5(String precoLv5) {
        this.precoLv5 = precoLv5;
    }

    public String getPrecoLv10() {
        return precoLv10;
    }

    public void setPrecoLv10(String precoLv10) {
        this.precoLv10 = precoLv10;
    }

    public String getPrecoLv15() {
        return precoLv15;
    }

    public void setPrecoLv15(String precoLv15) {
        this.precoLv15 = precoLv15;
    }

    public String getAjudanteHora() {
        return ajudanteHora;
    }

    public void setAjudanteHora(String ajudanteHora) {
        this.ajudanteHora = ajudanteHora;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

//    public Cliente getCliente() {
//        return cliente;
//    }
//
//    public void setCliente(Cliente cliente) {
//        this.cliente = cliente;
//    }

}
