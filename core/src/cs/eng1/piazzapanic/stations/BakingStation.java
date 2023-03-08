package cs.eng1.piazzapanic.stations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import cs.eng1.piazzapanic.food.ingredients.Patty;
import cs.eng1.piazzapanic.ui.StationActionUI;
import cs.eng1.piazzapanic.ui.StationUIController;
import cs.eng1.piazzapanic.food.ingredients.Ingredient;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class BakingStation extends Station{

    protected final Ingredient[] validIngredients;
    protected Ingredient currentIngredient;
    protected float timeCooked;
    protected float totalTimeToCook = 10f;
    private boolean progressVisible = false;
    private boolean isPowerupUsed = false;


    public BakingStation(int id, TextureRegion image, StationUIController uiController, StationActionUI.ActionAlignment alignment, Ingredient[] ingredients) {
        super(id, image, uiController, alignment);
        validIngredients = ingredients;
    }

    @Override
    public void reset() {
        timeCooked = 0;
        currentIngredient = null;
        progressVisible = false;
        resetCookingSpeed();
        super.reset();
    }

    public void act(float delta) {
        getInput();
        if (inUse) {
            timeCooked += delta;
            uiController.updateProgressValue(this, (timeCooked / totalTimeToCook) * 100f);
            if (timeCooked >= totalTimeToCook && progressVisible) {
                if (currentIngredient instanceof Patty && !((Patty) currentIngredient).getIsHalfCooked()) {
                    ((Patty) currentIngredient).setHalfCooked();
                } else if (currentIngredient instanceof Patty
                        && ((Patty) currentIngredient).getIsHalfCooked() && !currentIngredient.getIsCooked()) {
                    currentIngredient.setIsCooked(true);
                    resetCookingSpeed();
                }
                uiController.hideProgressBar(this);
                progressVisible = false;
                uiController.showActions(this, getActionTypes());
                nearbyChef.setPaused(false);
            }
        }
        super.act(delta);
    }

    private boolean isCorrectIngredient(Ingredient ingredientToCheck) {
        if (!ingredientToCheck.getIsCooked()) {
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
        } else {
            //check to see if total number of seconds has passed to progress the state of the patty.
            if (currentIngredient.getIsCooked()) {
                actionTypes.add(StationAction.ActionType.GRAB_INGREDIENT);
            }
            if (!inUse) {
                actionTypes.add(StationAction.ActionType.COOK_ACTION);
            }
        }
        return actionTypes;
    }

    @Override
    public void doStationAction(StationAction.ActionType action) {
        switch (action) {
            case COOK_ACTION:
                //timeCooked is used to track how long the
                //ingredient has been cooking for.
                timeCooked = 0;
                inUse = true;
                uiController.hideActions(this);
                uiController.showProgressBar(this);
                nearbyChef.setPaused(true);
                progressVisible = true;
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
        }
    }

    private void doubleCookingSpeed(){
        totalTimeToCook = 5f;
    }

    private void getInput(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_3) && !isPowerupUsed){
            doubleCookingSpeed();
            isPowerupUsed = true;
        }
    }

    private void resetCookingSpeed(){
        totalTimeToCook = 10f;
    }

    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (currentIngredient != null) {
            drawFoodTexture(batch, currentIngredient.getTexture());
        }
    }
}
