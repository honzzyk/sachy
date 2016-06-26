package cz.paka.sachy.model;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import java.util.Date;
import javax.persistence.Column;
import javax.validation.constraints.Size;
import javax.persistence.ManyToOne;

/**
 * Komentar v diskuzi k partii
 * 
 * @author Jan Novotny
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class KomentarPartie {

	@Column(columnDefinition = "TEXT")
	@Size(min = 1, message = "Zadejte text!")
	private String text;

	@Size(min = 1, max = 25, message = "Délka musí být 1 až 25 písmen!")
	private String autor;

	/**
	 * IP adresa uzivatele
	 */
	private String ip;

	private Date datum;

	@ManyToOne
	private Partie partie;
}
