package com.github.yeetmanlord.zeta_core.api.util.input;

import com.github.yeetmanlord.zeta_core.ZetaCore;
import org.bukkit.Bukkit;
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

	/**
	 * @return Whether a {@link AbstractGUIMenu} is currently open
	 */
	public boolean isGUIMenu() {

		return isGUIMenu;

	}

	/**
	 * @param isGUIMenu Whether a {@link AbstractGUIMenu} is currently open
	 */
	public void setGUIMenu(boolean isGUIMenu) {

		this.isGUIMenu = isGUIMenu;

	}

	/**
	 * @return Whether the player is currently taking chat input either for a command or a {@link AbstractGUIMenu}
	 */
	public boolean isTakingChatInput() {

		return takingChatInput;

	}

	/**
	 * @param takingChatInput Whether the player is currently taking chat input either for a command or a {@link AbstractGUIMenu}
	 */
	public void setTakingChatInput(boolean takingChatInput) {

		this.takingChatInput = takingChatInput;

	}

	/**
	 * @return The {@link AbstractGUIMenu} that the player is currently taking input for
	 */
	public AbstractGUIMenu getMenuToInputTo() {

		return menuToInputTo;

	}

	/**
	 * @param menuToInputTo The {@link AbstractGUIMenu} that the player is currently taking input for
	 */
	public void setMenuToInputTo(AbstractGUIMenu menuToInputTo) {

		this.menuToInputTo = menuToInputTo;

	}

	@Override
	public String toString() {

		return "PlayerMenuUtil: {\"Owner\": " + this.getOwner().getName() + ", \"Is Menu Open\": " + this.isGUIMenu + ", \"Is taking chat input\": " + this.takingChatInput + "}";

	}

	/**
	 * @return The object that the player is currently taking input for (In most cases this is a command)
	 */
	public IChatInputable getInputObject() {

		return inputObject;

	}

	/**
	 * @param inputObject The object that the player is currently taking input for (In most cases this is a command)
	 */
	public void setInputObject(IChatInputable inputObject) {

		this.inputObject = inputObject;

	}

	/**
	 * @return The type of input that the player is currently taking input for
	 */
	public InputType getInputObjectType() {

		return inputObjectType;

	}

	/**
	 * @param inputObjectType The type of input that the player is currently taking input for
	 */
	public void setInputObjectType(InputType inputObjectType) {

		this.inputObjectType = inputObjectType;

	}

}
