/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package amara.applications.ingame.client.systems.gui;

import amara.applications.ingame.client.gui.ScreenController_Shop;
import amara.applications.ingame.entitysystem.components.players.PlayerCharacterComponent;
import amara.applications.ingame.entitysystem.components.shop.ShopItemsComponent;
import amara.applications.ingame.entitysystem.components.units.TeamComponent;
import amara.libraries.entitysystem.ComponentMapObserver;
import amara.libraries.entitysystem.EntityWorld;

/**
 *
 * @author Carl
 */
public class DisplayShopItemsSystem extends GUIDisplaySystem<ScreenController_Shop> {

    public DisplayShopItemsSystem(int playerEntity, ScreenController_Shop screenController_shop){
        super(playerEntity, screenController_shop);
    }

    @Override
    protected void update(EntityWorld entityWorld, float deltaSeconds, int characterEntity){
        ComponentMapObserver observer = entityWorld.requestObserver(this, ShopItemsComponent.class);
        for (int shopEntity : observer.getNew().getEntitiesWithAll(ShopItemsComponent.class)) {
            onShopItemsUpdated(entityWorld, shopEntity);
        }
        for (int shopEntity : observer.getChanged().getEntitiesWithAll(ShopItemsComponent.class)) {
            onShopItemsUpdated(entityWorld, shopEntity);
        }
    }

    private void onShopItemsUpdated(EntityWorld entityWorld, int shopEntity) {
        TeamComponent teamComponent = entityWorld.getComponent(shopEntity, TeamComponent.class);
        if ((teamComponent == null) || (teamComponent.getTeamEntity() == getPlayerTeamEntity(entityWorld))) {
            ShopItemsComponent shopItemsComponent = entityWorld.getComponent(shopEntity, ShopItemsComponent.class);
            String[] itemTemplateNames = ((shopItemsComponent != null) ? shopItemsComponent.getItemTemplateNames() : new String[0]);
            screenController.setShopItems(itemTemplateNames);
        }
    }

    private int getPlayerTeamEntity(EntityWorld entityWorld) {
        int characterEntity = entityWorld.getComponent(playerEntity, PlayerCharacterComponent.class).getEntity();
        return entityWorld.getComponent(characterEntity, TeamComponent.class).getTeamEntity();
    }
}
