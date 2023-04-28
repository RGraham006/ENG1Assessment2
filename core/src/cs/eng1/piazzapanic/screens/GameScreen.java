package cs.eng1.piazzapanic.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import cs.eng1.piazzapanic.PiazzaPanicGame;
import cs.eng1.piazzapanic.chef.ChefManager;
import cs.eng1.piazzapanic.food.CustomerManager;
import cs.eng1.piazzapanic.food.ingredients.Ingredient;
import cs.eng1.piazzapanic.powerups.PowerupManager;
import cs.eng1.piazzapanic.food.recipes.Recipe;
import cs.eng1.piazzapanic.food.FoodTextureManager;
import cs.eng1.piazzapanic.stations.*;
import cs.eng1.piazzapanic.ui.Money;
import cs.eng1.piazzapanic.ui.StationActionUI;
import cs.eng1.piazzapanic.ui.StationUIController;
import cs.eng1.piazzapanic.ui.UIOverlay;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * The screen which can be used to load the tilemap and keep track of everything happening in the
 * game. It does all the initialization and then lets each actor do its actions based on the current
 * frame.
 */
public class GameScreen implements Screen {

  private final Stage stage;
  private final Stage uiStage;
  private final ChefManager chefManager;
  private final OrthogonalTiledMapRenderer tileMapRenderer;
  private final StationUIController stationUIController;
  private final UIOverlay uiOverlay;
  private final FoodTextureManager foodTextureManager;
  private final CustomerManager customerManager;
  private final PowerupManager powerupManager;
  private Preferences save_game = Gdx.app.getPreferences("Saved Game State");
  private boolean isFirstFrame = true;
  private final int mode;
  private final PiazzaPanicGame game;
  private final float tileUnitSize;

  public GameScreen(final PiazzaPanicGame game, final int mode, final int customerNum, final int difficulty) {

    this.game = game;
    this.mode = mode;

    TiledMap map = new TmxMapLoader().load("main-game-map.tmx");
    int sizeX = map.getProperties().get("width", Integer.class);
    int sizeY = map.getProperties().get("height", Integer.class);
    this.tileUnitSize = 1 / (float) map.getProperties().get("tilewidth", Integer.class);

    // Initialise stage and camera
    OrthographicCamera camera = new OrthographicCamera();
    StretchViewport viewport = new StretchViewport(sizeX, sizeY, camera); // Number of tiles
    this.stage = new Stage(viewport);

    ScreenViewport uiViewport = new ScreenViewport();
    this.uiStage = new Stage(uiViewport);
    this.stationUIController = new StationUIController(uiStage, game);
    uiOverlay = new UIOverlay(uiStage, game);

    // Initialise tilemap
    this.tileMapRenderer = new OrthogonalTiledMapRenderer(map, tileUnitSize);
    MapLayer objectLayer = map.getLayers().get("Stations");
    TiledMapTileLayer collisionLayer = (TiledMapTileLayer) map.getLayers().get("Foreground");

    foodTextureManager = new FoodTextureManager();
    chefManager = new ChefManager(tileUnitSize * 2.5f, collisionLayer, uiOverlay);
    customerManager = new CustomerManager(uiOverlay, foodTextureManager, mode , customerNum, difficulty);
    powerupManager = new PowerupManager(chefManager, customerManager, uiOverlay);

    // Add tile objects
    initialiseStations(tileUnitSize, objectLayer);
    chefManager.addChefsToStage(stage);
    powerupManager.addPowerupToStage(stage);

  }

