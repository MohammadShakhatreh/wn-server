# World Navigator

## TODO List

- Simple frontend for the game
- Implement CI/CD with Jenkins

## Packaging and running 

To get an executable jar file

```
$ maven package
```

After that you can run the game using

Giving that the `maze.json` is in the same directory as the jar file.
```
$ java -jar world-navigator-0.0.1-jar-with-dependencies.jar
```

## WN API Endpoints



## Maze Specification

The maze file is a `json` file and here is the specification to write your own maze.

### Maze object
First the top level object will hold the properties for the maze.

```
{
  "rooms": [ Room ], // Array of rooms that will be in the maze.
}
```

### Room object

```
{
  "sides" : { // defines what the four walls of the room will be.
    "NORTH" : { RoomSide },
    "EAST" : { RoomSide },
    "SOUTH" : { RoomSide },
    "WEST" : { RoomSide }
  },
  "lit" : true, // If the room is lit or not.
  "hasLights": true // If the room has lights.
                    // if this is false so does the lit property.
}
```

### RoomSide object

There are for types of a wall

#### Empty wall

```json
{
  "@type": "wall"
}
```

#### Door

```
{
  "@type": "door",
  "room": Number // next room index 
  "lock": Lock | Number // if Number then it's a predefined lock id 
}
```

#### Chest

```
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

```
{
  "@type": "seller",
  "prices": { // prices list.
    "flashlight": 20, // The description of the item and it's price.
    "blue key": 120,
    "brown key": 200
  }
}
```

#### Mirror and Painting

```
{
  "@type" : "painting", // or "mirror" to define a mirror.
  "item": "brown key"    // The hidden item, if there is no item hidden this proprty can be
                                                negelected.
}
```

### Lock

Applicable for `Door` or `Chest` Objects. 

```
{
    "lock": {
        "key": "blue key", // optional if the lock is always unlocked
        "open": Boolean,
        "locked": Boolean
    }
}
```

### Items

Items are defined by a string representing the item description.

There are two items in the game.

#### Flashlight

It's description is `flashlight`.

#### Key

It's description is `color key` where color is the key color.
 