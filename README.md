# World Navigator

A Multi-player text adventure game.

## Packaging and running 

To get an executable jar file

```
$ mvn clean package
```

After that you can run the game using

```
$ java -jar app.jar
```

## Postman API Collection

To see all the endpoints and test them use this 
[Postman Collection](https://www.getpostman.com/collections/1a288a49ef9f5aed7ada).

## Maze Specification

The maze specification to write your own maze.

### Maze Object
First the top level object will hold the properties for the maze.

```json
{
  "rooms": [ Room ]
}
```

### Room Object

```json
{
  "index": Number,  
  "sides" : { // defines what the four walls of the room will be.
    "NORTH" : RoomSide,
    "EAST" : RoomSide,
    "SOUTH" : RoomSide,
    "WEST" : RoomSide
  },
  "lit" : true,
  "hasLights": true 
}
```

### RoomSide Object

There are four types of walls.

#### Wall

```json
{
  "@type": "wall"
}
```

#### Door

```json
{
  "@type": "door",
  "room": Number // next room index or -1 to indecate special door to win.
  "lock": Lock | Number // if Number then it's a predefined lock id 
}
```

#### Chest

```json
{
    "@type": "chest",
    "lock": Lock | Number // as in the door object
    "gold": 20,
    "items": [
        "flashlight",
        "black key",
        "yellow key"
    ]
}
```

#### Seller

```json
{
  "@type": "seller",
  "prices": {
    "flashlight": 20, // The description of the item and it's price.
    "blue key": 120,
    "brown key": 200
  }
}
```

#### Mirror and Painting

```json
{
  "@type" : "painting", // or "mirror" to define a mirror.
  "item": "brown key"
}
```

### Lock

Applicable for `Door` or `Chest` Objects. 

```json
{
    "lock": {
        "@id": Number, // used to refernce the same lock in any place
        "key": "blue key",
        "open": Boolean,
        "locked": Boolean
    }
}
```

### Items

Items are defined by a string representing the item description.

There are two items in the game `flashlight` and `key`.

#### Flashlight

It's description is `flashlight`.

#### Key

It's description is `color key` where color is the key color.
 