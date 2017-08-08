class A {

  void h() {
    int x = 1;
    int a = 0;
    if (x >= a) {
      int y = 1 / a; // Noncompliant
    }
  }
}
