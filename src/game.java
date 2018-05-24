public class game {
  public static void main(String[] args) {
    int rows = 4;
    int cols = 4;
    board field = new board(rows, cols);

    while (true) {
      field.print();
      io.chooseMove(field);
    }
  }
}
