/*
 * Copyright (C) 2013-2016 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
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
package com.b3dgs.lionengine.example.game.selector;

import com.b3dgs.lionengine.Media;
import com.b3dgs.lionengine.Origin;
import com.b3dgs.lionengine.Updatable;
import com.b3dgs.lionengine.core.Medias;
import com.b3dgs.lionengine.drawable.Drawable;
import com.b3dgs.lionengine.drawable.SpriteAnimated;
import com.b3dgs.lionengine.game.SelectorListener;
import com.b3dgs.lionengine.game.Services;
import com.b3dgs.lionengine.game.collision.object.Collidable;
import com.b3dgs.lionengine.game.collision.object.CollidableModel;
import com.b3dgs.lionengine.game.object.ObjectGame;
import com.b3dgs.lionengine.game.object.SetupSurface;
import com.b3dgs.lionengine.game.object.feature.transformable.Transformable;
import com.b3dgs.lionengine.game.object.feature.transformable.TransformableModel;
import com.b3dgs.lionengine.geom.Rectangle;
import com.b3dgs.lionengine.graphic.ColorRgba;
import com.b3dgs.lionengine.graphic.Graphic;
import com.b3dgs.lionengine.graphic.Renderable;
import com.b3dgs.lionengine.graphic.Viewer;

/**
 * Peon entity implementation.
 */
class Peon extends ObjectGame implements Updatable, Renderable, SelectorListener
{
    /** Media reference. */
    public static final Media MEDIA = Medias.create("Peon.xml");

    /** Transformable model. */
    private final Transformable transformable;
    /** Collidable model. */
    private final Collidable collidable;
    /** Surface reference. */
    private final SpriteAnimated surface;
    /** Viewer reference. */
    private final Viewer viewer;
    /** Selected flag. */
    private boolean selected;

    /**
     * Create a peon.
     * 
     * @param setup The setup reference.
     * @param services The services reference.
     */
    public Peon(SetupSurface setup, Services services)
    {
        super(setup, services);

        transformable = addFeatureAndGet(new TransformableModel(setup.getConfigurer()));
        collidable = addFeatureAndGet(new CollidableModel(setup.getConfigurer()));

        viewer = services.get(Viewer.class);

        surface = Drawable.loadSpriteAnimated(setup.getSurface(), 15, 9);
        surface.setOrigin(Origin.MIDDLE);
        surface.setFrameOffsets(-8, -8);

        transformable.teleport(240, 160);
        collidable.setOrigin(Origin.MIDDLE);
    }

    @Override
    public void update(double extrp)
    {
        collidable.update(extrp);
        surface.setLocation(viewer, transformable);
    }

    @Override
    public void render(Graphic g)
    {
        surface.render(g);
        if (selected)
        {
            g.setColor(ColorRgba.GREEN);
            g.drawRect(viewer,
                       Origin.MIDDLE,
                       transformable.getX() + 8,
                       transformable.getY() + 8,
                       transformable.getWidth(),
                       transformable.getHeight(),
                       false);
        }
    }

    /*
     * SelectorListener
     */

    @Override
    public void notifySelectionStarted(Rectangle selection)
    {
        selected = false;
    }

    @Override
    public void notifySelectionDone(Rectangle selection)
    {
        for (final Rectangle rectangle : collidable.getCollisionBounds())
        {
            if (selection.contains(rectangle) || selection.intersects(rectangle))
            {
                selected = true;
                break;
            }
        }
    }
}
