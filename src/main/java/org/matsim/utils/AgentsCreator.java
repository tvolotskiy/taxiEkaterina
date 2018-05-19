package org.matsim.utils;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.*;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.controler.Controler;
import org.matsim.core.scenario.ScenarioUtils;


import java.net.URL;

public class AgentsCreator {
    public static void main(String[] args) {
        String configFile = "scenarios/braess/BraessConfig.xml";
        Config config = ConfigUtils.loadConfig(configFile);
        Scenario scenario = ScenarioUtils.loadScenario(config);
        PopulationFactory populationFactory = scenario.getPopulation().getFactory();

        create1000agents(populationFactory, scenario);
        Controler controler = new Controler(scenario);
        controler.run();

    }

    private static void create1000agents(PopulationFactory populationFactory, Scenario scenario) {

        for (int i = 0; i < 1000; i++) {
            Activity homeActivity = populationFactory.createActivityFromCoord("h",new Coord(1000,-6000));
            homeActivity.setEndTime(6 * 3600 + Math.random() * 600);
            Activity workActivity = populationFactory.createActivityFromCoord("w",new Coord(1000,0));
            workActivity.setEndTime(homeActivity.getEndTime() + 8 * 3600 + 6 * 60);
            Activity homeActivity2 = populationFactory.createActivityFromCoord("h",new Coord(1000,-6000));
            homeActivity2.setEndTime(24 *3600);
            Plan plan = populationFactory.createPlan();
            plan.addActivity(homeActivity);
            Leg carLeg = populationFactory.createLeg("car");
            plan.addLeg(carLeg);
            plan.addActivity(workActivity);
            plan.addLeg(carLeg);
            plan.addActivity(homeActivity2);
            Person person = populationFactory.createPerson(Id.createPersonId("test" + i + 20));
            person.addPlan(plan);
            scenario.getPopulation().addPerson(person);

        }

    }
}
