package cs.eng1.piazzapanic.stations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import cs.eng1.piazzapanic.ui.StationActionUI;
import cs.eng1.piazzapanic.ui.StationUIController;
import cs.eng1.piazzapanic.food.ingredients.Ingredient;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class BakingStation extends Station{

    protected final Ingredient[] validIngredients;
    protected Ingredient currentIngredient;
    protected float timeBaked;
    protected float totalTimeToCook = 5f;
    private boolean progressVisible = false;
    private boolean isPowerupUsed = false;
    private final float timeToBurn = 5f;
    private float burnTimer;

    public BakingStation(int id, TextureRegion image, StationUIController uiController, StationActionUI.ActionAlignment alignment, Ingredient[] ingredients) {
        super(id, image, uiController, alignment);
        validIngredients = ingredients;
    }

    public void act(float delta) {
        getInput();
        if(checkIfBurnt(delta)){
            currentIngredient.setIsBurnt(true);
            uiController.showActions(this, getActionTypes());
        }
        if (inUse) {
            timeBaked += delta;
            uiController.updateProgressValue(this, (timeBaked / totalTimeToCook) * 100f);
            if (timeBaked >= totalTimeToCook && progressVisible) {
                currentIngredient.setBaked(true);
                uiController.hideProgressBar(this);
                uiController.showActions(this, getActionTypes());
                progressVisible = false;
                nearbyChef.setPaused(false);
            }
        }
        super.act(delta);
    }

    private boolean checkIfBurnt(float delta){

        if(currentIngredient != null){
            if(currentIngredient.getBaked() && !currentIngredient.getIsBurnt()){
                System.out.println("Burn Timer Increasing");
                burnTimer += delta;
            }
        }
        if(burnTimer >= timeToBurn){
            burnTimer = 0;
            return true;
        }
        else{
            return false;
        }
    }

    private boolean isCorrectIngredient(Ingredient ingredientToCheck) {
        if (!ingredientToCheck.getBaked()) {
            for (Ingredient item : this.validIngredients) {
                if (Objects.equals(ingredientToCheck.getType(), item.getType())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<StationAction.ActionType> getActionTypes() {
        LinkedList<StationAction.ActionType> actionTypes = new LinkedList<>();
        if (nearbyChef == null) {
            return actionTypes;
        }
        if (currentIngredient == null) {
            if (nearbyChef.hasIngredient() && isCorrectIngredient(nearbyChef.getStack().peek())) {
                actionTypes.add(StationAction.ActionType.PLACE_INGREDIENT);
            }
        } else if (currentIngredient.getIsBurnt()) {
            actionTypes.add(StationAction.ActionType.CLEAR_STATION);
            return actionTypes;
        }
        else {
            //check to see if total number of seconds has passed to progress the state of the pizza base.
            if (currentIngredient.getBaked()) {
                actionTypes.add(StationAction.ActionType.GRAB_INGREDIENT);
            }
            if (!inUse) {
                actionTypes.add(StationAction.ActionType.BAKE_ACTION);
            }
        }
        return actionTypes;
    }

    @Override
    public void doStationAction(StationAction.ActionType action) {
        switch (action) {
            case BAKE_ACTION:
                //timeCooked is used to track how long the
                //ingredient has been cooking for.
                timeBaked = 0;
                inUse = true;
                uiController.hideActions(this);
                uiController.showProgressBar(this);
                nearbyChef.setPaused(true);
                progressVisible = true;
                burnTimer = 0;
                break;

            case PLACE_INGREDIENT:
                if (this.nearbyChef != null && nearbyChef.hasIngredient() && currentIngredient == null) {
                    if (this.isCorrectIngredient(nearbyChef.getStack().peek())) {
                        currentIngredient = nearbyChef.placeIngredient();
                    }
                }
                uiController.showActions(this, getActionTypes());
                break;

            case GRAB_INGREDIENT:
                if (nearbyChef.canGrabIngredient()) {
                    nearbyChef.grabIngredient(currentIngredient);
                    currentIngredient = null;
                    inUse = false;
                }
                uiController.showActions(this, getActionTypes());
                break;
            case CLEAR_STATION:
                inUse = false;
                currentIngredient = null;
                progressVisible = false;
                uiController.showActions(this, getActionTypes());
        }
    }

    @Override
    public void reset() {
        timeBaked = 0;
        burnTimer = 0;
        currentIngredient = null;
        progressVisible = false;
        resetCookingSpeed();
        super.reset();
    }


    private void doubleCookingSpeed(){
        totalTimeToCook = 1f;
    }

    private void getInput(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2) && !isPowerupUsed){
            doubleCookingSpeed();
            isPowerupUsed = true;
        }
    }

    private void resetCookingSpeed(){
        totalTimeToCook = 5f;
    }


    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (currentIngredient != null) {
            drawFoodTexture(batch, currentIngredient.getTexture());
        }
    }
}
