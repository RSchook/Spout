package org.bukkitcontrib.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkitcontrib.packet.PacketWidget;

public class GenericScreen implements Screen{
	protected List<Widget> widgets = new ArrayList<Widget>();
	protected UUID id = UUID.randomUUID();
	protected int playerId;
	public GenericScreen() {
		
	}
	
	public GenericScreen(int playerId) {
		this.playerId = playerId;
	}

	@Override
	public Widget[] getAttachedWidgets() {
		Widget[] list = new Widget[widgets.size()];
		widgets.toArray(list);
		return list;
	}

	@Override
	public Screen attachWidget(Widget widget) {
		widgets.add(widget);
		return this;
	}

	@Override
	public Screen removeWidget(Widget widget) {
		widgets.remove(widget);
		return this;
	}
	
	@Override
	public boolean containsWidget(Widget widget) {
		for (Widget w : widgets) {
			if (w.equals(widget)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean updateWidget(Widget widget) {
		int index = widgets.indexOf(widget);
		if (index > -1) {
			widgets.remove(index);
			widgets.add(index, widget);
			return true;
		}
		return false;
	}
	
	@Override
	public void onTick() {

	}
	
	@Override
	public UUID getId() {
		return id;
	}
}
