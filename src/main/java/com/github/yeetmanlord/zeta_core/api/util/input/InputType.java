package com.github.yeetmanlord.zeta_core.api.util.input;

/**
 * Represents the type of input a player should give. This doesn't necessarily mean that the input will be that type. It is more of a code readability thing.
 * In fact, you could use {@link InputType#NONE} for any input, and it would work just fine. There are so many inputs here because menus can get pretty big.
 * If you somehow run out of input types, you can probably use booleans for checking what is inputting.
 * @zeta.example <ul>boolean inputtingName = true; <br>
 *      public void processChatInput(InputType type, AsyncPlayerChatEvent event) {<ul>
 *          if (inputtingName) {<ul>
 *              // DO STUFF
 *         </ul> }
 *      </ul>}</ul>
 *
 *
 */
public enum InputType {
    NONE,
    STRING,
    STRING1,
    STRING2,
    STRING3,
    STRING4,
    STRING5,
    STRING6,
    STRING7,
    STRING8,
    STRING9,
    STRING10,
    NUMBER,
    NUMBER1,
    NUMBER2,
    NUMBER3,
    NUMBER4,
    NUMBER5,
    NUMBER6,
    NUMBER7,
    NUMBER8,
    NUMBER9,
    NUMBER10,
    BOOLEAN,
    BOOLEAN1,
    BOOLEAN2,
    BOOLEAN3,
    BOOLEAN4,
    BOOLEAN5;
}