package cz.paka.sachy.model;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import cz.paka.sachy.utils.Strankovani;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

/**
 * Trida reprezentujici novinky na uvodni strance
 * 
 * @author Jan Novotny
 *
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Novinka {

	@Size(min = 1, message = "Zadejte text!")
	@Column(columnDefinition = "TEXT")
	private String text;

	private Date datum;

	@ManyToOne
	private Uzivatel autor;

	@Size(min = 1, max = 50, message = "Délka musí být 1 až 50 písmen!")
	private String nadpis;
	

	public static List<Novinka> findAllOrderedByDateByPage(int page) {
		int firstResult = Strankovani.POCET_NOVINEK_NA_STRANKU * page - Strankovani.POCET_NOVINEK_NA_STRANKU;
		String query = "SELECT n FROM Novinka n ORDER BY n.datum DESC";
		return entityManager().createQuery(query, Novinka.class)
				.setFirstResult(firstResult)
				.setMaxResults(Strankovani.POCET_NOVINEK_NA_STRANKU) 
				.getResultList();
	}
}
