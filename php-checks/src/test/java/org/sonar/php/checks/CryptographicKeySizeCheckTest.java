package org.sonar.php.checks;

import org.junit.Test;
import org.sonar.plugins.php.CheckVerifier;

public class CryptographicKeySizeCheckTest {
  @Test
  public void test() throws Exception {
    CheckVerifier.verify(new CryptographicKeySizeCheck(), "CryptographicKeySize.php");
  }
}