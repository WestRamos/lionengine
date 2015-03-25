/*
 * Copyright (C) 2013-2015 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package com.b3dgs.lionengine.example.game.action;

import com.b3dgs.lionengine.core.Core;
import com.b3dgs.lionengine.core.Graphic;
import com.b3dgs.lionengine.core.Media;
import com.b3dgs.lionengine.core.Renderable;
import com.b3dgs.lionengine.core.Text;
import com.b3dgs.lionengine.core.Updatable;
import com.b3dgs.lionengine.core.Verbose;
import com.b3dgs.lionengine.core.awt.Mouse;
import com.b3dgs.lionengine.drawable.Drawable;
import com.b3dgs.lionengine.drawable.Image;
import com.b3dgs.lionengine.game.configurer.ConfigAction;
import com.b3dgs.lionengine.game.object.ObjectGame;
import com.b3dgs.lionengine.game.object.Services;
import com.b3dgs.lionengine.game.object.SetupSurface;
import com.b3dgs.lionengine.game.trait.actionable.Action;
import com.b3dgs.lionengine.game.trait.actionable.Actionable;
import com.b3dgs.lionengine.game.trait.actionable.ActionableModel;

/**
 * Abstract button action.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
class Button
        extends ObjectGame
        implements Action, Updatable, Renderable
{
    /** Media buildings reference. */
    public static final Media BUILDINGS = Core.MEDIA.create("action", "Buildings.xml");
    /** Media build farm reference. */
    public static final Media BUILD_FARM = Core.MEDIA.create("action", "BuildFarm.xml");
    /** Media build barracks reference. */
    public static final Media BUILD_BARRACKS = Core.MEDIA.create("action", "BuildBarracks.xml");
    /** Media cancel reference. */
    public static final Media CANCEL = Core.MEDIA.create("action", "Cancel.xml");

    /** Button image. */
    private final Image image;
    /** Text reference. */
    private final Text text;
    /** Actionable model. */
    private Actionable actionable;
    /** Action name. */
    private final String name;

    /**
     * Create build farm action.
     * 
     * @param setup The setup reference.
     * @param services The services reference.
     */
    public Button(SetupSurface setup, Services services)
    {
        super(setup, services);
        text = services.get(Text.class);
        image = Drawable.loadImage(setup.surface);
        name = setup.getConfigurer().getText(ConfigAction.NAME);
        addTrait(ActionableModel.class);
    }

    @Override
    protected void prepareTraits()
    {
        actionable = getTrait(Actionable.class);
        actionable.setClickAction(Mouse.LEFT);
        image.setLocation(actionable.getButton().getX(), actionable.getButton().getY());
    }

    @Override
    public void execute()
    {
        Verbose.info(name);
    }

    @Override
    public void update(double extrp)
    {
        actionable.update(extrp);
        if (actionable.isOver())
        {
            text.setText(actionable.getDescription());
        }
    }

    @Override
    public void render(Graphic g)
    {
        image.render(g);
    }
}