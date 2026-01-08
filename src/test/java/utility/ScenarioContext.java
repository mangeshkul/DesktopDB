package utility;

import io.cucumber.java.Scenario;

public class ScenarioContext {

	private static final ThreadLocal<Integer> scenarioIndex =
	        ThreadLocal.withInitial(() -> 0);
	
	 private static final ThreadLocal<String> scenarioName =
	            new ThreadLocal<>();

	public static int getNextScenarioIndex() {
	    int current = scenarioIndex.get();
	    scenarioIndex.set(current + 1);
	    return current;
	}

	
	public static void setScenarioName(String name) {
        scenarioName.set(name);
    }

    public static String getScenarioName() {
        return scenarioName.get();
    }
}
