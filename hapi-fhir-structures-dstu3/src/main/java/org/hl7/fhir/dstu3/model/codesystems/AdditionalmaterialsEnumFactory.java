package org.hl7.fhir.dstu3.model.codesystems;

/*
  Copyright (c) 2011+, HL7, Inc.
  All rights reserved.
  
  Redistribution and use in source and binary forms, with or without modification, 
  are permitted provided that the following conditions are met:
  
   * Redistributions of source code must retain the above copyright notice, this 
     list of conditions and the following disclaimer.
   * Redistributions in binary form must reproduce the above copyright notice, 
     this list of conditions and the following disclaimer in the documentation 
     and/or other materials provided with the distribution.
   * Neither the name of HL7 nor the names of its contributors may be used to 
     endorse or promote products derived from this software without specific 
     prior written permission.
  
  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND 
  ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED 
  WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. 
  IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, 
  INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT 
  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR 
  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
  WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
  ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
  POSSIBILITY OF SUCH DAMAGE.
  
*/

// Generated on Mon, Apr 17, 2017 17:38-0400 for FHIR v3.0.1


import org.hl7.fhir.dstu3.model.EnumFactory;

public class AdditionalmaterialsEnumFactory implements EnumFactory<Additionalmaterials> {

  public Additionalmaterials fromCode(String codeString) throws IllegalArgumentException {
    if (codeString == null || "".equals(codeString))
      return null;
    if ("xray".equals(codeString))
      return Additionalmaterials.XRAY;
    if ("image".equals(codeString))
      return Additionalmaterials.IMAGE;
    if ("email".equals(codeString))
      return Additionalmaterials.EMAIL;
    if ("model".equals(codeString))
      return Additionalmaterials.MODEL;
    if ("document".equals(codeString))
      return Additionalmaterials.DOCUMENT;
    if ("other".equals(codeString))
      return Additionalmaterials.OTHER;
    throw new IllegalArgumentException("Unknown Additionalmaterials code '"+codeString+"'");
  }

  public String toCode(Additionalmaterials code) {
    if (code == Additionalmaterials.XRAY)
      return "xray";
    if (code == Additionalmaterials.IMAGE)
      return "image";
    if (code == Additionalmaterials.EMAIL)
      return "email";
    if (code == Additionalmaterials.MODEL)
      return "model";
    if (code == Additionalmaterials.DOCUMENT)
      return "document";
    if (code == Additionalmaterials.OTHER)
      return "other";
    return "?";
  }

    public String toSystem(Additionalmaterials code) {
      return code.getSystem();
      }

}

