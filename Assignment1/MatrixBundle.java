import java.math.BigInteger;

public record MatrixBundle(int[][] exp_mat, int[][] exp_mod_mat, BigInteger[] r_vector) {}
