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
    private char action;

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
        player_position.x = 0;
        player_position.y = 0;

        char [][]new_map = {
            {'!',' ',' ',' ',' ',' ',' ',' '},
            {' ','#','#',' ','#','#','#',' '},
            {' ','#','#',' ','#','#','#',' '},
            {' ',' ',' ',' ',' ',' ',' ',' '},
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
        this.action = scanner.next().charAt(0);

        switch (action) {
            case 'd':
                Move(+1,0);
                break;
            case 'a':
                Move(-1,0);
                break;
            case 'w':
                Move(0,-1);
                break;
            case 's':
                Move(0,+1);
                break;
            case 'i':
                Break(0, -1);
                break;
            case 'k':
                Break(0, +1);
                break;
            case 'j':
                Break(-1, 0);
                break;
            case 'l':
                Break(+1, 0);
                break;
        
            default:
                System.out.println("Please make sure input is one of these : [w,a,s,d,i,j,k,l]");
                break;
        }
    }
    /**
     * if we move to right position
     * if(map[player_position.y+ydir][player_position.x+xdir] == " ")
     *      we can go
     *      Then we will be able to move to there 
     *      and we will do something like that :
     *      player_position.x += 1; // if for right side
     *      player_position.y += 0;
     */

    public void Move(int xDir, int yDir)
    {
        // We must check that we are not outside of the array length !!
        if(player_position.x+xDir < 0 || player_position.x+xDir > (mapsize.x-1))
            return;
        if(player_position.y+yDir < 0 || player_position.y+yDir > (mapsize.y-1))
            return;
        if(map[player_position.y+yDir][player_position.x+xDir] == ' ')
        {
            this.map[player_position.y][player_position.x] = ' ';
            this.player_position.x += xDir;
            this.player_position.y += yDir;
            this.map[player_position.y][player_position.x] = '!';
        }
    }

    public void Break(int xDir, int yDir)
    {
        // We must check that we are not outside of the array length (MAPSIZE!) !!
        if(player_position.x+xDir < 0 || player_position.x+xDir > (mapsize.x-1))
            return;
        if(player_position.y+yDir < 0 || player_position.y+yDir > (mapsize.y-1))
            return;
        if(map[player_position.y+yDir][player_position.x+xDir] == '#')
            this.map[player_position.y+yDir][player_position.x+xDir] = ' ';
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
        player_position.x = 0;
        player_position.y = 0;

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
        this.Render();
        while(IsRunning()){
            /* <----- UPDATE -----> */
            this.Update();

            /* <----- RENDER -----> */
            this.Render();
        }
    }
}
