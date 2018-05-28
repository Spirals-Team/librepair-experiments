package spoon.test.control;


public class Ifs {
    spoon.test.control.Ifs.Token jj_nt;

    int jj_ntk;

    spoon.test.control.Ifs.Token token;

    spoon.test.control.Ifs.Token token_source;

    @java.lang.SuppressWarnings({ "unused", "null" })
    final int jj_ntk() {
        int y;
        int i;
        int j;
        int partitionNumber = 0;
        int _imageRows = 0;
        int _partitionRows = 0;
        int _partitionColumns = 0;
        int _imageColumns = 0;
        int[] image = null;
        int[] _part = null;
        for (j = 0, partitionNumber = 0; j < _imageRows; j += _partitionRows) {
            for (i = 0; i < _imageColumns; i += _partitionColumns , partitionNumber++) {
                for (y = 0; y < _partitionRows; y++) {
                    java.lang.System.arraycopy(image[(j + y)], i, _part[y], 0, _partitionColumns);
                }
            }
        }
        for (int k = 0; k < 12; k++) {
        }
        if ((jj_nt = token.next) == null) {
            return jj_ntk = (token.next = token_source.getNextToken()).kind;
        }
        return jj_ntk = jj_nt.kind;
    }

    static class Token {
        public spoon.test.control.Ifs.Token next;

        public spoon.test.control.Ifs.Token getNextToken() {
            return null;
        }

        public int kind;
    }
}

