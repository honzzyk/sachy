// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package cz.paka.sachy.model;

import cz.paka.sachy.model.Prispevek;
import java.util.Date;

privileged aspect Prispevek_Roo_JavaBean {
    
    public String Prispevek.getText() {
        return this.text;
    }
    
    public void Prispevek.setText(String text) {
        this.text = text;
    }
    
    public String Prispevek.getAutor() {
        return this.autor;
    }
    
    public void Prispevek.setAutor(String autor) {
        this.autor = autor;
    }
    
    public String Prispevek.getIp() {
        return this.ip;
    }
    
    public void Prispevek.setIp(String ip) {
        this.ip = ip;
    }
    
    public Date Prispevek.getDatum() {
        return this.datum;
    }
    
    public void Prispevek.setDatum(Date datum) {
        this.datum = datum;
    }
    
}
