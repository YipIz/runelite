package net.runelite.client.plugins.miscplugins.specbar;

import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.WidgetHiddenChanged;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;

@PluginDescriptor(
	name = "Spec Bar",
	description = "Adds a spec bar to every weapon",
	tags = {"spec bar", "special attack", "spec", "bar"},
	enabledByDefault = false
)
public class SpecBarPlugin extends Plugin
{
	@Inject
	private Client client;

	@Override
	protected void startUp() throws Exception
	{
	}

	@Override
	protected void shutDown() throws Exception
	{
	}

	@Subscribe
	private void onWidgetHiddenChanged(WidgetHiddenChanged event)
	{
		if (WidgetInfo.TO_GROUP(event.getWidget().getId()) == WidgetID.COMBAT_GROUP_ID)
		{
			hideSpecBar();
		}
	}

	@Subscribe
	private void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState().equals(GameState.LOGGING_IN))
		{
			hideSpecBar();
		}

	}

	private static final int SPECIAL_ATTACK_BAR = 35;
	/**
	 * Displays the special attack bar
	 */
	private void hideSpecBar()
	{
		try
		{
			Widget specBar = client.getWidget(WidgetID.COMBAT_GROUP_ID, SPECIAL_ATTACK_BAR);
			specBar.setHidden(false);
			specBar.revalidate();
		}
		catch (NullPointerException e)
		{
			// Stops the nulls that occur before the spec bar is loaded by player equipping a spec wep
		}
	}
}
