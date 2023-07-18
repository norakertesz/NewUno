import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DataManager {
    private static final String DATABASE_NAME = "database.sqlite";
    private static final String CREATETABLE = "CREATE TABLE Sessions (Player varchar(100) NOT NULL, Session int NOT NULL, Round int NOT NULL, Score int NOT NULL, CONSTRAINT PK_Sessions PRIMARY KEY (Player, Session, Round));";
    private static final String INSERT_TEMPLATE= "INSERT INTO Sessions (Player, Session, Round, Score) VALUES ('%1s', %2d, %3d, %4d);";
    private static final String SELECT_BYPLAYERANDSESSION = "SELECT Player, SUM(Score) AS Score FROM Sessions WHERE Player = '%1s' AND Session = %2d;";

    public static void createDatabase() {
        try{
            SqliteClient client = new SqliteClient(DATABASE_NAME);
            if (client.tableExists("Sessions")){
                client.executeStatement("DROP TABLE Sessions;");
            }
            client.executeStatement(CREATETABLE);

        }catch (SQLException ex) {
            System.out.println("Ups! Something went wrong:" + ex.getMessage());
        }
    }

    public static void recordWinnerInDB() {
        Player winner = null;
        for (Player player : Game.playersInGame) {
            if (player == Game.winner) {
                winner = player;
                break;
            }
        }

        if (winner != null) {
            try {
                SqliteClient client = new SqliteClient(DATABASE_NAME);

// Update DB
                client.executeStatement(String.format(INSERT_TEMPLATE, winner.getName(), Game.getSession(), Game.getRound(), Game.getRoundWinnerPoint()));

                System.out.println("Points for the winner have been updated in the database2.");
            } catch (SQLException ex) {
                System.out.println("Oops! Something went wrong: " + ex.getMessage());
            }
        }
    }
}
