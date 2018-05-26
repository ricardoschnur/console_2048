public class game {
  public static void main(String[] args) {
    int win_score = 2048;
    int rows = 4;
    int cols = 4;
    board field = new board(rows, cols, win_score);

    while (true) {
      field.print();
      io.chooseMove(field);
    }
  }
}
