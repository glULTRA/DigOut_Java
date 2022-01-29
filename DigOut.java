import java.io.IOException;
import java.util.Scanner;

public class DigOut 
{
    // Properties
    private Vector2 player_position = new Vector2();
    private Vector2 mapsize = new Vector2();
    private char[][] map = new char[mapsize.y][mapsize.x];
    private char[] maplist = {'#','@','&','$'};
    private Scanner scanner = new Scanner(System.in);
    /** Game List includes:
     * @ [!] is_the_player
     * @ [#] is_sand
     * @ [@] is_block
     * @ [&] is_bomm
     * @ [$] is_coin
     */

    public DigOut(){
        // Default GenerateRandomMap
        this.mapsize.y = 4;
        this.mapsize.x = 8;
        char [][]new_map = {
            {'x','x','x','x','x','x','x','x'},
            {'x','x','x','x','x','x','x','x'},
            {'x','x','x','x','x','x','x','x'},
            {'x','x','x','x','x','x','x','x'},
        };
        this.map = new_map;
        this.GenerateRandomMap();

    }
    public void Update(){
        // We should be able to handle input.
        HandleInput();
    }

    void HandleInput(){
        System.out.print("Input :");
        
    }

    public void Render()
    {
        // Clear OLD Frame!
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // Draw the game map
        this.Draw();
    }
    public boolean IsRunning(){
        return true;
    }

    public void GenerateRandomMap()
    {
        // First of always player's position is at 0,0 Coords.
        map[0][0] = '!'; // Player position

        for(int i=0; i < mapsize.y; i++)
        {
            for(int j=0; j < mapsize.x; j++)
            {
                // We should skip some cases.
                // First we should skip player's position.
                if(map[i][j]==map[0][0]) continue; 
            }
        }
    }

    public void Draw()
    {
        for(int i = 0; i < mapsize.y; i++)
        {
            for(int j = 0; j < mapsize.x; j++)
            {
                System.out.print(map[i][j]);
            }
            System.out.println();
        }
    }

    public void Run()
    {
        while(IsRunning()){
            /* <----- UPDATE -----> */
            this.Update();

            /* <----- RENDER -----> */
            this.Render();
        }
    }
}
