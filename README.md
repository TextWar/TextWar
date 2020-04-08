<p align="center">
<img src="readme_image/banner.png" width=500>
<div align="center">
 <a href="https://github.com/TextWar/TextWar/network/members">
 <img src="https://img.shields.io/github/forks/TextWar/TextWar?style=social">
 </a>
 <a href="https://github.com/TextWar/TextWar/stargazers">
 <img src="https://img.shields.io/github/stars/TextWar/TextWar?style=social">
 </a>
</div>
 <div align="center">
 <img src="https://img.shields.io/github/v/release/TextWar/TextWar?include_prereleases">
 <img src="https://img.shields.io/github/languages/code-size/TextWar/TextWar">
 <a href="https://github.com/TextWar/TextWar/issues">
    <img src="https://img.shields.io/github/issues/TextWar/TextWar">
 </a>
 <img src="https://coveralls.io/repos/github/TextWar/TextWar/badge.svg?branch=master">
 <a href="http://hits.dwyl.com/TextWar/TextWar">
 <img src="http://hits.dwyl.com/TextWar/TextWar.svg">
 </a>
 
 <a href="https://gitter.im/mytextwar/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)">
    <img src="https://badges.gitter.im/mytextwar/community.svg" >
   </a>
  <a href = "https://travis-ci.org/github/TextWar/TextWar/builds/671445536?utm_source=github_status&utm_medium=notification">
  <img src="https://travis-ci.org/TextWar/TextWar.svg?branch=master">
 </a>
  <a href="https://github.com/TextWar/TextWar/search?l=java">
  <img src="https://img.shields.io/github/languages/count/TextWar/TextWar">
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

#### How To Download the binary source?

For Linux:

`git clone https://github.com/TextWar/TextWar-Kit.git`

`cd TextWar-Kit/download_env` 

`sh download.sh` to check your java development kit and python 3

`sh get_source.sh` to download the source of the TextWar and compile it to jar file

#### CopyRight

@CopyRight TextWar Developing Studio

</div>










