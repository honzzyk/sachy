package cz.paka.sachy.model;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import javax.persistence.ManyToOne;

/**
 * Role uzivatele
 * 
 * @author Jan Novotny
 *
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class RoleUzivatele {

	@ManyToOne
	private Uzivatel uzivatel;

	private String authority;

}
