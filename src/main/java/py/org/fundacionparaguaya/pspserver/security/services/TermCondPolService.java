package py.org.fundacionparaguaya.pspserver.security.services;

import py.org.fundacionparaguaya.pspserver.security.constants.TermCondPolType;
import py.org.fundacionparaguaya.pspserver.security.dtos.TermCondPolDTO;

/**
 *
 * @author mgonzalez
 *
 */
public interface TermCondPolService {
    TermCondPolDTO getLastTermCondPol(TermCondPolType type);
}
