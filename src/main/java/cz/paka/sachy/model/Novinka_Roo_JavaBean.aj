// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package cz.paka.sachy.model;

import cz.paka.sachy.model.Novinka;
import cz.paka.sachy.model.Uzivatel;
import java.util.Date;

privileged aspect Novinka_Roo_JavaBean {
    
    public String Novinka.getText() {
        return this.text;
    }
    
    public void Novinka.setText(String text) {
        this.text = text;
    }
    
    public Date Novinka.getDatum() {
        return this.datum;
    }
    
    public void Novinka.setDatum(Date datum) {
        this.datum = datum;
    }
    
    public Uzivatel Novinka.getAutor() {
        return this.autor;
    }
    
    public void Novinka.setAutor(Uzivatel autor) {
        this.autor = autor;
    }
    
    public String Novinka.getNadpis() {
        return this.nadpis;
    }
    
    public void Novinka.setNadpis(String nadpis) {
        this.nadpis = nadpis;
    }
    
}
