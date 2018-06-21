package io.fundrequest.core.request.fund;

import io.fundrequest.core.request.fund.command.FundsAddedCommand;
import io.fundrequest.core.request.fund.dto.FundDto;
import io.fundrequest.core.request.fund.dto.FundersDto;
import io.fundrequest.core.token.dto.TokenValueDto;

import java.security.Principal;
import java.util.List;

public interface FundService {
    List<FundDto> findAll();

    List<FundDto> findAll(Iterable<Long> ids);

    FundDto findOne(Long id);

    List<TokenValueDto> getTotalFundsForRequest(Long requestId);

    FundersDto getFundedBy(Principal principal, Long requestId);

    void clearTotalFundsCache(Long requestId);

    void addFunds(FundsAddedCommand command);
}
