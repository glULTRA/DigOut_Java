import java.io.IOException;
import java.util.Scanner;

public class DigOut 
{
    // Properties
    private Vector2 player_position = new Vector2(); // x and y coordinates.
    private Vector2 mapsize = new Vector2(); // width and height of the map.
    private char[][] map = new char[mapsize.y][mapsize.x]; // Map Tile.
    private char[] maplist = {'#','@','&','$','+'};
    class maplistMaximum{ public int sand ,block ,bomb ,coin ,life; };
    private Scanner scanner = new Scanner(System.in);
    private char action;

    /** Game List includes:
     * @ [!] is_the_player
     * @ [#] is_sand
     * @ [@] is_block
     * @ [&] is_bomb
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
        this.Settings();
        this.GenerateRandomMap();

    }
    public void Settings(){
        // We will go through it later.
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
                try {
                    Thread.sleep(1900);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
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
        this.map[0][0] = '!'; // Player position
        this.player_position.x = 0;
        this.player_position.y = 0;
        
        // Random Map algorithm.
        int count_of_block = 0;
        int count_of_bomb  = 0;
        int count_of_life  = 0;
        int count_of_coin  = 0;
        int count_of_sand  = 0;

        // Map list maximu size
        maplistMaximum maplistmax = new maplistMaximum();
        maplistmax.block = 10;
        maplistmax.bomb = 2;
        maplistmax.life = 1;
        maplistmax.coin = 1;

        for(int i=0; i < mapsize.y; i++)
        {
            for(int j=0; j < mapsize.x; j++)
            {
                // We should skip some cases.
                // First we should skip player's position.
                if(map[i][j]==map[0][0]) continue;

                // getting random number from 0 to 4 and casting it to integer.
                int random = (int)(Math.random()*5);
                
                if(this.maplist[random] == '@')
                    count_of_block++;
                else if(this.maplist[random] == '&')
                    count_of_bomb++;
                else if(this.maplist[random] == '+')
                    count_of_life++;
                else if(this.maplist[random] == '$')
                    count_of_coin++;
                else if(this.maplist[random] == '#')
                    count_of_sand++;

                if(count_of_block > maplistmax.block)
                {
                    --count_of_block;
                    --j;
                    continue;
                }
                else if(count_of_bomb > maplistmax.bomb)
                {
                    --count_of_bomb;
                    --j;
                    continue;
                }
                else if(count_of_life > maplistmax.life){
                    --count_of_life;
                    --j;
                    continue;
                }
                else if(count_of_coin > maplistmax.coin){
                    --count_of_coin;
                    --j;
                    continue;
                }

                this.map[i][j] = this.maplist[random];
            }
        }

        // Check if there is a coin.!!!
        if(count_of_coin == 0)
            this.GenerateRandomMap();
        
    }

    public void Draw()
    {
        for(int i = 0; i < mapsize.y; i++)
        {
            for(int j = 0; j < mapsize.x; j++)
            {
                System.out.print(map[i][j]+" ");
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
