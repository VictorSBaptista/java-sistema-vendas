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
@Table(name = "item_compra")
@NamedQueries({
    @NamedQuery(name = "ItemCompra.findAll", query = "SELECT i FROM ItemCompra i")})
public class ItemCompra implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ItemCompraPK itemCompraPK;
    @Basic(optional = false)
    @Column(name = "quantidade")
    private int quantidade;
    @Basic(optional = false)
    @Column(name = "valor")
    private double valor;
    @JoinColumn(name = "compra_id_compra", referencedColumnName = "id_compra", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Compra compra;
    @JoinColumn(name = "produto_id_produto", referencedColumnName = "id_produto")
    @ManyToOne(optional = false)
    private Produto produtoIdProduto;

    public ItemCompra() {
    }

    public ItemCompra(ItemCompraPK itemCompraPK) {
        this.itemCompraPK = itemCompraPK;
    }

    public ItemCompra(ItemCompraPK itemCompraPK, int quantidade, double valor) {
        this.itemCompraPK = itemCompraPK;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public ItemCompra(int idItemCompra, int compraIdCompra) {
        this.itemCompraPK = new ItemCompraPK(idItemCompra, compraIdCompra);
    }

    public ItemCompraPK getItemCompraPK() {
        return itemCompraPK;
    }

    public void setItemCompraPK(ItemCompraPK itemCompraPK) {
        this.itemCompraPK = itemCompraPK;
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

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Produto getProdutoIdProduto() {
        return produtoIdProduto;
    }

    public void setProdutoIdProduto(Produto produtoIdProduto) {
        this.produtoIdProduto = produtoIdProduto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itemCompraPK != null ? itemCompraPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemCompra)) {
            return false;
        }
        ItemCompra other = (ItemCompra) object;
        if ((this.itemCompraPK == null && other.itemCompraPK != null) || (this.itemCompraPK != null && !this.itemCompraPK.equals(other.itemCompraPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ItemCompra[ itemCompraPK=" + itemCompraPK + " ]";
    }
    
}
