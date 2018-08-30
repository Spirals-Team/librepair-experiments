package ru.job4j.service;

import org.springframework.stereotype.Service;
import ru.job4j.Tokenizer;

@Service("tokenizerServiceImpl")
public class TokenizerServiceImpl implements TokenizerService {

    @Override
    public String codeEncodeToken(final String auth) {
        return new Tokenizer().codeEncodeToken(auth);
    }
}
