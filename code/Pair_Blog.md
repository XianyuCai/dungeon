# Assignment II Pair Blog Template

## Task 1) Code Analysis and Refactoring ⛏️

### a) From DRY to Design Patterns

[Links to your merge requests](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T2/teams/W18A_DAIKON/assignment-ii/-/merge_requests/1)

> i. Look inside src/main/java/dungeonmania/entities/enemies. Where can you notice an instance of repeated code? Note down the particular offending lines/methods/fields.

[Answer]
ZombieToast and Mercenary share the same code for calcualting the next position based
on the player's position when player has a Invincibility potion or Invisiblity potion.

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

> ii. What Design Pattern could be used to improve the quality of the code and avoid repetition? Justify your choice by relating the scenario to the key characteristics of your chosen Design Pattern.

[Answer]
Encapsulation of Algorithms:
The Strategy Pattern allows you to encapsulate different algorithms for movement into separate classes. This is useful since different enemies have different movement behaviors based on various conditions like the presence of potions.

Interchangeable Strategies:
By using the Strategy Pattern, you can define a family of algorithms (movement behaviors) and make them interchangeable. This allows each enemy to use different movement strategies without changing their class. For instance, the movement logic when the player has an InvincibilityPotion, InvisibilityPotion, or no potion at all can be encapsulated into separate strategy classes.

Simplified Code Maintenance:
Encapsulating movement logic in separate classes reduces the complexity within each enemy class. This makes the code easier to understand, maintain, and extend. It becomes straightforward to add new movement behaviors or modify existing ones without affecting other parts of the code.

> iii. Using your chosen Design Pattern, refactor the code to remove the repetition.

1. Create a interface that defines the method for movement. MovementStrategy.
2. Implement the specific movement strategy for when the player has a
invicibilty potion or invisibility potion. InvicibiltyMovementStrategy & InvisibilityMovementStrategy.
3. Update the enemy class to hold a reference to MovementStrategy and use it for movement.
4. Set the invincibilityMovementStrategy & InvisibilityMovementStrategy for enemies when required.

### b) Observer Pattern

> Identify one place where the Observer Pattern is present in the codebase, and outline how the implementation relates to the key characteristics of the Observer Pattern.

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

[Answer]

### c) Inheritance Design

[Links to your merge requests](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T2/teams/W18A_DAIKON/assignment-ii/-/merge_requests/2)

> i. Name the code smell present in the above code. Identify all subclasses of Entity which have similar code smells that point towards the same root cause.

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

[Answer]

> ii. Redesign the inheritance structure to solve the problem, in doing so remove the smells.

In the abstract entity class we give the methods a default behaviour,
this allows subclasses to override them only if needed.

### d) More Code Smells

[Links to your merge requests](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T2/teams/W18A_DAIKON/assignment-ii/-/merge_requests/3)

> i. What design smell is present in the above description?

Making changes in "heaps of different places for it to work".
"we abandoned it", didnt even revert to orignial code.
Code is not modular.
Code is not well organized.

[Answer]

> ii. Refactor the code to resolve the smell and underlying problem causing it.

We made the Player class responsible for intating the collection process and each
class that implements InventoryItem knows how to add itself to the players inventory.
This design centralizes the responsbility and reduces coupling.

### e) Open-Closed Goals

[Links to your merge requests](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T2/teams/W18A_DAIKON/assignment-ii/-/merge_requests/4)

> i. Do you think the design is of good quality here? Do you think it complies with the open-closed principle? Do you think the design should be changed?

[Answer]
The switch statement handles different goal types. I believe that the code quality is not
good, because the approach provided requires we modify the achieved and toString
methods every time a new goal type is added, which violates OCP.

> ii. If you think the design is sufficient as it is, justify your decision. If you think the answer is no, pick a suitable Design Pattern that would improve the quality of the code and refactor the code accordingly.

I believe that the strategy pattern would be the most suitable design pattern,
as we have a family of different algorthims in this case goal checking logic and this
would make them interchangable.

### f) Open Refactoring

[Merge Request 1](https://nw-syd-gitlab.cseunsw.tech/COMP2511/24T2/teams/W18A_DAIKON/assignment-ii/-/merge_requests/5)

Encapsulation of State Transitions:

The transitionToNextState method in the PlayerState class encapsulates the logic of transitioning to the next state based on the potion type.
The Player class no longer needs to handle the specifics of state transitions directly.
State-Specific Buffs:

Each state class (BaseState, InvisibleState, InvincibleState) implements the applyBuff method to apply the appropriate buffs.
The Player class calls the applyBuff method on the current state to apply buffs, ensuring that state-specific behavior is encapsulated within the state classes.

Centralized Durability Management: By moving the durability field and related methods (getDurability and use) into the Buildable superclass, we reduce redundancy and centralize durability management.
Parameterization of Subclasses: The Bow and Shield subclasses now pass the durability parameter to the superclass constructor, while handling their specific attributes (like defence) independently.
Maintain Flexibility: Taking parameters like durability in the constructors allows for more flexibility and avoids hard coding specific values directly within the methods.

[Merge Request 2](/put/links/here)

[Briefly explain what you did]

Add all other changes you made in the same format here:

## Task 2) Evolution of Requirements 👽

### a) Microevolution - Enemy Goal

[Links to your merge requests](/put/links/here)

**Assumptions**

The Game class already supports the tracking of defeated enemies and spawners.
The player has methods to interact with and destroy spawners.
The configuration file provides an enemy_goal field specifying the required number of enemies to be defeated.

**Design**

EnemyGoal Class:

Implements GoalStrategy.
Tracks the required number of enemies to defeat as specified in the configuration.
Verifies if both the required number of enemies are defeated and all spawners are destroyed.

Modifications to Game Class:

Add fields and methods to track the number of defeated enemies and the destruction of spawners.
Modifications to ZombieToastSpawner Class:

Ensure the spawner is destroyed upon overlap with another entity.
Correctly update the game state to reflect the spawner's destruction.

**Changes after review**

Added proper documentation to the methods in EnemyGoal.
Refactored the Game class to ensure clarity and separation of concerns.
Ensured that ZombieToastSpawner's onDestroy method correctly updates the game state.

**Test list**

[Test List]

**Other notes**

[Any other notes]

### Choice 1 (A)

[Links to your merge requests](/put/links/here)

**Assumptions**

[Any assumptions made]

**Design**

[Design]

**Changes after review**

[Design review/Changes made]

**Test list**

[Test List]

**Other notes**

[Any other notes]

### Choice 2 (D)

[Links to your merge requests](/put/links/here)

**Assumptions**

[Any assumptions made]

**Design**

[Design]

**Changes after review**

[Design review/Changes made]

**Test list**

[Test List]

**Other notes**

[Any other notes]

### Choice 3 (Insert choice) (If you have a 3rd member)

[Links to your merge requests](/put/links/here)

**Assumptions**

[Any assumptions made]

**Design**

[Design]

**Changes after review**

[Design review/Changes made]

**Test list**

[Test List]

**Other notes**

[Any other notes]

## Task 3) Investigation Task ⁉️

[Merge Request 1](/put/links/here)

[Briefly explain what you did]

[Merge Request 2](/put/links/here)

[Briefly explain what you did]

Add all other changes you made in the same format here:
