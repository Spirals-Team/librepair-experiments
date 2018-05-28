package dev.paie.entite;

import fr.inria.spirals.npefix.resi.CallChecker;
import fr.inria.spirals.npefix.resi.context.MethodContext;
import fr.inria.spirals.npefix.resi.exception.AbnormalExecutionError;
import fr.inria.spirals.npefix.resi.exception.ForceReturn;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Avantage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_avantage", unique = true, nullable = false)
    private Integer id;

    @Column(name = "CODE", nullable = false)
    private String code;

    @Column(name = "NOM", nullable = false)
    private String nom;

    @Column(name = "MONTANT", nullable = false)
    private BigDecimal montant;

    public String getCode() {
        MethodContext _bcornu_methode_context1 = new MethodContext(String.class, 28, 615, 657);
        try {
            CallChecker.varInit(this, "this", 28, 615, 657);
            CallChecker.varInit(this.montant, "montant", 28, 615, 657);
            CallChecker.varInit(this.nom, "nom", 28, 615, 657);
            CallChecker.varInit(this.code, "code", 28, 615, 657);
            CallChecker.varInit(this.id, "id", 28, 615, 657);
            return code;
        } catch (ForceReturn _bcornu_return_t) {
            return ((String) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context1.methodEnd();
        }
    }

    public void setCode(String code) {
        MethodContext _bcornu_methode_context2 = new MethodContext(void.class, 32, 661, 717);
        try {
            CallChecker.varInit(this, "this", 32, 661, 717);
            CallChecker.varInit(code, "code", 32, 661, 717);
            CallChecker.varInit(this.montant, "montant", 32, 661, 717);
            CallChecker.varInit(this.nom, "nom", 32, 661, 717);
            CallChecker.varInit(this.code, "code", 32, 661, 717);
            CallChecker.varInit(this.id, "id", 32, 661, 717);
            this.code = code;
            CallChecker.varAssign(this.code, "this.code", 33, 698, 714);
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context2.methodEnd();
        }
    }

    public String getNom() {
        MethodContext _bcornu_methode_context3 = new MethodContext(String.class, 36, 721, 761);
        try {
            CallChecker.varInit(this, "this", 36, 721, 761);
            CallChecker.varInit(this.montant, "montant", 36, 721, 761);
            CallChecker.varInit(this.nom, "nom", 36, 721, 761);
            CallChecker.varInit(this.code, "code", 36, 721, 761);
            CallChecker.varInit(this.id, "id", 36, 721, 761);
            return nom;
        } catch (ForceReturn _bcornu_return_t) {
            return ((String) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context3.methodEnd();
        }
    }

    public void setNom(String nom) {
        MethodContext _bcornu_methode_context4 = new MethodContext(void.class, 40, 765, 817);
        try {
            CallChecker.varInit(this, "this", 40, 765, 817);
            CallChecker.varInit(nom, "nom", 40, 765, 817);
            CallChecker.varInit(this.montant, "montant", 40, 765, 817);
            CallChecker.varInit(this.nom, "nom", 40, 765, 817);
            CallChecker.varInit(this.code, "code", 40, 765, 817);
            CallChecker.varInit(this.id, "id", 40, 765, 817);
            this.nom = nom;
            CallChecker.varAssign(this.nom, "this.nom", 41, 800, 814);
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context4.methodEnd();
        }
    }

    public BigDecimal getMontant() {
        MethodContext _bcornu_methode_context5 = new MethodContext(BigDecimal.class, 44, 821, 873);
        try {
            CallChecker.varInit(this, "this", 44, 821, 873);
            CallChecker.varInit(this.montant, "montant", 44, 821, 873);
            CallChecker.varInit(this.nom, "nom", 44, 821, 873);
            CallChecker.varInit(this.code, "code", 44, 821, 873);
            CallChecker.varInit(this.id, "id", 44, 821, 873);
            return montant;
        } catch (ForceReturn _bcornu_return_t) {
            return ((BigDecimal) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context5.methodEnd();
        }
    }

    public void setMontant(BigDecimal montant) {
        MethodContext _bcornu_methode_context6 = new MethodContext(void.class, 48, 877, 949);
        try {
            CallChecker.varInit(this, "this", 48, 877, 949);
            CallChecker.varInit(montant, "montant", 48, 877, 949);
            CallChecker.varInit(this.montant, "montant", 48, 877, 949);
            CallChecker.varInit(this.nom, "nom", 48, 877, 949);
            CallChecker.varInit(this.code, "code", 48, 877, 949);
            CallChecker.varInit(this.id, "id", 48, 877, 949);
            this.montant = montant;
            CallChecker.varAssign(this.montant, "this.montant", 49, 924, 946);
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context6.methodEnd();
        }
    }

    public Integer getId() {
        MethodContext _bcornu_methode_context7 = new MethodContext(Integer.class, 52, 953, 992);
        try {
            CallChecker.varInit(this, "this", 52, 953, 992);
            CallChecker.varInit(this.montant, "montant", 52, 953, 992);
            CallChecker.varInit(this.nom, "nom", 52, 953, 992);
            CallChecker.varInit(this.code, "code", 52, 953, 992);
            CallChecker.varInit(this.id, "id", 52, 953, 992);
            return id;
        } catch (ForceReturn _bcornu_return_t) {
            return ((Integer) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context7.methodEnd();
        }
    }

    public void setId(Integer id) {
        MethodContext _bcornu_methode_context8 = new MethodContext(void.class, 56, 996, 1045);
        try {
            CallChecker.varInit(this, "this", 56, 996, 1045);
            CallChecker.varInit(id, "id", 56, 996, 1045);
            CallChecker.varInit(this.montant, "montant", 56, 996, 1045);
            CallChecker.varInit(this.nom, "nom", 56, 996, 1045);
            CallChecker.varInit(this.code, "code", 56, 996, 1045);
            CallChecker.varInit(this.id, "id", 56, 996, 1045);
            this.id = id;
            CallChecker.varAssign(this.id, "this.id", 57, 1030, 1042);
        } catch (ForceReturn _bcornu_return_t) {
            _bcornu_return_t.getDecision().getValue();
            return ;
        } finally {
            _bcornu_methode_context8.methodEnd();
        }
    }

    public boolean equals(Avantage av) {
        MethodContext _bcornu_methode_context9 = new MethodContext(boolean.class, 60, 1049, 1212);
        try {
            CallChecker.varInit(this, "this", 60, 1049, 1212);
            CallChecker.varInit(av, "av", 60, 1049, 1212);
            CallChecker.varInit(this.montant, "montant", 60, 1049, 1212);
            CallChecker.varInit(this.nom, "nom", 60, 1049, 1212);
            CallChecker.varInit(this.code, "code", 60, 1049, 1212);
            CallChecker.varInit(this.id, "id", 60, 1049, 1212);
            if (CallChecker.beforeDeref(av, Avantage.class, 61, 1112, 1113)) {
                if (CallChecker.beforeDeref(av, Avantage.class, 61, 1145, 1146)) {
                    if (CallChecker.beforeDeref(av, Avantage.class, 62, 1188, 1189)) {
                        av = CallChecker.beforeCalled(av, Avantage.class, 61, 1112, 1113);
                        this.code = CallChecker.beforeCalled(this.code, String.class, 61, 1095, 1103);
                        av = CallChecker.beforeCalled(av, Avantage.class, 61, 1145, 1146);
                        this.nom = CallChecker.beforeCalled(this.nom, String.class, 61, 1129, 1136);
                        av = CallChecker.beforeCalled(av, Avantage.class, 62, 1188, 1189);
                        this.montant = CallChecker.beforeCalled(this.montant, BigDecimal.class, 62, 1165, 1176);
                        return ((CallChecker.isCalled(this.code, String.class, 61, 1095, 1103).equals(CallChecker.isCalled(av, Avantage.class, 61, 1112, 1113).getCode())) && (CallChecker.isCalled(this.nom, String.class, 61, 1129, 1136).equals(CallChecker.isCalled(av, Avantage.class, 61, 1145, 1146).getNom()))) && ((CallChecker.isCalled(this.montant, BigDecimal.class, 62, 1165, 1176).compareTo(CallChecker.isCalled(av, Avantage.class, 62, 1188, 1189).getMontant())) == 0);
                    }else
                        throw new AbnormalExecutionError();
                    
                }else
                    throw new AbnormalExecutionError();
                
            }else
                throw new AbnormalExecutionError();
            
        } catch (ForceReturn _bcornu_return_t) {
            return ((Boolean) (_bcornu_return_t.getDecision().getValue()));
        } finally {
            _bcornu_methode_context9.methodEnd();
        }
    }
}

