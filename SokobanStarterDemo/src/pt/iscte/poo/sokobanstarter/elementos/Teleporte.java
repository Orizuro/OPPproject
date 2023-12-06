package pt.iscte.poo.sokobanstarter.elementos;

import java.util.List;

import pt.iscte.poo.sokobanstarter.GameElement;
import pt.iscte.poo.sokobanstarter.Movable;
import pt.iscte.poo.sokobanstarter.onUpdateElement;
import pt.iscte.poo.utils.Point2D;

public class Teleporte extends GameElement implements onUpdateElement {

    private Point2D otherTeleportPoint2D; // Point2D for the other teleport location

    public Teleporte(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Teleport";
    }

    @Override
    public int getLayer() {
        return 0;
    }
    
    // Method to find the other teleport location
    void findOtherTeleport() {
        for (GameElement gameElement : instance.getUpdateElmets()) {
            if (gameElement instanceof Teleporte) {
                if (!gameElement.getPosition().equals(this.getPosition())) {
                    otherTeleportPoint2D = gameElement.getPosition();
                }
            }
        }
    }

    @Override
    public void elementUpdate() {
        if (otherTeleportPoint2D == null)findOtherTeleport();
      
        // Get elements at this teleport location and the other teleport location
        List<GameElement> thisList = instance.getGameElement(getPosition());
        List<GameElement> otherList = instance.getGameElement(otherTeleportPoint2D);

        for (GameElement elementOnSamePoint : thisList) {
            if (elementOnSamePoint instanceof Movable) {
                // If the movable element is already teleported, exit the method
                if (((Movable) elementOnSamePoint).justTeletrasported()) {
                    return;
                }

                // Check if there are movable elements at the other teleport location
                for (GameElement elementOtherPoint : otherList) {
                    if (elementOtherPoint instanceof Movable) {
                        return;
                    }
                }

                // Teleport the movable element to the other teleport location
                ((Movable) elementOnSamePoint).setPosition(otherTeleportPoint2D);
                ((Movable) elementOnSamePoint).setJustTeletrasported(true);
                return;
            }
        }
        return;
    }
}
