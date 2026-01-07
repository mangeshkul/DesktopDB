package utility;

import io.cucumber.java.Scenario;

public class ScenarioContext {

	private static final ThreadLocal<Integer> scenarioIndex =
	        ThreadLocal.withInitial(() -> 0);

	public static int getNextScenarioIndex() {
	    int current = scenarioIndex.get();
	    scenarioIndex.set(current + 1);
	    return current;
	}

}
