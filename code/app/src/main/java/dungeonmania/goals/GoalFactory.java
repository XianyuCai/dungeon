package dungeonmania.goals;

import org.json.JSONArray;
import org.json.JSONObject;

public class GoalFactory {
    public static Goal createGoal(JSONObject jsonGoal, JSONObject config) {
        JSONArray subgoals;
        Goal goal1;
        Goal goal2;
        switch (jsonGoal.getString("goal")) {
        case "AND":
            subgoals = jsonGoal.getJSONArray("subgoals");
            goal1 = createGoal(subgoals.getJSONObject(0), config);
            goal2 = createGoal(subgoals.getJSONObject(1), config);
            return new Goal(new AndGoal(goal1, goal2));
        case "OR":
            subgoals = jsonGoal.getJSONArray("subgoals");
            goal1 = createGoal(subgoals.getJSONObject(0), config);
            goal2 = createGoal(subgoals.getJSONObject(1), config);
            return new Goal(new OrGoal(goal1, goal2));
        case "exit":
            return new Goal(new ExitGoal());
        case "boulders":
            return new Goal(new BouldersGoal());
        case "treasure":
            int treasureGoal = config.optInt("treasure_goal", 1);
            return new Goal(new TreasureGoal(treasureGoal));
        case "enemies":
            int enemyGoal = config.optInt("enemy_goal", 1);
            return new Goal(new EnemyGoal(enemyGoal));
        default:
            return null;
        }
    }
}
