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
package com.b3dgs.lionengine.example.game.assign;

import com.b3dgs.lionengine.core.Core;
import com.b3dgs.lionengine.core.Graphic;
import com.b3dgs.lionengine.core.Media;
import com.b3dgs.lionengine.core.Renderable;
import com.b3dgs.lionengine.core.Text;
import com.b3dgs.lionengine.core.Updatable;
import com.b3dgs.lionengine.core.awt.Mouse;
import com.b3dgs.lionengine.drawable.Drawable;
import com.b3dgs.lionengine.drawable.Image;
import com.b3dgs.lionengine.game.Cursor;
import com.b3dgs.lionengine.game.object.Handler;
import com.b3dgs.lionengine.game.object.ObjectGame;
import com.b3dgs.lionengine.game.object.Services;
import com.b3dgs.lionengine.game.object.SetupSurface;
import com.b3dgs.lionengine.game.trait.actionable.Action;
import com.b3dgs.lionengine.game.trait.actionable.Actionable;
import com.b3dgs.lionengine.game.trait.actionable.ActionableModel;
import com.b3dgs.lionengine.game.trait.assignable.Assign;
import com.b3dgs.lionengine.game.trait.assignable.Assignable;
import com.b3dgs.lionengine.game.trait.assignable.AssignableModel;
import com.b3dgs.lionengine.game.trait.pathfindable.Pathfindable;

/**
 * Move action.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
class Move
        extends ObjectGame
        implements Action, Assign, Updatable, Renderable
{
    /** Media reference. */
    public static final Media MEDIA = Core.MEDIA.create("Move.xml");

    /** Button image. */
    private final Image image;
    /** Text reference. */
    private final Text text;
    /** Cursor reference. */
    private final Cursor cursor;
    /** Handler reference. */
    private final Handler handler;
    /** Actionable model. */
    private Actionable actionable;
    /** Assignable model. */
    private Assignable assignable;
    /** Current action state. */
    private Updatable state;

    /**
     * Create move action.
     * 
     * @param setup The setup reference.
     * @param services The services reference.
     */
    public Move(SetupSurface setup, Services services)
    {
        super(setup, services);
        text = services.get(Text.class);
        cursor = services.get(Cursor.class);
        handler = services.get(Handler.class);
        image = Drawable.loadImage(setup.surface);
        addTrait(ActionableModel.class);
        addTrait(AssignableModel.class);
    }

    @Override
    protected void prepareTraits()
    {
        actionable = getTrait(Actionable.class);
        actionable.setClickAction(Mouse.LEFT);

        assignable = getTrait(Assignable.class);
        assignable.setClickAssign(Mouse.LEFT);

        image.setLocation(actionable.getButton().getX(), actionable.getButton().getY());
        state = actionable;
    }

    @Override
    public void execute()
    {
        cursor.setSurfaceId(1);
        state = assignable;
    }

    @Override
    public void assign()
    {
        for (final Pathfindable pathfindable : handler.get(Pathfindable.class))
        {
            pathfindable.setDestination(cursor.getInTileX(), cursor.getInTileY());
        }
        cursor.setSurfaceId(0);
        state = actionable;
    }

    @Override
    public void update(double extrp)
    {
        if (actionable.isOver())
        {
            text.setText(actionable.getDescription());
        }
        state.update(extrp);
    }

    @Override
    public void render(Graphic g)
    {
        image.render(g);
    }
}