package plugin.nomore.nmputils.api;

import net.runelite.api.Client;
import net.runelite.api.EquipmentInventorySlot;
import net.runelite.api.InventoryID;
import net.runelite.api.ItemContainer;

import javax.inject.Inject;

public class EquipmentAPI
{

    @Inject
    private Client client;

    @Inject
    private StringAPI string;

    public boolean isWeilding(int itemId)
    {

        assert client.isClientThread();

        ItemContainer equipmentContainer = client.getItemContainer(InventoryID.EQUIPMENT);
        if (equipmentContainer == null)
        {
            return false;
        }

        return equipmentContainer.getItem(EquipmentInventorySlot.WEAPON.getSlotIdx()).getId() == itemId;
    }

    public boolean isWeilding(String itemName)
    {

        assert client.isClientThread();

        ItemContainer equipmentContainer = client.getItemContainer(InventoryID.EQUIPMENT);
        if (equipmentContainer == null)
        {
            return false;
        }

        return string.removeWhiteSpaces(client.getItemDefinition(equipmentContainer
                .getItem(EquipmentInventorySlot.WEAPON.getSlotIdx())
                .getId())
                .getName())
                .contains(string.removeWhiteSpaces(itemName));
    }
}