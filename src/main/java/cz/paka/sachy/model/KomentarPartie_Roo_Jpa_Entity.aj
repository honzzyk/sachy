// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package cz.paka.sachy.model;

import cz.paka.sachy.model.KomentarPartie;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect KomentarPartie_Roo_Jpa_Entity {
    
    declare @type: KomentarPartie: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long KomentarPartie.id;
    
    @Version
    @Column(name = "version")
    private Integer KomentarPartie.version;
    
    public Long KomentarPartie.getId() {
        return this.id;
    }
    
    public void KomentarPartie.setId(Long id) {
        this.id = id;
    }
    
    public Integer KomentarPartie.getVersion() {
        return this.version;
    }
    
    public void KomentarPartie.setVersion(Integer version) {
        this.version = version;
    }
    
}