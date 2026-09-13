# Peps 2D Game

A 2D Java game in which the player controls a character that moves through the world, runs, jumps, and collects fruits to restore energy.

The project focuses on object-oriented design and uses several design patterns to keep the game systems modular, maintainable, and easy to extend.

## Gameplay

The player controls an avatar that can move across the terrain, run, jump, and collect fruits.

The avatar has an energy level that changes during gameplay. Fruits scattered throughout the world can be collected to restore energy, and the user interface is updated whenever the player's energy changes.

Trees are generated throughout the world and consist of trunks, animated leaves, and collectible fruits.

## Project Structure

### Avatar Package

The `avatar` package is responsible for the player character, its movement states, and its energy system.

#### `Avatar`

The main player entity.

Responsibilities:
- Initializes the player's animations.
- Stores and manages the current energy level.
- Acts as the publisher in the Observer pattern.
- Notifies subscribers whenever the player's energy changes.

#### `State`

An interface representing the current behavior of the player.

It defines methods for:
- Handling input-driven state transitions.
- Executing state-specific logic through `stateRules`.

#### `IdleState`

Implements the `State` interface and controls the avatar while it is standing on the ground.

#### `RunState`

Implements the `State` interface and controls the avatar while it is running left or right.

#### `JumpState`

Implements the `State` interface and controls the avatar while it is in the air.

#### `EnergyObserver`

An interface for objects that need to receive updates when the player's energy level changes.

#### `EnergyNum`

A text-based game object that implements `EnergyObserver`.

It receives energy updates from the avatar and displays the player's current energy level.

## Avatar Package – OOP Design

### State Design Pattern

The player's movement behavior is divided into separate state classes.

This keeps movement logic out of the main `Avatar` class and makes it easier to introduce additional states in the future.

It supports:
- Single Responsibility Principle
- Open/Closed Principle

### Observer Design Pattern

The energy display is implemented using the Observer pattern.

`Avatar` acts as the publisher, while `EnergyNum` acts as a subscriber.

Whenever the player's energy changes, the UI is updated without tightly coupling the display logic to the player implementation.

### Program to an Interface

The `Avatar` class interacts with the `State` and `EnergyObserver` interfaces rather than depending directly on specific implementations.

This reduces coupling and makes the system easier to extend.

---

## Trees Package

The `trees` package handles tree generation and all tree-related game objects.

### `Flora`

Responsible for generating trees within a given world range.

Tree generation uses deterministic randomness, meaning that the same location in the world will generate the same trees when revisited.

### `Tree`

Represents a complete tree and acts as a facade for its internal structure.

It coordinates the creation and management of:
- Trunks
- Leaves
- Fruits

Other parts of the game can work with a `Tree` without needing to know how each individual component is created.

### `Trunk`

Creates the blocks that form the tree trunk.

The trunk blocks participate in collision handling.

### `Leaf`

Creates leaf objects and controls their animations.

Leaves react to wind by:
- Rotating over time.
- Changing their width to create a movement effect.

### `Fruit`

Creates collectible fruits.

When a fruit is collected:
- The player's energy is increased.
- The fruit disappears.
- It reappears after one full day cycle.

## Trees Package – OOP Design

### Facade Design Pattern

`Tree` acts as a facade for the entire tree structure.

The game does not need to manage trunks, leaves, and fruits separately. Instead, it can interact with a single `Tree` object.

### Factory Method Pattern

`Tree`, `Trunk`, `Leaf`, and `Fruit` use static `create` methods.

These methods encapsulate object creation and initialization logic instead of exposing those details to the rest of the game.

### Single Responsibility Principle

Each class handles a specific responsibility:

- `Flora` generates trees.
- `Tree` manages the tree structure.
- `Trunk` creates trunks.
- `Leaf` manages leaf behavior and animation.
- `Fruit` manages collection, energy restoration, and respawning.

### Deterministic Tree Generation

`Flora` generates trees using a deterministic random seed based on the world seed and tree position.

As a result, returning to the same world location recreates the same trees.

### Program to an Interface

`Fruit` does not depend directly on the `Avatar` class.

Instead, a `Consumer<Float>` callback is passed through `Flora` and `Tree` into `Fruit`.

When the fruit is collected, it invokes this callback to update the player's energy.

This keeps the vegetation system independent from the avatar implementation.

---

## World Management

### `MiniWorld`

`MiniWorld` represents a fixed horizontal section of the game world.

It tracks the game objects that belong to its X-coordinate range, including terrain blocks and trees.

Its `remove()` method handles the cleanup of the objects associated with that area.

### OOP Design

`MiniWorld` follows the Single Responsibility Principle by managing the lifecycle of objects belonging to a specific world range.

It also uses encapsulation and information hiding by keeping the cleanup implementation inside the `MiniWorld` class.

---

## API Changes

A public constant named `GROUND_TAG` was added to `Block`.

This avoids repeated magic strings when referring to ground objects.

No existing public API methods were modified.

## Main OOP Concepts Used

The project demonstrates several object-oriented programming concepts and design principles:

- State Design Pattern
- Observer Design Pattern
- Facade Design Pattern
- Factory Method Pattern
- Single Responsibility Principle
- Open/Closed Principle
- Encapsulation and Information Hiding
- Programming to Interfaces
- Dependency reduction through callbacks
- Deterministic world generation
