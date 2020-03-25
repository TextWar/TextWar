<p align="center">
<img src="readme_image/logo.png" width=500>
</p>


# QQTextWar
### A Simple game for chatting software

This game is based on Unicode character design, 
you can stitch your map by characters, and build your own server and player battle.

This RPG game is designed with NPCs, monsters, creatures, skill points.

And will be designed to interface with all chat software.

This game is based on the java,groovy,python.
#### Matching components

We wrote a game's map generator and map editor using python.

Data structure with json as map.

If you want to make and modify maps, use the [MapEditor](https://github.com/TextWar/Textwar-MapEditor)

You should get the [Map generator](https://github.com/TextWar/textwar-py) to run it.

![editor](readme_image/editor.png)

![map](readme_image/map.png)

You can see more in [wiki](https://github.com/TextWar/QQTextWar/wiki)

#### API

We will support the api for the TextWar plugins.

such as

```groovy
 GameMap map = new GameMap(EXAMPLE_MAP);
        Player player = new Player(new Vector(0,0),192992929,1,1,1)
                .addInto(map)
                .as(Player.class);
        player.move(new Vector(1,2),map).update();
        Mob mob = new Slime(new Vector(1,1),0)
                .addInto(map)
                .as(Mob.class);
        mob.move(new Vector(3,1),map).update();
```