  /**
   * @param tileUnitSize The ratio of world units over the pixel width of a single tile/station.
   * @param objectLayer  The layer on the TMX tilemap which contains all the information about the
   *                     stations and station colliders including position, bounds and station
   *                     capabilities.
   */
  private void initialiseStations(float tileUnitSize, MapLayer objectLayer) {
    Array<TiledMapTileMapObject> tileObjects = objectLayer.getObjects()
        .getByType(TiledMapTileMapObject.class);
    Array<RectangleMapObject> colliderObjects = objectLayer.getObjects()
        .getByType(RectangleMapObject.class);
    HashMap<Integer, StationCollider> colliders = new HashMap<>();

    for (RectangleMapObject colliderObject : new Array.ArrayIterator<>(colliderObjects)) {
      Integer id = colliderObject.getProperties().get("id", Integer.class);
      StationCollider collider = new StationCollider(chefManager);
      Rectangle bounds = colliderObject.getRectangle();
      collider.setBounds(bounds.getX() * tileUnitSize, bounds.getY() * tileUnitSize,
          bounds.getWidth() * tileUnitSize, bounds.getHeight() * tileUnitSize);
      stage.addActor(collider);
      colliders.put(id, collider);
    }

    for (TiledMapTileMapObject tileObject : new Array.ArrayIterator<>(tileObjects)) {
      // Check if it is actually a station
      if (!tileObject.getProperties().containsKey("stationType")) {
        continue;
      }

      // Get basic station properties
      Station station;
      
      int id = tileObject.getProperties().get("id", Integer.class);
      String ingredients = tileObject.getProperties().get("ingredients", String.class);
      StationActionUI.ActionAlignment alignment = StationActionUI.ActionAlignment.valueOf(
          tileObject.getProperties().get("actionAlignment", "TOP", String.class));

      // Initialise specific station types
      switch (tileObject.getProperties().get("stationType", String.class)) {
        case "cookingStation":
          station = new CookingStation(id, tileObject.getTextureRegion(), stationUIController,
              alignment, Ingredient.arrayFromString(ingredients, foodTextureManager), tileObject.getProperties().get("stationLocked", String.class), game);
          break;
        case "ingredientStation":
          station = new IngredientStation(id, tileObject.getTextureRegion(), stationUIController,
              alignment, Ingredient.fromString(ingredients, foodTextureManager));
          break;
        case "choppingStation":
          station = new ChoppingStation(id, tileObject.getTextureRegion(), stationUIController,
              alignment, Ingredient.arrayFromString(ingredients, foodTextureManager), tileObject.getProperties().get("stationLocked", String.class), game);
          break;
        case "recipeStation":
          station = new RecipeStation(id, tileObject.getTextureRegion(), stationUIController,

            alignment, foodTextureManager, customerManager, uiOverlay, mode, game);

          customerManager.addRecipeStation((RecipeStation) station);
          break;
        case "bakingStation":
          station = new BakingStation(id, tileObject.getTextureRegion(), stationUIController,
                  alignment, Ingredient.arrayFromString(ingredients, foodTextureManager), tileObject.getProperties().get("stationLocked", String.class), game);
          break;
        default:
          station = new Station(id, tileObject.getTextureRegion(), stationUIController, alignment);
      }
      float tileX = tileObject.getX() * tileUnitSize;
      float tileY = tileObject.getY() * tileUnitSize;
      float rotation = tileObject.getRotation();

      // Adjust x and y positions based on Tiled quirks with rotation changing the position of the tile
      if (rotation == 90) {
        tileY -= 1;
      } else if (rotation == 180) {
        tileX -= 1;
        tileY -= 1;
      } else if (rotation == -90 || rotation == 270) {
        tileX -= 1;
      }

      station.setBounds(tileX, tileY, 1, 1);
      station.setImageRotation(-tileObject.getRotation());
      stage.addActor(station);

      String colliderIDs = tileObject.getProperties().get("collisionObjectIDs", String.class);
      for (String idString : colliderIDs.split(",")) {
        try {
          Integer colliderID = Integer.parseInt(idString);
          StationCollider collider = colliders.get(colliderID);
          if (collider != null) {
            collider.register(station);
          }
        } catch (NumberFormatException e) {
          System.out.println("Error parsing collider ID: " + e.getMessage());
        }

      }
    }
  }

  public PowerupManager getPowerupManager() {
    return powerupManager;
  }

  @Override
  public void show() {
    InputMultiplexer multiplexer = new InputMultiplexer();
    multiplexer.addProcessor(uiStage);
    multiplexer.addProcessor(stage);
    Gdx.input.setInputProcessor(multiplexer);
    uiOverlay.init();
    isFirstFrame = true;
  }

