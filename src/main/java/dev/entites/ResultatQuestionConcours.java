package dev.entites;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "resultat_question_concours")
public class ResultatQuestionConcours extends BaseEntite {

	@ManyToOne
	@JoinColumn(name = "id_passage")
	private PassageConcours passageConcours;
	
	@ManyToOne
	@JoinColumn(name = "id_question")
	private Question question;
	
	@ManyToOne
	@JoinColumn(name = "id_option_reponse")
	private OptionQuestion reponseOptionQuestion;

	public PassageConcours getPassageConcours() {
		return passageConcours;
	}

	public void setPassageConcours(PassageConcours passageConcours) {
		this.passageConcours = passageConcours;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public OptionQuestion getReponseOptionQuestion() {
		return reponseOptionQuestion;
	}

	public void setReponseOptionQuestion(OptionQuestion reponseOptionQuestion) {
		this.reponseOptionQuestion = reponseOptionQuestion;
	}
	
	
	
}
