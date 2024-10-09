## Week 5

- List the tasks you completed this week here

## Week 6
i. Look inside src/main/java/dungeonmania/entities/enemies. Where can you notice an instance of repeated code? Note down the particular offending lines/methods/fields.

if (map.getPlayer().getEffectivePotion() instanceof InvincibilityPotion) {
            Position plrDiff = Position.calculatePositionBetween(map.getPlayer().getPosition(), getPosition());

            Position moveX = (plrDiff.getX() >= 0) ? Position.translateBy(getPosition(), Direction.RIGHT)
                    : Position.translateBy(getPosition(), Direction.LEFT);
            Position moveY = (plrDiff.getY() >= 0) ? Position.translateBy(getPosition(), Direction.UP)
                    : Position.translateBy(getPosition(), Direction.DOWN);
            Position offset = getPosition();
            if (plrDiff.getY() == 0 && map.canMoveTo(this, moveX))
                offset = moveX;
            else if (plrDiff.getX() == 0 && map.canMoveTo(this, moveY))
                offset = moveY;
            else if (Math.abs(plrDiff.getX()) >= Math.abs(plrDiff.getY())) {
                if (map.canMoveTo(this, moveX))
                    offset = moveX;
                else if (map.canMoveTo(this, moveY))
                    offset = moveY;
                else
                    offset = getPosition();
            } else {
                if (map.canMoveTo(this, moveY))
                    offset = moveY;
                else if (map.canMoveTo(this, moveX))
                    offset = moveX;
                else
                    offset = getPosition();
            }
            nextPos = offset;
}

ii. What Design Pattern could be used to improve the quality of the code and avoid repetition? Justify your choice by relating the scenario to the key characteristics of your chosen Design Pattern.

1. Create a interface that defines the method for movement. MovementStrategy.
2. Implement the specific movement strategy for when the player has a
invicibilty potion or invisibility potion. InvicibiltyMovementStrategy & InvisibilityMovementStrategy.
3. Update the enemy class to hold a reference to MovementStrategy and use it for movement.
4. Set the invincibilityMovementStrategy & InvisibilityMovementStrategy for enemies when required.

Identify one place where the Observer Pattern is present in the codebase, and outline how the implementation relates to the key characteristics of the Observer Pattern.

public void onDestroy(GameMap map) {
        Game g = map.getGame();
        g.unsubscribe(getId());
    }

onDestroy found in Enemey and ZombieToastSpawner, is a observer pattern as it has
a subject (Game g) and every entity that extends enemy is a observer, that can
subscribe or unsubscribe from the games list of entities.
When a entity is destroyed it unsubscribes from the Game, this action is the
observers way of notifying the subject (Game) to remove the entity from the list
of active entities.

i. Name the code smell present in the above code. Identify all subclasses of Entity which have similar code smells that point towards the same root cause.

@Override
public void onOverlap(GameMap map, Entity entity) {
    return;
}

@Override
public void onMovedAway(GameMap map, Entity entity) {
    return;
}

@Override
public void onDestroy(GameMap gameMap) {
    return;
}

Uncessary Overriding.
Uncessary methods.
All these methods have a similar parameters and return the same thing.

Collectable entities are a big problem. We tried to change the way picking up items is handled, to be done at the player level instead of within the entity itself but found that we had to start making changes in heaps of different places for it to work, so we abandoned it.

i. What code smell is present in the above description?
Making changes in "heaps of different places for it to work".
"we abandoned it", didnt even revert to orignial code.
Code is not modular.
Code is not well organized.

i. Do you think the design is of good quality here? Do you think it complies with the open-closed principle? Do you think the design should be changed?

The design quality of this code is not good as it does not follow the open-closed principle.
The code cannot be extended without modifiying the existing code.

ii. If you think the design is sufficient as it is, justify your decision. If you think the answer is no, pick a suitable Design Pattern that would improve the quality of the code and refactor the code accordingly.

I believe that the strategy pattern would be the most suitable design pattern,
as we have a family of different algorthims in this case goal checking logic and
would make them interchangable.


## Week 8
[Merge Request: ](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T2/teams/W18A_DAIKON/assignment-ii/-/merge_requests/8)

* Implemented new boss entities: Assassin and Hydra

## Assassin (extending from Mercenary):
  - Added bribeFailRate attribute to handle bribe failure chance
  - Overrode interact method to implement bribe failure logic
  - Implemented special movement behavior when player is invisible

## Hydra (extending from ZombieToast):
  - Added healthIncreaseRate and healthIncreaseAmount attributes
  - Overrode getBattleStatistics method to implement health increase chance during battles
  - Implemented static final variables for default values (health, attack, health increase rate, and amount)
  - Maintained ZombieToast movement behavior

## Updated EntityFactory to support creation of Assassin and Hydra entities:
  - Added buildAssassin and buildHydra methods
  - Modified constructEntity method to handle new entity types

## Updated config file reading in EntityFactory for new attributes:
  - Assassin: assassin_health, assassin_attack, assassin_bribe_amount, assassin_bribe_fail_rate
  - Hydra: hydra_health, hydra_attack, hydra_health_increase_rate, hydra_health_increase_amount

* Ensured backwards compatibility with existing configuration files

* Added new test cases to verify the behavior of Assassin and Hydra entities

* Refined implementations based on testing results:
  - Fixed bugs related to bribe mechanics for Assassin
  - Adjusted Hydra's health increase logic for better game balance

* Updated relevant documentation to reflect new boss entities and their behaviors


## Week 9

[Merge Request: ](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T2/teams/W18A_DAIKON/assignment-ii/-/merge_requests/9)

* Implementing Sun Stone & More Buildables in Dungeon Mania

### 1. Sun Stone Implementation

We created a new `SunStone` class that extends the `Entity` class and implements the `InventoryItem` interface. Key features include:
- Can be collected by the player
- Used to open doors without being consumed
- Counts as treasure for goal conditions
- Can be used in crafting without being consumed (except when explicitly listed as an ingredient)

We updated the `Player` class to handle Sun Stone collection and the `Door` class to allow opening with a Sun Stone.

### 2. Sceptre Implementation

We implemented the Sceptre as a new buildable item with the following characteristics:
- Can be crafted using (1 wood OR 2 arrows) + (1 key OR 1 treasure) + (1 sun stone)
- Allows mind control of mercenaries and assassins without distance constraints
- Mind control effect lasts for a configurable duration

We updated the `Player` class to handle Sceptre crafting and usage, and modified the `Mercenary` and `Assassin` classes to implement the mind control effect.

### 3. Midnight Armour Implementation

We created a `MidnightArmour` class as another buildable item:
- Can be crafted with 1 sword + 1 sun stone
- Cannot be built if there are zombies in the dungeon
- Provides both attack and defense bonuses to the player

We updated the `Player` class for crafting logic and battle calculations to account for Midnight Armour's effects.

### 4. EntityFactory Updates

We modified the `EntityFactory` class to handle the creation of Sun Stones and updated the building logic for Sceptres and Midnight Armour.

### 5. Configuration and Map Updates

We updated the configuration file handling to include new parameters such as:
- `mind_control_duration`
- `midnight_armour_attack`
- `midnight_armour_defence`

We also created new map files to test these new features.

### 6. Testing

We developed a comprehensive suite of unit tests to ensure the correct functionality of these new features, including:
- Sun Stone collection and usage
- Sceptre crafting with various material combinations
- Sceptre mind control functionality and duration
- Midnight Armour crafting and its effects in battle
