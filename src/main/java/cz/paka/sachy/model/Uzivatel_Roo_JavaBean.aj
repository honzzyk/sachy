// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package cz.paka.sachy.model;

import cz.paka.sachy.model.Novinka;
import cz.paka.sachy.model.RoleUzivatele;
import cz.paka.sachy.model.Uzivatel;
import java.util.List;
import java.util.Set;

privileged aspect Uzivatel_Roo_JavaBean {
    
    public String Uzivatel.getJmeno() {
        return this.jmeno;
    }
    
    public void Uzivatel.setJmeno(String jmeno) {
        this.jmeno = jmeno;
    }
    
    public String Uzivatel.getHeslo() {
        return this.heslo;
    }
    
    public void Uzivatel.setHeslo(String heslo) {
        this.heslo = heslo;
    }
    
    public Set<Novinka> Uzivatel.getNovinky() {
        return this.novinky;
    }
    
    public void Uzivatel.setNovinky(Set<Novinka> novinky) {
        this.novinky = novinky;
    }
    
    public Boolean Uzivatel.getEnabled() {
        return this.enabled;
    }
    
    public void Uzivatel.setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    
    public List<RoleUzivatele> Uzivatel.getRoleUzivatele() {
        return this.roleUzivatele;
    }
    
    public void Uzivatel.setRoleUzivatele(List<RoleUzivatele> roleUzivatele) {
        this.roleUzivatele = roleUzivatele;
    }
    
}
