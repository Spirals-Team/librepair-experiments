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


import org.hl7.fhir.exceptions.FHIRException;

public enum MedicationRequestIntent {

        /**
         * The request is a suggestion made by someone/something that doesn't have an intention to ensure it occurs and without providing an authorization to act
         */
        PROPOSAL, 
        /**
         * The request represents an intension to ensure something occurs without providing an authorization for others to act
         */
        PLAN, 
        /**
         * The request represents a request/demand and authorization for action
         */
        ORDER, 
        /**
         * The request represents an instance for the particular order, for example a medication administration record.
         */
        INSTANCEORDER, 
        /**
         * added to help the parsers
         */
        NULL;
        public static MedicationRequestIntent fromCode(String codeString) throws FHIRException {
            if (codeString == null || "".equals(codeString))
                return null;
        if ("proposal".equals(codeString))
          return PROPOSAL;
        if ("plan".equals(codeString))
          return PLAN;
        if ("order".equals(codeString))
          return ORDER;
        if ("instance-order".equals(codeString))
          return INSTANCEORDER;
        throw new FHIRException("Unknown MedicationRequestIntent code '"+codeString+"'");
        }
        public String toCode() {
          switch (this) {
            case PROPOSAL: return "proposal";
            case PLAN: return "plan";
            case ORDER: return "order";
            case INSTANCEORDER: return "instance-order";
            default: return "?";
          }
        }
        public String getSystem() {
          return "http://hl7.org/fhir/medication-request-intent";
        }
        public String getDefinition() {
          switch (this) {
            case PROPOSAL: return "The request is a suggestion made by someone/something that doesn't have an intention to ensure it occurs and without providing an authorization to act";
            case PLAN: return "The request represents an intension to ensure something occurs without providing an authorization for others to act";
            case ORDER: return "The request represents a request/demand and authorization for action";
            case INSTANCEORDER: return "The request represents an instance for the particular order, for example a medication administration record.";
            default: return "?";
          }
        }
        public String getDisplay() {
          switch (this) {
            case PROPOSAL: return "Proposal";
            case PLAN: return "Plan";
            case ORDER: return "Order";
            case INSTANCEORDER: return "Instance Order";
            default: return "?";
          }
    }


}

