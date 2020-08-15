package com.worldnavigator;

import com.worldnavigator.web.entities.MazeTemplate;
import com.worldnavigator.web.repositories.MazeTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class WorldNavigatorApplication implements CommandLineRunner {

    private final String twoRoomsMaze = "{\"rooms\":[{\"index\":0,\"sides\":{\"NORTH\":{\"@type\":\"seller\",\"prices\":{\"flashlight\":20,\"blue key\":40,\"brown key\":30}},\"EAST\":{\"@type\":\"door\",\"room\":1,\"lock\":{\"@id\":1,\"key\":\"blue key\",\"open\":true,\"locked\":false}},\"SOUTH\":{\"@type\":\"painting\",\"item\":\"brown key\"},\"WEST\":{\"@type\":\"mirror\"}},\"lit\":true,\"hasLights\":true},{\"index\":1,\"sides\":{\"NORTH\":{\"@type\":\"chest\",\"lock\":{\"@id\":2,\"key\":\"brown key\",\"open\":false,\"locked\":true},\"gold\":200,\"items\":[\"flashlight\",\"black key\",\"yellow key\"]},\"EAST\":{\"@type\":\"wall\"},\"SOUTH\":{\"@type\":\"door\",\"room\":-1},\"WEST\":{\"@type\":\"door\",\"room\":0,\"lock\":1}},\"lit\":true,\"hasLights\":true}]}";
    private final String nineRoomsMaze = "{\"rooms\":[{\"index\":0,\"sides\":{\"NORTH\":{\"@type\":\"seller\",\"prices\":{\"flashlight\":20,\"blue key\":40,\"brown key\":30}},\"EAST\":{\"@type\":\"door\",\"room\":1,\"lock\":{\"@id\":1,\"key\":\"blue key\",\"open\":false,\"locked\":true}},\"SOUTH\":{\"@type\":\"painting\",\"item\":\"brown key\"},\"WEST\":{\"@type\":\"mirror\"}},\"lit\":true,\"hasLights\":true},{\"index\":1,\"sides\":{\"NORTH\":{\"@type\":\"chest\",\"gold\":200,\"items\":[\"flashlight\",\"black key\",\"yellow key\"]},\"EAST\":{\"@type\":\"door\",\"room\":2},\"SOUTH\":{\"@type\":\"door\",\"room\":4},\"WEST\":{\"@type\":\"door\",\"room\":0,\"lock\":1}},\"lit\":false,\"hasLights\":true},{\"index\":2,\"sides\":{\"NORTH\":{\"@type\":\"wall\"},\"EAST\":{\"@type\":\"door\",\"room\":3},\"SOUTH\":{\"@type\":\"wall\"},\"WEST\":{\"@type\":\"door\",\"room\":1}},\"lit\":true,\"hasLights\":true},{\"index\":3,\"sides\":{\"NORTH\":{\"@type\":\"door\",\"room\":-1},\"EAST\":{\"@type\":\"painting\",\"item\":\"gray key\"},\"SOUTH\":{\"@type\":\"wall\"},\"WEST\":{\"@type\":\"door\",\"room\":2}},\"lit\":true,\"hasLights\":true},{\"index\":4,\"sides\":{\"NORTH\":{\"@type\":\"door\",\"room\":1},\"EAST\":{\"@type\":\"wall\"},\"SOUTH\":{\"@type\":\"door\",\"room\":6},\"WEST\":{\"@type\":\"wall\"}},\"lit\":false,\"hasLights\":true},{\"index\":5,\"sides\":{\"NORTH\":{\"@type\":\"wall\"},\"EAST\":{\"@type\":\"wall\"},\"SOUTH\":{\"@type\":\"door\",\"room\":8},\"WEST\":{\"@type\":\"wall\"}},\"lit\":true,\"hasLights\":true},{\"index\":6,\"sides\":{\"NORTH\":{\"@type\":\"door\",\"room\":4},\"EAST\":{\"@type\":\"door\",\"room\":7},\"SOUTH\":{\"@type\":\"door\",\"room\":-1},\"WEST\":{\"@type\":\"wall\"}},\"lit\":true,\"hasLights\":true},{\"index\":7,\"sides\":{\"NORTH\":{\"@type\":\"wall\"},\"EAST\":{\"@type\":\"door\",\"room\":8},\"SOUTH\":{\"@type\":\"wall\"},\"WEST\":{\"@type\":\"door\",\"room\":6}},\"lit\":true,\"hasLights\":true},{\"index\":8,\"sides\":{\"NORTH\":{\"@type\":\"door\",\"room\":5},\"EAST\":{\"@type\":\"mirror\",\"item\":\"cyan key\"},\"SOUTH\":{\"@type\":\"wall\"},\"WEST\":{\"@type\":\"door\",\"room\":7}},\"lit\":true,\"hasLights\":true}]}";

    private final MazeTemplateRepository mazeTemplateRepository;

    @Autowired
    public WorldNavigatorApplication(MazeTemplateRepository mazeTemplateRepository) {
        this.mazeTemplateRepository = mazeTemplateRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(WorldNavigatorApplication.class, args);
    }

    @Override
    public void run(String... args) {
        List<MazeTemplate> mazes = mazeTemplateRepository.findAll();

        if(mazes.isEmpty()) {
            mazeTemplateRepository.save(new MazeTemplate(0, "2 Rooms", twoRoomsMaze));
            mazeTemplateRepository.save(new MazeTemplate(0, "9 Rooms", nineRoomsMaze));
        }
    }
}
