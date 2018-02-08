package com.rgross.parser;

import com.rgross.contract.CyberSecurityContract;
import com.rgross.exception.InvalidLineFormatException;
import com.rgross.exception.OutOfStateException;
import com.rgross.model.NaicsCode;
import com.rgross.parser.CyberSecurityContractParser;
import com.rgross.repository.CyberSecurityContractRepository;
import com.rgross.repository.NaicsCodeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created by ryan_gross on 1/13/18.
 */
public class CyberSecurityGeneralContractParserTest {

    CyberSecurityContractParser cyberSecurityContractParser;
    CyberSecurityContractRepository cyberSecurityContractRepository;
    NaicsCodeRepository naicsCodeRepository;
    String inStateExample = "6707A57B-18DB-4F8E-918E-ECAD49ED4487,\"Active\",4800.00,4800.00,4800.00,\"1500: Department of Justice\",\"1544: U.S. MARSHALS SERVICE\",\"1500: Department of Justice\",\"1544: U.S. MARSHALS SERVICE\",\"15M300: U.S. MARSHALS SERVICE\",\"1544: U.S. MARSHALS SERVICE\",\"15M300: U.S. MARSHALS SERVICE\",\"X\",09/15/2016,09/15/2016,09/30/2016,09/30/2016,\"\",\"PO Purchase Order\",\"\",\"J: FIRM FIXED PRICE\",\"\",\"B: PLAN NOT REQUIRED\",\"\",\"\",\"X: NOT APPLICABLE\",\"\",\"X: NOT APPLICABLE\",\"\",\"\",\"\",\"IGF::OT::IGF REMOTE SPEAKER MIC\",\"Y: YES\",\"1\",\"NONE: NONE\",\"15\",\"4575\",\"\",\"\",\"\",\"\",\"\",\"\",\"MAGNUM ELECTRONICS, INC\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"927 HORSEPOND RD\",\"\",\"\",\"DOVER\",\"DE\",\"199017221\",\"USA: UNITED STATES OF AMERICA\",\"DE\",\"00\",\"00\",\"786404830\",\"19901\",\"786404830\",\"786404830\",\"3027349250\",\"3027341056\",\"08/07/2000\",\"08/22/2017\",\"MAGNUM ELECTRONICS, INC\",\"\",\"\",\"DOVER\",\"DE: Delaware\",\"USA: UNITED STATES OF AMERICA\",\"199017221\",\"DE00\",\"DE00\",\"58\",\"5810: COMMUNICATIONS SECURITY EQUIPMENT AND COMPONENTS\",\"\",\"\",\"334290\",\"\",\"N: Transaction does not use GFE/GFP\",\"E: NOT REQUIRED\",\"C: NO CLAUSES INCLUDED AND NO SUSTAINABILITY INCLUDED\",\"\",\"D: NOT A BUNDLED REQUIREMENT\",\"N: N\",\"USA: UNITED STATES\",\"D: MFG IN U.S.\",\"A: U.S. OWNED BUSINESS\",\"1544: U.S. MARSHALS SERVICE\",\"DJM16A37P0219\",\"0\",\"0\",2016,\"\",\"\",\"\",\"\",\"F: COMPETED UNDER SAP\",\":\",\"3\",\"D: COMMERCIAL ITEM PROCEDURES NOT USED\",\"N: NO\",\"\",\"N\",\"\",\"SP1: SIMPLIFIED ACQUISITION\",\"SBA: SMALL BUSINESS SET ASIDE - TOTAL\",\"N\",\"NONE: NO PREFERENCE USED\",\"N\",\"\",\"\",\"OTHER\",18,1500000,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,Y,N,N,N,N,N,\"S: SMALL BUSINESS\",\"N\",\"N\",\"N\",N,N,N,N,N,\"\",\"N\",\"N\",\"Y\",N,N,N,N,N,N,N,\"Y\",\"N\",\"N\",\"\",\"false\",\"N\",\"N\",\"N\",\"\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"\",\"\",\"\",\"\",\"N: NO\",\"N\",\"N\",\"N\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"X: NOT APPLICABLE\",9/15/2016";
    String inStateInvalidLengthExample = "6707A57B-18DB-4F8E-918E-ECAD49ED4487,\"abc\", \"Active\",4800.00,4800.00,4800.00,\"1500: Department of Justice\",\"1544: U.S. MARSHALS SERVICE\",\"1500: Department of Justice\",\"1544: U.S. MARSHALS SERVICE\",\"15M300: U.S. MARSHALS SERVICE\",\"1544: U.S. MARSHALS SERVICE\",\"15M300: U.S. MARSHALS SERVICE\",\"X\",09/15/2016,09/15/2016,09/30/2016,09/30/2016,\"\",\"PO Purchase Order\",\"\",\"J: FIRM FIXED PRICE\",\"\",\"B: PLAN NOT REQUIRED\",\"\",\"\",\"X: NOT APPLICABLE\",\"\",\"X: NOT APPLICABLE\",\"\",\"\",\"\",\"IGF::OT::IGF REMOTE SPEAKER MIC\",\"Y: YES\",\"1\",\"NONE: NONE\",\"15\",\"4575\",\"\",\"\",\"\",\"\",\"\",\"\",\"MAGNUM ELECTRONICS, INC\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"927 HORSEPOND RD\",\"\",\"\",\"DOVER\",\"DE\",\"199017221\",\"USA: UNITED STATES OF AMERICA\",\"DE\",\"00\",\"00\",\"786404830\",\"19901\",\"786404830\",\"786404830\",\"3027349250\",\"3027341056\",\"08/07/2000\",\"08/22/2017\",\"MAGNUM ELECTRONICS, INC\",\"\",\"\",\"DOVER\",\"DE: Delaware\",\"USA: UNITED STATES OF AMERICA\",\"199017221\",\"DE00\",\"DE00\",\"58\",\"5810: COMMUNICATIONS SECURITY EQUIPMENT AND COMPONENTS\",\"\",\"\",\"334290\",\"\",\"N: Transaction does not use GFE/GFP\",\"E: NOT REQUIRED\",\"C: NO CLAUSES INCLUDED AND NO SUSTAINABILITY INCLUDED\",\"\",\"D: NOT A BUNDLED REQUIREMENT\",\"N: N\",\"USA: UNITED STATES\",\"D: MFG IN U.S.\",\"A: U.S. OWNED BUSINESS\",\"1544: U.S. MARSHALS SERVICE\",\"DJM16A37P0219\",\"0\",\"0\",2016,\"\",\"\",\"\",\"\",\"F: COMPETED UNDER SAP\",\":\",\"3\",\"D: COMMERCIAL ITEM PROCEDURES NOT USED\",\"N: NO\",\"\",\"N\",\"\",\"SP1: SIMPLIFIED ACQUISITION\",\"SBA: SMALL BUSINESS SET ASIDE - TOTAL\",\"N\",\"NONE: NO PREFERENCE USED\",\"N\",\"\",\"\",\"OTHER\",18,1500000,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,Y,N,N,N,N,N,\"S: SMALL BUSINESS\",\"N\",\"N\",\"N\",N,N,N,N,N,\"\",\"N\",\"N\",\"Y\",N,N,N,N,N,N,N,\"Y\",\"N\",\"N\",\"\",\"false\",\"N\",\"N\",\"N\",\"\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"\",\"\",\"\",\"\",\"N: NO\",\"N\",\"N\",\"N\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"X: NOT APPLICABLE\",9/15/2016";
    String outOfStateExample = "6707A57B-18DB-4F8E-918E-ECAD49ED4487,\"Active\",4800.00,4800.00,4800.00,\"1500: Department of Justice\",\"1544: U.S. MARSHALS SERVICE\",\"1500: Department of Justice\",\"1544: U.S. MARSHALS SERVICE\",\"15M300: U.S. MARSHALS SERVICE\",\"1544: U.S. MARSHALS SERVICE\",\"15M300: U.S. MARSHALS SERVICE\",\"X\",09/15/2016,09/15/2016,09/30/2016,09/30/2016,\"\",\"PO Purchase Order\",\"\",\"J: FIRM FIXED PRICE\",\"\",\"B: PLAN NOT REQUIRED\",\"\",\"\",\"X: NOT APPLICABLE\",\"\",\"X: NOT APPLICABLE\",\"\",\"\",\"\",\"IGF::OT::IGF REMOTE SPEAKER MIC\",\"Y: YES\",\"1\",\"NONE: NONE\",\"15\",\"4575\",\"\",\"\",\"\",\"\",\"\",\"\",\"MAGNUM ELECTRONICS, INC\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"927 HORSEPOND RD\",\"\",\"\",\"DOVER\",\"DE\",\"199017221\",\"USA: UNITED STATES OF AMERICA\",\"DE\",\"00\",\"00\",\"786404830\",\"19901\",\"786404830\",\"786404830\",\"3027349250\",\"3027341056\",\"08/07/2000\",\"08/22/2017\",\"MAGNUM ELECTRONICS, INC\",\"\",\"\",\"DOVER\",\"VA:Virginia\",\"USA: UNITED STATES OF AMERICA\",\"199017221\",\"DE00\",\"DE00\",\"58\",\"5810: COMMUNICATIONS SECURITY EQUIPMENT AND COMPONENTS\",\"\",\"\",\"334290\",\"\",\"N: Transaction does not use GFE/GFP\",\"E: NOT REQUIRED\",\"C: NO CLAUSES INCLUDED AND NO SUSTAINABILITY INCLUDED\",\"\",\"D: NOT A BUNDLED REQUIREMENT\",\"N: N\",\"USA: UNITED STATES\",\"D: MFG IN U.S.\",\"A: U.S. OWNED BUSINESS\",\"1544: U.S. MARSHALS SERVICE\",\"DJM16A37P0219\",\"0\",\"0\",2016,\"\",\"\",\"\",\"\",\"F: COMPETED UNDER SAP\",\":\",\"3\",\"D: COMMERCIAL ITEM PROCEDURES NOT USED\",\"N: NO\",\"\",\"N\",\"\",\"SP1: SIMPLIFIED ACQUISITION\",\"SBA: SMALL BUSINESS SET ASIDE - TOTAL\",\"N\",\"NONE: NO PREFERENCE USED\",\"N\",\"\",\"\",\"OTHER\",18,1500000,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,N,Y,N,N,N,N,N,\"S: SMALL BUSINESS\",\"N\",\"N\",\"N\",N,N,N,N,N,\"\",\"N\",\"N\",\"Y\",N,N,N,N,N,N,N,\"Y\",\"N\",\"N\",\"\",\"false\",\"N\",\"N\",\"N\",\"\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"\",\"\",\"\",\"\",\"N: NO\",\"N\",\"N\",\"N\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"X: NOT APPLICABLE\",9/15/2016";
    String inStateExampleTwo = "CBC5C0E2-99E2-4EE2-9976-78F477F4B4E1,\"Active\",483017.29,\"\",\"\",\"7000: Department of Homeland Security\",\"7001: OFFICE OF PROCUREMENT OPERATIONS\",\"7000: Department of Homeland Security\",\"7001: OFFICE OF PROCUREMENT OPERATIONS\",\"FPSD3: FPS EAST CCG DIVISION 3\",\"7001: OFFICE OF PROCUREMENT OPERATIONS\",\"SCNP: DIRECTORATE FOR NATIONAL PROTECTION AND PROGRAMS\",\"X\",11/25/2015,11/25/2015,03/31/2016,03/31/2016,\"\",\"DO Delivery Order\",\"C: FUNDING ONLY ACTION\",\"J: FIRM FIXED PRICE\",\"\",\"\",\"\",\"N: NO\",\"N: NO - SERVICE WHERE PBA IS NOT USED.\",\"\",\"X: NOT APPLICABLE\",\"Z: NOT APPLICABLE\",\"N: No\",\"\",\"IGF::CL,CT::IGF STATE OF DELAWARE PROTECTIVE SECURITY OFFICER (PSO) SERVICES\",\"N: NO\",\"1\",\"NONE: NONE\",\"70\",\"0542\",\"\",\"\",\"\",\"\",\"\",\"\",\"FRONTLINE SECURITY SERVICES, LLC\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"3111 HUBBARD RD\",\"\",\"\",\"HYATTSVILLE\",\"MD\",\"207852022\",\"USA: UNITED STATES OF AMERICA\",\"MD\",\"04\",\"04\",\"196900596\",\"207852022\",\"196900596\",\"196900596\",\"3014528595\",\"\",\"10/03/2008\",\"08/27/2014\",\"FRONTLINE SECURITY SERVICES, LLC\",\"\",\"\",\"WILMINGTON\",\"DE: Delaware\",\"USA: UNITED STATES OF AMERICA\",\"198013519\",\"DE00\",\"DE00\",\"S\",\"S206: HOUSEKEEPING- GUARD\",\"\",\"\",\"561612\",\"\",\"N: Transaction does not use GFE/GFP\",\"E: NOT REQUIRED\",\"C: NO CLAUSES INCLUDED AND NO SUSTAINABILITY INCLUDED\",\"\",\"D: NOT A BUNDLED REQUIREMENT\",\"N: N\",\"USA: UNITED STATES\",\"C: NOT A MANUFACTURED END PRODUCT\",\"A: U.S. OWNED BUSINESS\",\"7001: OFFICE OF PROCUREMENT OPERATIONS\",\"HSHQE316J00001\",\"P00001\",\"0\",2016,\"7001\",\"HSHQE314D00001\",\"0\",\"\",\"D: FULL AND OPEN COMPETITION AFTER EXCLUSION OF SOURCES\",\": \",\"6\",\"A: COMMERCIAL ITEM\",\"N: NO\",\"\",\"N\",\"\",\"NP: NEGOTIATED PROPOSAL/QUOTE\",\"SDVOSBC: SERVICE DISABLED VETERAN OWNED SMALL BUSINESS SET-ASIDE\",\"N\",\"NONE: NO PREFERENCE USED\",\"Y\",\"\",\"\",\"CORPORATE NOT TAX EXEMPT\",65,5000000,N,N,N,Y,N,N,N,N,Y,Y,N,N,N,N,N,Y,N,N,Y,N,N,N,N,Y,N,N,N,N,N,\"S: SMALL BUSINESS\",\"N\",\"N\",\"N\",N,N,N,N,N,\"\",\"N\",\"N\",\"Y\",N,N,N,N,N,N,N,\"N\",\"Y\",\"N\",\"\",\"false\",\"N\",\"N\",\"Y\",\"\",\"N\",\"Y\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"N\",\"\",\"\",\"\",\"\",\"N: NO\",\"Y\",\"N\",\"N\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"\",\"X: NOT APPLICABLE\",12/3/2015";

