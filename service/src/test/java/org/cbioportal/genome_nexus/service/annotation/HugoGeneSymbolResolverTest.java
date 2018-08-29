package org.cbioportal.genome_nexus.service.annotation;

import org.cbioportal.genome_nexus.model.VariantAnnotation;
import org.cbioportal.genome_nexus.service.mock.CanonicalTranscriptResolverMocker;
import org.cbioportal.genome_nexus.service.mock.VariantAnnotationMockData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class HugoGeneSymbolResolverTest
{
    @InjectMocks
    private HugoGeneSymbolResolver hugoGeneSymbolResolver;

    @Mock
    private CanonicalTranscriptResolver canonicalTranscriptResolver;

    private final VariantAnnotationMockData variantAnnotationMockData = new VariantAnnotationMockData();
    private final CanonicalTranscriptResolverMocker canonicalTranscriptResolverMocker = new CanonicalTranscriptResolverMocker();

    @Test
    public void resolveHugoGeneSymbolForCanonical() throws IOException
    {
        Map<String, VariantAnnotation> variantMockData = this.variantAnnotationMockData.generateData();
        this.canonicalTranscriptResolverMocker.mockMethods(variantMockData, this.canonicalTranscriptResolver);

        assertEquals(
            "JAK1",
            this.hugoGeneSymbolResolver.resolve(variantMockData.get("1:g.65325832_65325833insG"))
        );

        assertEquals(
            "TPRXL",
            this.hugoGeneSymbolResolver.resolve(variantMockData.get("3:g.14106026_14106037delCCAGCAGTAGCT"))
        );

        assertEquals(
            "FGD5",
            this.hugoGeneSymbolResolver.resolve(variantMockData.get("3:g.14940279_14940280insCAT"))
        );

        assertEquals(
            "ZBTB20",
            this.hugoGeneSymbolResolver.resolve(variantMockData.get("3:g.114058003_114058003delG"))
        );

        assertEquals(
            "DRD5",
            this.hugoGeneSymbolResolver.resolve(variantMockData.get("4:g.9784947_9784948insAGA"))
        );

        assertEquals(
            "SHROOM3",
            this.hugoGeneSymbolResolver.resolve(variantMockData.get("4:g.77675978_77675979insC"))
        );

        assertEquals(
            "IFNGR1",
            this.hugoGeneSymbolResolver.resolve(variantMockData.get("6:g.137519505_137519506delCT"))
        );

        assertEquals(
            "IFNGR1",
            this.hugoGeneSymbolResolver.resolve(variantMockData.get("6:g.137519505_137519506delCTinsA"))
        );

        assertEquals(
            "BRAF",
            this.hugoGeneSymbolResolver.resolve(variantMockData.get("7:g.140453136A>T"))
        );

        assertEquals(
            "GPR124",
            this.hugoGeneSymbolResolver.resolve(variantMockData.get("8:g.37696499_37696500insG"))
        );

        assertEquals(
            "TSC1",
            this.hugoGeneSymbolResolver.resolve(variantMockData.get("9:g.135797242_135797242delCinsAT"))
        );

        assertEquals(
            "CHUK",
            this.hugoGeneSymbolResolver.resolve(variantMockData.get("10:g.101953779_101953779delT"))
        );

        assertEquals(
            "GANAB",
            this.hugoGeneSymbolResolver.resolve(variantMockData.get("11:g.62393546_62393547delGGinsAA"))
        );

        assertEquals(
            "KRAS",
            this.hugoGeneSymbolResolver.resolve(variantMockData.get("12:g.25398285C>A"))
        );

        assertEquals(
            "FLT3",
            this.hugoGeneSymbolResolver.resolve(variantMockData.get("13:g.28608258_28608275delCATATTCATATTCTCTGAinsGGGGTGGGGGGG"))
        );

        assertEquals(
            "USP7",
            this.hugoGeneSymbolResolver.resolve(variantMockData.get("16:g.9057113_9057114insCTG"))
        );

        assertEquals(
            "EML2",
            this.hugoGeneSymbolResolver.resolve(variantMockData.get("19:g.46141892_46141893delTCinsAA"))
        );

        assertEquals(
            "CHEK2",
            this.hugoGeneSymbolResolver.resolve(variantMockData.get("22:g.29091840_29091841delTGinsCA"))
        );

        assertEquals(
            "MYH9",
            this.hugoGeneSymbolResolver.resolve(variantMockData.get("22:g.36689419_36689421delCCT"))
        );
    }

    @Test
    public void resolveHugoGeneSymbol() throws IOException
    {
        Map<String, VariantAnnotation> variantMockData = this.variantAnnotationMockData.generateData();

        assertEquals(
            "BRF2",
            this.hugoGeneSymbolResolver.resolve(
                variantMockData.get("8:g.37696499_37696500insG").getTranscriptConsequences().get(0)
            )
        );

        assertEquals(
            "B3GAT3",
            this.hugoGeneSymbolResolver.resolve(
                variantMockData.get("11:g.62393546_62393547delGGinsAA").getTranscriptConsequences().get(0)
            )
        );

        assertEquals(
            "MIR330",
            this.hugoGeneSymbolResolver.resolve(
                variantMockData.get("19:g.46141892_46141893delTCinsAA").getTranscriptConsequences().get(1)
            )
        );
    }
}
