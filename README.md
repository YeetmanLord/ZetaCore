# ZetaCore
A core API that adds many features to make developing gui-based plugins easier. ZetaCore also acts as a management plugin for all plugins using it (That also extend the ZetaPlugin class.) It includes features for easily managing config files as well as easily add your config options to a database. There are also many utilities and helper classes to create gui menus and develop intricate commands for your plugin.

# Contents
[Config, config, and more config!](#config-config-and-more-config)
- [Basic Data Storer](#basic-data-storer)
- [Adding SQL Support](#add-some-sql)
- [Initializing the data storers](#initializing-these-data-storers-and-initializing-a-zetaplugin)

[Menus](#menus)
- [Simple Menu](#simple-menu)
- [Adding chat input](#adding-chat-input)
- [Adding player inventory input](#adding-player-inventory-input)

[Advanced](#advanced)
- [Commands](#commands)
  - [Creating the command](#creating-the-command)
  - [Adding sub-commands](#adding-sub-commands)
  - [Registering](#registering)
- [ISQL Objects](#isql-objects)
  - [Object](#object)
  - [Handler](#handler)
  - [Data Storer](#data-storer)
  



# Config, config, and more config!
All about data and storing it.

## Basic data storer
This uses a yml file to store data and has no database relation.
```java
public class MyDataHandler extends DataStorer {
  private boolean someOption;
  private String nameOfSomething;
  
  public MyDataHandler(ZetaPlugin plugin) {
    super(plugin, "file_name");
  }
  
  @Override
  public void write() {
    this.config.set("someOption", someOption);
    this.config.set("name", nameOfSomething);
    
    this.save(); // Important, actually writes to file.
  }
  
  @Override
  public void read() {
    this.someOption = this.config.getBoolean("someOption");
    this.nameOfSomething = this.config.getString("name");
  }
  
  @Override
  public void setDefualts() {
    if (!this.config.contains("someOption") {
      this.config.set("someOption", someOption);
    }
    if (!this.config.contains("name") {
      this.config.set("name", nameOfSomething);
    }
    
    // After defaults are set the read method is called.
  }
}
```

## Add some SQL
Now you will have your data stored in an sql database *and* a local file. This ensures that if the database is ever down you still have the correct data.
We'll build off of the previous example. (Although that isn't a *very* practical example it still works to get the gist)
```java
public class MyDataHandler extends AbstractUntypedSQLDataStorer<Integer> /* The generic specifies the type of the primary key */ {
  // UntypedSQLDataStorer indicates there is no associated object.
  
  public MyDataHandler(ZetaPlugin plugin) {
    super(plugin, "file_name", "table_name");
  }
  
  // You have a few new methods to implement.
  @Override
 	public List<SQLColumn<?>> getColumns(SQLHandler handler) {
    // In most cases you will likely not have to use the SQLHandler
    List<SQLColumn<?>> columns = new ArrayList<>();
    columns.add(new SQLInteger("ID", table, /* Reference to this table */ 1);
    columns.add(new SQLVarChar("NAME", table, 16);
    columns.add(new SQLBool("SOME_BOOLEAN", table);
    return columns;
  }
  
  @Override
  public void readDB() {
    List<Row> rows = this.table.getRows();
    
    this.someOption = rows.get(0).getBoolean("SOME_BOOLEAN"); // Gets the first row and then gets the boolean under the key of `SOME_BOOLEAN`
    this.nameOfSomething = rows.get(0).getString("NAME"); // Gets the first row and then gets the string under the key of `NAME`
  }
  
  @Override
  public void writeDB() {
    this.table.writeValue(0, /* Id */ nameOfSomething, this.someOption); 
    /* 
    * Using this method you must input your objects in the order that you specified in your getColumns method
    * Also when writing to the database (outside of this method) you will need to either call table.commit() or writeToDB()
    * Because for performance I use batches of SQL statements rather than sending them individually
    */ 
  }
  
  @Override
  public String getPrimaryKey() {
    return "ID";
  }
```


## Initializing these data storers and Initializing a ZetaPlugin
This has to be done in your main class (And that class has to extend ZetaPlugin)
This example assumes you implemented you data handler with SQL.
```java
public class MyPlugin extends ZetaPlugin { // Already extends JavaPlugin, so all is well
  private MyDataHandler dataHandler;
  private boolean dataRead;
  
  @Override
  public void onEnable() {
    // Register any events that DON'T require your data.
    super.onEnable();
  }
  
  
  @Override
  public String getPluginName() {
      return "MyPlugin";
  }
  
  @Override
  protected void registerDataStorers() {
    dataHandler = new MyDataHandler(this);
    dataHandler.setup();
  }
  
  // Data is read asynchronously so this method is run after data has been read
  @Override
  public void onDataReadFinish() {
    this.dataRead = true;
    // Register any events that require your data
  }
  
  @Override
  public boolean initializedFinished() {
    return dataRead;
  }
  
  @Override
  public ItemStack getIcon() {
    return new ItemStack(Material.COMMAND_BLOCK);
  }
}
```
This example also shows how you would set up a ZetaPlugin in the first place. The getIcon method is used to get the item displayed when in the menu for managing various plugins.

# Menus
ZetaCore has features to allow you to easily create gui menus.
## Simple Menu
This is a simple example of how to create a GUI using ZetaCore. This example uses ZetaCore's AbstractGUIMenu class to implement a simple GUI as well as that class's methods to easily create items and add them to the GUI. This will create a menu where the player can click on items to get them. The menu will have 54 slots and will be titled "Test Menu" with a gold coloring. There is also a secret feature where if you click a specific slot, you will get a secret diamond.
```java
public class SimpleMenu extends AbstractGUIMenu {

	public SimpleMenu(PlayerUtil menuUtil) {
		super(menuUtil, "&6Test Menu", 54); // Supports formatting codes
	}

	@Override
	public void setItems() {
		// Set items here
		this.inv.setItem(0, makeItem(Material.DIAMOND, "&bDiamond"));
		this.inv.setItem(1, makeItem(Material.GOLD_INGOT, "&6Gold"));
		this.inv.setItem(2, makeItem(Material.IRON_INGOT, "&fIron"));
		this.inv.setItem(3, makeItem(Material.COAL, "&8Coal"));
		this.inv.setItem(4, makeItem(Material.REDSTONE, "&cRedstone"));
		this.inv.setItem(5, makeItem(Material.BARRIER, "&cBarrier", "&7Click me to get a barrier")); // Supports lore and formatting codes
	}

	@Override
	public void handleClick(InventoryClickEvent e) {
		// Handle clicks here
		// If the player clicks on an item, this method will be called
		switch (e.getSlot()) {
			case 0:
				this.owner.getInventory().addItem(makeItem(Material.DIAMOND, "&bDiamond"));
				break;
			case 1:
				this.owner.getInventory().addItem(makeItem(Material.GOLD_INGOT, "&6Gold"));
				break;
			case 2:
				this.owner.getInventory().addItem(makeItem(Material.IRON_INGOT, "&fIron"));
				break;
			case 3:
				this.owner.getInventory().addItem(makeItem(Material.COAL, "&8Coal"));
				break;
			case 4:
				this.owner.getInventory().addItem(makeItem(Material.REDSTONE, "&cRedstone"));
				break;
			case 5:
				this.owner.getInventory().addItem(makeItem(Material.BARRIER, "&cBarrier"));
				break;
		}
	}

	@Override
	public void handleClickAnywhere(InventoryClickEvent e) {
		// However, if you want to handle clicks anywhere in the inventory, use this method
		super.handleClickAnywhere(e);
		if (e.getSlot() == 12) {
			this.owner.getInventory().addItem(makeItem(Material.DIAMOND, "&4Secret Diamond"));
		}
	}
}
```

## Adding chat input
This is an example of how to implement chat input for a GUI using ZetaCore. This builds off of the `Creating a simple GUI using ZetaCore` example. However, to get a barrier, the player must type "barrier" in chat. This will also close the GUI and send a title to the player.
```java
public class SimpleGUI extends AbstractGUIMenu implements IChatInputable {
    •••
    @Override
    public void handleClick(InventoryClickEvent e) {
        // Handle clicks here
        // If the player clicks on an item, this method will be called
        switch (e.getSlot()) {
            •••
            case 5:
                // Initialize chat input
                this.menuUtil.setMenuToInputTo(this);
                this.menuUtil.setTakingChatInput(true);
                // Input Type is used to determine what to do with the input
                this.setInputType(InputType.STRING);
                this.menuUtil.setGUIMenu(true);
                // Synchronously close menu, since InventoryClick is called asynchronously
                this.syncClose();
                // Send title packets
                this.sendTitlePackets("&6You must enter a password!");
                break;
        }
    }

    @Override
    public void processChatInput(InputType type, AsyncPlayerChatEvent event) {
        // Process chat input here
        if (type == InputType.STRING) {
            if (event.getMessage().trim().equals("barrier")) {
                // Synchronously give player item
                Bukkit.getScheduler().runTask(MyPlugin.getInstance(), () -> owner.getInventory().addItem(new ItemStack(Material.BARRIER)));
            }
        }
    }
}
```


## Adding player inventory input
This is an example of how to implement player inventory input for a GUI using ZetaCore. This builds off of the `Creating a simple GUI using ZetaCore` example. However, if the player clicks on the barrier, an input menu will open. The player must input a diamond sword into the input menu. If they do, they get a barrier. If they don't they get a stone sword.
```java
public class SimpleMenu extends AbstractGUIMenu implements IPlayerInputMenuInputable {

	•••
	@Override
	public void handleClick(InventoryClickEvent e) {
		// Handle clicks here
		// If the player clicks on an item, this method will be called
		switch (e.getSlot()) {
			•••
			case 5:
				// Open input menu
				new PlayerInputMenu(this).open();
				break;
		}
	}

	@Override
	public void setInputValuesFromInputMenu(InventoryClickEvent event) {
		// Handle player's input here
		if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
			ItemStack item = event.getCurrentItem();
			if (item.getType() == Material.DIAMOND_SWORD) {
				Bukkit.getScheduler().runTask(MyPlugin.getInstance(), () -> this.owner.getInventory().addItem(new ItemStack(Material.BARRIER)));
			} else {
				Bukkit.getScheduler().runTask(MyPlugin.getInstance(), () -> this.owner.getInventory().addItem(new ItemStack(Material.STONE_SWORD)));
			}
		}
	}
}
```

# Advanced
## Commands
### Creating the command
This command already handles comamnd execution and tab completion.
Although more complex commands may require that these are overriden.
```java
public class MyCommand extends com.github.yeetmanlord.zeta_core.commands.Command {
  public MyCommand(ZetaPlugin plugin) {
    super(new HelperCOmmand("mycommand"), /* The name helps with the help menu */ plugin);
    commands.add(new TestSubCommand("hello", "Hello Everyone!"));
    commands.add(new TestSubCommand("world", "Hello World"));
    commands.add(new TestSubCommand("test", "Test test test"));
  }

  @Override
  protected String getName() {
    return "mycommand";
  }
  @Override
  protected String getDesc() {
    return "A test command";
  }
  @Override
  protected String getSyntax() {
    return "/mycommand hello | world | test | help";
  }
}
```

### Adding Sub Commands
```java
public class TestSubCommand implements ISubCommand {
  private String name;
  private String whatToSay;
  
  public TestSubCommand(String name, String whatSay) {
    this.name = name;
    this.whatToSay = whatSay;
  }
  
  // Represents how far in the command is
  // /command index0 index1 index2 etc.
  @Override
  public int getIndex() {
    return 0;
  }
  
  @Override
  public String getName() {
    return name;
  }
  
  @Override
  public String getDesc() {
    return "Says " + whatToSay;
  }
  
  public String getSyntax() {
    return "";
  }
  
  public String getPermission() {
    return "myplugin.mycommand.subpermission";
  }
  
  public void run(CommandSender sender, String[] args) {
    sender.sendMessage(ChatColor.GOLD + this.whatToSay);
  }
}
```

### Registering
This command is registered like any other command using `getCommand("command").setExecutor(new MyCommand(this));` Since the Command class show above already implements CommandExecutor there is nothing special to it.

## ISQL Objects
These are objects that can be translated and stored into an SQL database.
### Object
```java
public class MyObject implements ISQL<MyObject> {
  private int id;
  private String name;
  private boolean allowed;
  
  public MyObject(int id, String name, boolean allowed) {
    this.id = id;
    this.name = name;
    this.allowed = allowed;
  }
  
  •••
  
  public ISQLObjectHandler<MyObject> getHandler() {
    return HANDLER;
  }
  
  public static final Handler HANDLER = new Handler();
}
```

### Handler
This is inside of the MyObject class
```java
public static class Handler implements ISQLObjectHandler<MyObject> {
  public Row translateDataToSQL(MyObject obj) {
    Row row = new Row();
    // Keys should reference keys in table
    row.put("ID", obj.id);
    row.put("NAME", obj.name);
    row.put("ALLOWED", obj.allowed);
    return row;
  }
  
  public MyObject load(Row data) {
    int id = row.getInt("ID");
    String name = row.getString("NAME");
    boolean allowed = row.getBoolean("ALLOWED");
    return new MyObject(id, name, allowed);
  }
}
```

### Data Storer
```java
public class MyObjectHandler extends AbstractSQLDataStorer<Integer> {
  ••• Implementation and stuff
  private List<MyObject> myObjects;
  
  @Override
  public void readDB() {
    for (Row row : this.table.getRows()) {
      this.myObjects.add(this.getHandler().load(row));      
    }
  }
  
  @Override 
  public void writeDB() {
    for (MyObject obj : this.myObjects) {
      this.table.writeValue(obj.getHandler().translateDataToSQL(obj)) // Just in case there is some class extending
    }
  }
  
  @Override
  public MyObject.Handler getHandler() {
    return MyObject.HANDLER;
  }
}
```

