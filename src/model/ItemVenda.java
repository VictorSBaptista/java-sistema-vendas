/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "item_venda")
@NamedQueries({
    @NamedQuery(name = "ItemVenda.findAll", query = "SELECT i FROM ItemVenda i")})
public class ItemVenda implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ItemVendaPK itemVendaPK;
    @Basic(optional = false)
    @Column(name = "quantidade")
    private int quantidade;
    @Basic(optional = false)
    @Column(name = "valor")
    private double valor;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "desconto")
    private Double desconto;
    @JoinColumn(name = "produto_id_produto", referencedColumnName = "id_produto")
    @ManyToOne(optional = false)
    private Produto produtoIdProduto;
    @JoinColumn(name = "venda_id_venda", referencedColumnName = "id_venda", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Venda venda;

    public ItemVenda() {
    }

    public ItemVenda(ItemVendaPK itemVendaPK) {
        this.itemVendaPK = itemVendaPK;
    }

    public ItemVenda(ItemVendaPK itemVendaPK, int quantidade, double valor) {
        this.itemVendaPK = itemVendaPK;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public ItemVenda(int idItemVenda, int vendaIdVenda) {
        this.itemVendaPK = new ItemVendaPK(idItemVenda, vendaIdVenda);
    }

    public ItemVendaPK getItemVendaPK() {
        return itemVendaPK;
    }

    public void setItemVendaPK(ItemVendaPK itemVendaPK) {
        this.itemVendaPK = itemVendaPK;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public Double getDesconto() {
        return desconto;
    }

    public void setDesconto(Double desconto) {
        this.desconto = desconto;
    }

    public Produto getProdutoIdProduto() {
        return produtoIdProduto;
    }

    public void setProdutoIdProduto(Produto produtoIdProduto) {
        this.produtoIdProduto = produtoIdProduto;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemVendaPK != null ? itemVendaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemVenda)) {
            return false;
        }
        ItemVenda other = (ItemVenda) object;
        if ((this.itemVendaPK == null && other.itemVendaPK != null) || (this.itemVendaPK != null && !this.itemVendaPK.equals(other.itemVendaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ItemVenda[ itemVendaPK=" + itemVendaPK + " ]";
    }
    
}
