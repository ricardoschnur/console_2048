public class game {
  public static void main(String[] args) {
    int rows = 4;
    int cols = 4;
    board field = new board(rows, cols);

    /*
    for (int i = 0; i < rows; ++i) {
      for (int j = 0; j < cols; ++j) {
        int val = 1 << i * cols + j;
        val = val > 2048 ? 0 : val;
        field.setValue(i, j, val);
        if (val != 0) {
          field.setFull(i, j, true);
        }
      }
    }
    */

    field.print();
  }
}