  @Override
  public void render(float delta) {
    // Initialise screen
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    stage.getCamera().update();
    uiStage.getCamera().update();

    // Render background
    tileMapRenderer.setView((OrthographicCamera) stage.getCamera());
    tileMapRenderer.render();

    // Render stage
    stage.act(delta);
    uiStage.act(delta);

    stage.draw();
    uiStage.draw();

    customerManager.updateCustomerOrders(delta);

    if(chefManager.addThirdChef(tileUnitSize, this.game.shopScreen.getChefUnlocked())){
      chefManager.addChefsToStage(stage);
    }

    if (isFirstFrame) {
      isFirstFrame = false;
    }

    if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_0)){
      getMoney().addMoney(10000);
    }
    if(Gdx.input.isKeyJustPressed(Input.Keys.NUM_9)){
      uiOverlay.addPoint();
    }
    // Save game state
    if(Gdx.input.isKeyJustPressed(Input.Keys.C)){
      saveGame();
    }
    // Load from save file
    if(Gdx.input.isKeyJustPressed(Input.Keys.V)){
      loadGame();
    }
  }

  public Money getMoney() {
    return uiOverlay.getMoney();
  }

  /**
   * Save game as the file location %user%/.prefs.
   * Game is only updated if the game has not been finished.
   */
  public void saveGame(){

    if (uiOverlay.isGameFinished()) {
      return;
    }

    System.out.println("Saved");
    // Save chef information
    for (int i = 0; i < chefManager.getChefs().size(); i++) {
      // Positions
      save_game.putFloat("Chef"+i+"_x", chefManager.getChefs().get(i).getX());
      save_game.putFloat("Chef"+i+"_y", chefManager.getChefs().get(i).getY());
      save_game.putFloat("Chef"+i+"_rotation", chefManager.getChefs().get(i).getRotation());
      
      // Ingredients held
      String ingredientStackAString = chefManager.getChefs().get(i).getStack().toString();
      save_game.putString("Chef"+i+"_ingredientStack", ingredientStackAString);      
    }

    // Save general game information
    save_game.putInteger("Money", getMoney().getMoney());
    save_game.putInteger("Reputation", uiOverlay.getReputation());
    save_game.putFloat("Timer", uiOverlay.getTime());
    
    // Save customers
    save_game.putInteger("Remaining_customers", customerManager.getRemainingCustomers());
    ArrayList<Recipe> orders = customerManager.getCustomerOrders();
    for (Recipe recipe : orders){
      save_game.putString("Customer_order", recipe.getType());
    }
    
    // Push changes onto file
    save_game.flush();
  }

  public void loadGame(){

    System.out.println("Loaded");

    // Load chef information
    for (int i = 0; i < chefManager.getChefs().size(); i++) {
      // Positions
      chefManager.getChefs().get(i).setX(save_game.getFloat("Chef"+i+"_x"));
      chefManager.getChefs().get(i).setY(save_game.getFloat("Chef"+i+"_y"));
      chefManager.getChefs().get(i).setRotation(save_game.getFloat("Chef"+i+"_rotation"));
      
      // Ingredients held
      chefManager.getChefs().get(i).getStack().clear();
      String stackAsString = save_game.getString("Chef"+i+"_ingredientStack");
      stackAsString = stackAsString.substring(1, stackAsString.length() - 1);
      String[] elements = stackAsString.split(", ");

      for (int j = 0; j < elements.length; j++) {
        
        // Remove underscores
        try {
          Ingredient.fromString(elements[j], foodTextureManager);
          System.out.println(elements[j]);
          int underscoreIndex = elements[j].indexOf("_");
          String element_before = elements[j].substring(0, underscoreIndex);
          // Determine if cooked / chopped ... or not
          String element_after = elements[j].substring(underscoreIndex+1, elements[j].length());
          System.out.println("AFTER "+ element_after);
          if (element_before.equals("bun")){
            element_before = "dough";
           }
          Ingredient ing = Ingredient.fromString(element_before, foodTextureManager);
          if (element_after.equals("cooked")){
            
            ing.setIsCooked(true);
            chefManager.getChefs().get(i).setIngredientStack(ing);
          }
          else if (element_after.equals("chopped")){
            ing.setIsChopped(true);
            chefManager.getChefs().get(i).setIngredientStack(ing);
          }
          else if (element_after.equals("baked")){
            ing.setBaked(true);
            chefManager.getChefs().get(i).setIngredientStack(ing);
          }
          else{
            chefManager.getChefs().get(i).setIngredientStack(ing);
          }
        } catch (java.lang.StringIndexOutOfBoundsException e) {
          // Then stack was empty 
        } 
        
      }
      // Customers ??
      
     
    }


    // Load general game information
    getMoney().addMoney(save_game.getInteger("Money"));
    int reputationPoints = save_game.getInteger("Reputation");
    uiOverlay.setCustomPoints(reputationPoints);
    uiOverlay.setTime(save_game.getFloat("Timer"));

  }


  @Override
  public void resize(int width, int height) {
    this.stage.getViewport().update(width, height, true);
    this.uiStage.getViewport().update(width, height, true);
  }

  @Override
  public void pause() {

  }

  @Override
  public void resume() {

  }

  @Override
  public void hide() {

  }

  @Override
  public void dispose() {
    stage.dispose();
    uiStage.dispose();
    tileMapRenderer.dispose();
    foodTextureManager.dispose();
    chefManager.dispose();
  }
}
