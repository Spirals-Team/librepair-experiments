package com.cmpl.web.core.membership;

public interface MembershipTranslator {

  MembershipDTO fromCreateFormToDTO(MembershipCreateForm form);

  MembershipResponse fromDTOToResponse(MembershipDTO dto);

}
