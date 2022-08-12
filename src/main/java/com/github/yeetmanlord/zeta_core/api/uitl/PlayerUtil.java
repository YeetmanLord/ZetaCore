package com.github.yeetmanlord.zeta_core.api.uitl;

import org.bukkit.entity.Player;

import com.github.yeetmanlord.zeta_core.menus.AbstractGUIMenu;

/**
 * Utility class for handling interactions with commands and menus.
 * 
 * @author YeetManLord
 *
 */
public class PlayerUtil {

	private final Player owner;

	private boolean isGUIMenu;

	private boolean takingChatInput;

	private AbstractGUIMenu menuToInputTo;

	private IChatInputable inputObject;

	private InputType inputObjectType;

	public PlayerUtil(Player player) {

		this.setMenuToInputTo(null);
		this.setGUIMenu(false);
		this.setTakingChatInput(false);
		this.owner = player;

	}

	public Player getOwner() {

		return owner;

	}

	public boolean isGUIMenu() {

		return isGUIMenu;

	}

	public void setGUIMenu(boolean isGUIMenu) {

		this.isGUIMenu = isGUIMenu;

	}

	public boolean isTakingChatInput() {

		return takingChatInput;

	}

	public void setTakingChatInput(boolean takingChatInput) {

		this.takingChatInput = takingChatInput;

	}

	public AbstractGUIMenu getMenuToInputTo() {

		return menuToInputTo;

	}

	public void setMenuToInputTo(AbstractGUIMenu menuToInputTo) {

		this.menuToInputTo = menuToInputTo;

	}

	@Override
	public String toString() {

		return "PlayerMenuUtil: {\"Owner\": " + this.getOwner().getName() + ", \"Is Menu Open\": " + this.isGUIMenu + ", \"Is taking chat input\": " + this.takingChatInput + "}";

	}

	public IChatInputable getInputObject() {

		return inputObject;

	}

	public void setInputObject(IChatInputable inputObject) {

		this.inputObject = inputObject;

	}

	public InputType getInputObjectType() {

		return inputObjectType;

	}

	public void setInputObjectType(InputType inputObjectType) {

		this.inputObjectType = inputObjectType;

	}

}
