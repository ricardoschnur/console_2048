import java.util.Scanner;

public class io {
  public static Scanner scanner = new Scanner( System.in );


  public static char getInput() {
    char c = scanner.next().trim().charAt(0);
    return c;
  }

  public static void chooseMove(board field) {
    // Check if move is possible
    if (field.free == 0) {
      board new_field = field.copyBoard();
      boolean b = (new_field.moveUp() == 0)
                    && (new_field.moveDown() == 0)
                    && (new_field.moveLeft() == 0)
                    && (new_field.moveRight() == 0);
      if (b == true) {
        field.loseGame();
      }
    }


    System.out.print( "Enter a move: " );
    char c = getInput();
    int i = 0;
    switch (c) {
      case 'w':
      i = field.moveUp();
      break;

      case 'a':
      i = field.moveLeft();
      break;

      case 's':
      i = field.moveDown();
      break;

      case 'd':
      i = field.moveRight();
      break;

      case 'q':
      field.moveQuit();
      break;

      case 'n':
      field.reset();
      return;

      default:
      System.out.println("Did not recognize move '" + c + "'.\n");
      io.chooseMove(field);
      break;
    }
    if (i == 0) {
      field.print();
      System.out.println("Move '" + c + "' not possible!\n");
      io.chooseMove(field);
      return;
    }
    field.addTile();
  }
}
