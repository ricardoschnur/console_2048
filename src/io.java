import java.util.Scanner;

public class io {
  public static Scanner scanner = new Scanner( System.in );


  public static char getInput() {
    char c = scanner.next().trim().charAt(0);
    return c;
  }

  public static void chooseMove(board field) {
    System.out.print( "Enter a move: " );
    char c = getInput();
    switch (c) {
      case 'w':
      field.moveUp();
      break;

      case 'a':
      field.moveLeft();
      break;

      case 's':
      field.moveDown();
      break;

      case 'd':
      field.moveRight();
      break;

      case 'q':
      field.moveQuit();
      break;

      default:
      System.out.println("Did not recognize move '" + c + "'.\n");
      io.chooseMove(field);
      break;
    }
  }
}
