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
public class ItemVendaPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "id_item_venda")
    private int idItemVenda;
    @Basic(optional = false)
    @Column(name = "venda_id_venda")
    private int vendaIdVenda;

    public ItemVendaPK() {
    }

    public ItemVendaPK(int idItemVenda, int vendaIdVenda) {
        this.idItemVenda = idItemVenda;
        this.vendaIdVenda = vendaIdVenda;
    }

    public int getIdItemVenda() {
        return idItemVenda;
    }

    public void setIdItemVenda(int idItemVenda) {
        this.idItemVenda = idItemVenda;
    }

    public int getVendaIdVenda() {
        return vendaIdVenda;
    }

    public void setVendaIdVenda(int vendaIdVenda) {
        this.vendaIdVenda = vendaIdVenda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idItemVenda;
        hash += (int) vendaIdVenda;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ItemVendaPK)) {
            return false;
        }
        ItemVendaPK other = (ItemVendaPK) object;
        if (this.idItemVenda != other.idItemVenda) {
            return false;
        }
        if (this.vendaIdVenda != other.vendaIdVenda) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.ItemVendaPK[ idItemVenda=" + idItemVenda + ", vendaIdVenda=" + vendaIdVenda + " ]";
    }
    
}
