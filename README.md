<p align="center">
<img src="readme_image/banner.png" width=500>
 <div align="center">
 <img src="https://img.shields.io/github/issues/TextWar/TextWar">
 <img src="https://img.shields.io/github/forks/TextWar/TextWar">
   <img src="https://img.shields.io/github/stars/TextWar/TextWar">
  <img src="https://coveralls.io/repos/github/TextWar/TextWar/badge.svg?branch=master">
  <img src="https://travis-ci.org/TextWar/TextWar.svg?branch=master">
  <a href="https://gitter.im/mytextwar/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)">
   <img src="https://badges.gitter.im/mytextwar/community.svg" >
  </a>
  <a href="LICENSE">
     <img src="https://img.shields.io/badge/license-TPL-green">
  </a>
  </div>

</p>
<div align="center">
 
# TextWar
###### Logo is deigned by MagicLu
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

<p align="center">
<img src="readme_image/map.png" width=500>
</p>


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
#### WIKI

You can see more in [wiki](https://github.com/TextWar/QQTextWar/wiki)

#### CopyRight

@CopyRight TextWar Developing Studio

</div>










