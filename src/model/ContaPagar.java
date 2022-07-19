/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author vicbaptista
 */
@Entity
@Table(name = "conta_pagar")
@NamedQueries({
    @NamedQuery(name = "ContaPagar.findAll", query = "SELECT c FROM ContaPagar c")})
public class ContaPagar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_conta_pagar")
    private Integer idContaPagar;
    @Basic(optional = false)
    @Column(name = "data_vencimento")
    private String dataVencimento;
    @Column(name = "data_pagamento")
    private String dataPagamento;
    @Basic(optional = false)
    @Column(name = "pagamento")
    private String pagamento;
    @Basic(optional = false)
    @Column(name = "vencida")
    private String vencida;
    @JoinColumn(name = "compra_id_compra", referencedColumnName = "id_compra")
    @ManyToOne(optional = false)
    private Compra compraIdCompra;

    public ContaPagar() {
    }

    public ContaPagar(Integer idContaPagar) {
        this.idContaPagar = idContaPagar;
    }

    public ContaPagar(Integer idContaPagar, String dataVencimento, String pagamento, String vencida) {
        this.idContaPagar = idContaPagar;
        this.dataVencimento = dataVencimento;
        this.pagamento = pagamento;
        this.vencida = vencida;
    }

    public Integer getIdContaPagar() {
        return idContaPagar;
    }

    public void setIdContaPagar(Integer idContaPagar) {
        this.idContaPagar = idContaPagar;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public String getVencida() {
        return vencida;
    }

    public void setVencida(String vencida) {
        this.vencida = vencida;
    }

    public Compra getCompraIdCompra() {
        return compraIdCompra;
    }

    public void setCompraIdCompra(Compra compraIdCompra) {
        this.compraIdCompra = compraIdCompra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idContaPagar != null ? idContaPagar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContaPagar)) {
            return false;
        }
        ContaPagar other = (ContaPagar) object;
        if ((this.idContaPagar == null && other.idContaPagar != null) || (this.idContaPagar != null && !this.idContaPagar.equals(other.idContaPagar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ContaPagar[ idContaPagar=" + idContaPagar + " ]";
    }
    
}
