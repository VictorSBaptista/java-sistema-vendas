/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author vicbaptista
 */
@Embeddable
public class ItemCompraPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_item_compra")
    private int idItemCompra;
    @Basic(optional = false)
    @Column(name = "compra_id_compra")
    private int compraIdCompra;

    public ItemCompraPK() {
    }

    public ItemCompraPK(int idItemCompra, int compraIdCompra) {
        this.idItemCompra = idItemCompra;
        this.compraIdCompra = compraIdCompra;
    }

    public int getIdItemCompra() {
        return idItemCompra;
    }

    public void setIdItemCompra(int idItemCompra) {
        this.idItemCompra = idItemCompra;
    }

    public int getCompraIdCompra() {
        return compraIdCompra;
    }

    public void setCompraIdCompra(int compraIdCompra) {
        this.compraIdCompra = compraIdCompra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idItemCompra;
        hash += (int) compraIdCompra;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemCompraPK)) {
            return false;
        }
        ItemCompraPK other = (ItemCompraPK) object;
        if (this.idItemCompra != other.idItemCompra) {
            return false;
        }
        if (this.compraIdCompra != other.compraIdCompra) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ItemCompraPK[ idItemCompra=" + idItemCompra + ", compraIdCompra=" + compraIdCompra + " ]";
    }
    
}
