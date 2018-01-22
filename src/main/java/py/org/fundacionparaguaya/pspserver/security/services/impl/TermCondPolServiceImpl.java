package py.org.fundacionparaguaya.pspserver.security.services.impl;

import static com.google.common.base.Preconditions.checkArgument;

import java.util.Optional;

import org.springframework.stereotype.Service;

import py.org.fundacionparaguaya.pspserver.common.exceptions.UnknownResourceException;
import py.org.fundacionparaguaya.pspserver.security.constants.TermCondPolType;
import py.org.fundacionparaguaya.pspserver.security.dtos.TermCondPolDTO;
import py.org.fundacionparaguaya.pspserver.security.mapper.TermCondPolMapper;
import py.org.fundacionparaguaya.pspserver.security.repositories.TermCondPolRepository;
import py.org.fundacionparaguaya.pspserver.security.services.TermCondPolService;

@Service
public class TermCondPolServiceImpl implements TermCondPolService {

    private TermCondPolRepository repository;

    private final TermCondPolMapper mapper;

    public TermCondPolServiceImpl(TermCondPolRepository repository,
        TermCondPolMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public TermCondPolDTO getLastTermCondPol(TermCondPolType type) {

        checkArgument(type!=null,
            "Argument was %s but expected not null", type);

        return Optional.ofNullable(repository
            .findFirstByTypeCodOrderByCreatedDateDesc(type))
            .map(mapper::entityToDto)
            .orElseThrow(() -> new UnknownResourceException(
                "Terms and Conditions o Privacity Polities"
                + " does not exist"));

    }

}