    @Before
    public void setup() throws OutOfStateException, InvalidLineFormatException
    {
        cyberSecurityContractRepository = Mockito.mock(CyberSecurityContractRepository.class);
        naicsCodeRepository = Mockito.mock(NaicsCodeRepository.class);
        cyberSecurityContractParser = new CyberSecurityContractParser(cyberSecurityContractRepository, naicsCodeRepository);

    }

    @Test
    public void generateCyberSecurityContract_Successful() throws Exception {
        NaicsCode naicsCode = new NaicsCode(Integer.valueOf("334290"), "");
        Mockito.when(naicsCodeRepository.findByNaicsCode(Integer.valueOf("334290"))).thenReturn(naicsCode);
        CyberSecurityContract cyberSecurityContract = cyberSecurityContractParser.generateCyberSecurityContract(inStateExample);
        Assert.assertTrue(cyberSecurityContract.getDollarsObligated() == 4800.00);
        Assert.assertTrue(cyberSecurityContract.getDunsNumber().equals("786404830"));
        Assert.assertTrue(cyberSecurityContract.getFiscalYear() == 2016);
        Assert.assertTrue(cyberSecurityContract.getNaicsCode() == naicsCode);
    }

    @Test
    public void generateCyberSecurityContract_SuccessfulTwo() throws Exception {
        NaicsCode naicsCode = new NaicsCode(Integer.valueOf("561612"), "");
        Mockito.when(naicsCodeRepository.findByNaicsCode(Integer.valueOf("561612"))).thenReturn(naicsCode);
        CyberSecurityContract cyberSecurityContract = cyberSecurityContractParser.generateCyberSecurityContract(inStateExampleTwo);
        Assert.assertTrue(cyberSecurityContract.getDunsNumber().equals("196900596"));
        Assert.assertTrue(cyberSecurityContract.getFiscalYear() == 2016);
        Assert.assertTrue(cyberSecurityContract.getNaicsCode() == naicsCode);
    }

//    @Test(expected = InvalidLineFormatException.class)
//    public void generateCyberSecurityContract_Failure() throws Exception {
//
//        CyberSecurityContract cyberSecurityContract = cyberSecurityContractParser.generateCyberSecurityContract(inStateInvalidLengthExample);
//
//    }
//
//    @Test
//    public void processLine_Successful() throws Exception {
//        Mockito.when(cyberSecurityContractRepository.save(Mockito.any(CyberSecurityContract.class))).thenReturn(cyberSecurityContract);
//
//        cyberSecurityContractParser.processLine(inStateExample);
//
//        Mockito.verify(cyberSecurityContractRepository).save(Mockito.any(CyberSecurityContract.class));
//    }
//
//    @Test
//    public void processLine_Failure() throws Exception {
//
//        cyberSecurityContractParser.processLine(inStateInvalidLengthExample);
//
//        Mockito.verifyZeroInteractions(cyberSecurityContractRepository);
//
//    }

}
