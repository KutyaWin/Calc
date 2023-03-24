public enum RomNumeric {
    C(100),  L(50),  X(10), IX(9), IIX(8), VII(7), VI(6), V(5), IV(4), III(3),II(2), I(1);

   private int numeric;


    RomNumeric(int numeric){
      this.numeric = numeric;
    }

    public int getNumeric() {
        return numeric;
    }

}
