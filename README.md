# Bomberman UET20

![Java](https://img.shields.io/badge/Java-11%2B-orange?logo=openjdk&logoColor=white)
![JavaFX](https://img.shields.io/badge/JavaFX-OpenJFX-blue?logo=java&logoColor=white)
![Platform](https://img.shields.io/badge/Platform-Linux%20%7C%20Windows%20%7C%20macOS-lightgrey)
![License](https://img.shields.io/badge/License-MIT-green)

A classic Bomberman clone built with JavaFX. Originally developed as a UET (University of Engineering and Technology) OOP course project in 2020.

## Game Features

- Single-player Bomberman gameplay on a 20x13 tile grid
- 2 enemy types: **Balloom** (random walk) and **Oneal** (faster, unpredictable)
- Bomb placement with timed explosions that spread in 4 directions
- Destructible brick walls
- Power-up items: Bomb++, Flame++, Speed++
- 3-life system with respawn
- Animated menu screen
- Christmas snow mode

## Controls

| Key | Action |
|-----|--------|
| W / Arrow Up | Move up |
| S / Arrow Down | Move down |
| A / Arrow Left | Move left |
| D / Arrow Right | Move right |
| Space | Place bomb |

## System Requirements

- **Java**: JDK 11 or higher (tested on JDK 21)
- **JavaFX**: OpenJFX (required separately for JDK 11+)

> **Note:** JDK 8 bundled JavaFX, but JDK 11+ requires it as a separate dependency.

## System Compatibility

| Platform | Status | Notes |
|----------|--------|-------|
| Linux (Ubuntu/Debian) | Tested | MP3 audio may not work due to missing codecs in OpenJFX — game runs silently |
| Windows | Compatible | Requires JavaFX SDK |
| macOS | Compatible | Requires JavaFX SDK |

## Installation & Running

### 1. Clone the repository

```bash
git clone https://github.com/hoangphi01/javaBombermanUET20.git
cd javaBombermanUET20
```

### 2. Install JavaFX

**Linux (Ubuntu/Debian):**
```bash
sudo apt-get install -y openjfx
```

**Windows / macOS:**

Download the JavaFX SDK from [https://openjfx.io](https://openjfx.io) and extract it. Set the path accordingly in the commands below (replace `/usr/share/openjfx/lib` with your extracted SDK's `lib` folder).

### 3. Compile

```bash
find src -name "*.java" > /tmp/sources.txt
javac --module-path /usr/share/openjfx/lib \
      --add-modules javafx.controls,javafx.media \
      -d out -sourcepath src @/tmp/sources.txt
```

Copy game resources to the output directory:
```bash
cp -r src/textures src/Music src/font src/levels out/
```

### 4. Run

```bash
java --module-path /usr/share/openjfx/lib \
     --add-modules javafx.controls,javafx.media \
     -cp out uet.oop.bomberman.BombermanGame
```

## Project Structure

```
bomberman20/
├── src/
│   ├── uet/oop/bomberman/
│   │   ├── BombermanGame.java        # Main game class & entry point
│   │   ├── Update.java               # Game logic & state updates
│   │   ├── Map.java                  # Game map management
│   │   ├── gameBackground.java       # Menu screen UI
│   │   ├── gameOver.java             # Game over screen
│   │   ├── gameSnow.java             # Christmas snow effect
│   │   ├── gameSound.java            # Sound management
│   │   ├── entities/
│   │   │   ├── Entity.java           # Base entity class
│   │   │   ├── MovingEntity.java     # Moving entities (player, enemies)
│   │   │   ├── StaticEntity.java     # Walls, grass, items
│   │   │   ├── Bomber.java           # Player character
│   │   │   ├── Bomb.java             # Bomb entity
│   │   │   └── Flame.java            # Explosion effect
│   │   └── graphics/
│   │       ├── Sprite.java           # Sprite & animation management
│   │       └── SpriteSheet.java      # Spritesheet loading
│   ├── textures/                     # Game sprites & images
│   ├── Music/                        # Sound effects & music
│   ├── font/                         # Custom pixel font
│   └── levels/                       # Level configuration files
└── README.md
```

## Known Issues

- **MP3 audio on Linux**: OpenJFX on Linux lacks MP3 codec support. The game handles this gracefully and runs without sound.
- **Power-ups**: Bomb++, Flame++, and Speed++ items are partially implemented — they appear on the map but their effects are not fully wired up.
