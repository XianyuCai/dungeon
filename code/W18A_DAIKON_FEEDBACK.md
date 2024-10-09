# Task 1 Code Analysis and Refactoring
## From Dry to Design Patterns
**7/7**

- Nice identification of repeated code in ZombieToast and Mercenary

- Nice use of the strategy pattern

## Observer Pattern
**5/5**

- Very nice identitifcation of the game, as the subject and explanation of subscription system with the understanding of notifying with on destroy will unsubscribe the observers

## Inheritance Design
**2/6**

- Incorrectly identified the code smell but correct explanation (-0.5): The correct code smell is Refused Bequest

- No entities listed (-2)

- Nice identitifcation of methods which cause refused bequest

- Resolution is incorrect as moving the onOverlap, onMovedAway, onDestroy to entity does not fix the issue as the subclasses still inherit and do not implement the methods (-1.5): Correct way is to make these interfaces and have the entities which require each interface implement them

- Nice deletion of unused methods in the (even though they still inherit it's one step closer to the solution)

## More Code Smells
**5/6**

- No code smell identified or explained correctly, the correct answer was Shotgun Surgery, these are found on refactory guru (-1)

- Nice resolution by using the player to pick up

## Open-Closed Goals
**5.5/6**

- Correct explanation

- Misnaming of strategy pattern when implementing composite pattern (-0.5)

- Correct implementation

## Open Refactoring
**4/10**

- Nice refactory of two code refactors, (note this MR also retintroduced Refused Bequest with subclasses inheriting from entity)

- Requires 6 full refactors to get full marks (3 given, 3 additional)

# Task 2 Evolution of Requirements
## Microevolution - Enemy Goal
**Correctness**: 4.4/6

**Design**: 7.5/7.5
- Nice usage of enemy goal with refactor of goals
**Testing**: 1.5/1.5

- Nice testing


## Bosses
**Correctness**: 1.8/4

**Design**: 5/5

- Nice inheritance from zombietoast and mercenary without breaking LSP

**Testing**: 0.75/1

- Good coverage of basic testing of bosses

- Missing randomisation for testing (-0.25)

## Sunstone & more buildables
**Correctness**: 3.6/6

**Design**: 7/7.5
- LSP violation - refused bequest (can’t “use” midnight armour)

**Testing**: 1.5/1.5
- Nice testing

# Task 3
**0/10**

- Missing

# Code Hygiene
- All regression tests remained passing some of the time

- Code Coverage remains above 80%

- The linter remains passing

# Merge Requests X
- Please link your merge requests correctly so I can give you marks

- Some merge requests were extremely long

- Merge Requests that are too large and contain too many changes