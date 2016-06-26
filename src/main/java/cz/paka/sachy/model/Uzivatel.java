package cz.paka.sachy.model;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import cz.paka.sachy.utils.Utils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.validation.constraints.NotNull;

/**
 * Trida zastupujici uzivatele - muze se prihlasit
 * 
 * @author Jan Novotny
 *
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Uzivatel {

	@Column(unique = true)
	@NotNull
	private String jmeno;

	@NotNull
	private String heslo;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "autor")
	private Set<Novinka> novinky = new HashSet<Novinka>();

	@Column(columnDefinition = "BIT")
	private Boolean enabled;

	@OneToMany(mappedBy = "uzivatel")
	private List<RoleUzivatele> roleUzivatele;

	/**
	 * vraci zda je uzivatel admin
	 */
	public boolean isAdmin() {
		for (RoleUzivatele role : roleUzivatele) {
			if (role.getAuthority().equals("ROLE_ADMIN")) {
				return true;
			}
		}
		return false;
	}

	/**
	 * najde uzivatele dle jmena
	 */
	public static Uzivatel findUzivatelByName(String name) {
		if (name == null || name.isEmpty()) {
			return null;
		}
		Query q = entityManager().createQuery("select u from Uzivatel u where u.jmeno=?1");
		q.setParameter(1, name);

		List<Uzivatel> u = q.getResultList();
		return (u.size() != 0) ? u.get(0) : null;
	}

	/**
	 * k nove vytvorenemu uzivateli vytvori i jeho roli a ulozi do DB
	 */
	public static void createUserRoleAndSave(Uzivatel uzivatel) {
		uzivatel.setEnabled(true);
		uzivatel.setHeslo(Utils.sha256(uzivatel.getHeslo()));
		uzivatel.persist();

		RoleUzivatele role = new RoleUzivatele();
		role.setAuthority("ROLE_DEFAULT");
		role.setUzivatel(uzivatel);
		role.persist();
	}
}
