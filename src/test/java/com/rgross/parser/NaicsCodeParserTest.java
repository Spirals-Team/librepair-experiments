package com.rgross.parser;

import com.rgross.exception.InvalidLineFormatException;
import com.rgross.model.NaicsCode;
import com.rgross.parser.NaicsCodeParser;
import com.rgross.repository.NaicsCodeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;

/**
 * Created by Ryan on 1/8/2018.
 */
public class NaicsCodeParserTest {

    NaicsCodeParser parser;
    NaicsCodeRepository repository;
    NaicsCode naicsCode;

    String codeWithoutQuotes = "213111,Drilling Oil and Gas Wells,,,";
    String codeWithQuotes = "921140,\"Executive and Legislative Offices, Combined \",,,";


    @Before
    public void setup() throws InvalidLineFormatException {
        repository = Mockito.mock(NaicsCodeRepository.class);
        parser = new NaicsCodeParser(repository);

        naicsCode = parser.generateNaicsCode(codeWithQuotes);

    }

    @Test
    public void generateNaicsCodeTest_WithoutQuotes() throws InvalidLineFormatException {
        NaicsCode naicsCode = parser.generateNaicsCode(codeWithoutQuotes);
        Assert.assertTrue(naicsCode.getNaicsCode().equals(213111));
        Assert.assertTrue(naicsCode.getCodeDescription().equals("Drilling Oil and Gas Wells"));
    }

    @Test
    public void generateNaicsCodeTest_WithQuotes() throws InvalidLineFormatException {
        NaicsCode naicsCode = parser.generateNaicsCode(codeWithQuotes);
        Assert.assertTrue(naicsCode.getNaicsCode().equals(921140));
        Assert.assertTrue(naicsCode.getCodeDescription().equals("Executive and Legislative Offices, Combined"));
    }

    @Test
    public void processLineTest_ExistingCodeFound() throws Exception {

//        Mockito.when(repository.containsNaicsCode(any(Integer.class))).thenReturn(true);
        Mockito.when(repository.save(any(NaicsCode.class))).thenReturn(naicsCode);

        parser.processLine(codeWithQuotes);

        Mockito.verify(repository, Mockito.times(0)).save(any(NaicsCode.class));
    }

    @Test
    public void processLineTest_NoExistingCodeFound() throws Exception {

//        Mockito.when(repository.containsNaicsCode(any(Integer.class))).thenReturn(false);
        Mockito.when(repository.save(Mockito.any(NaicsCode.class))).thenReturn(naicsCode);

        parser.processLine(codeWithQuotes);

        Mockito.verify(repository).save(any(NaicsCode.class));
    }

}
