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
package com.b3dgs.lionengine.example.game.extraction;

import com.b3dgs.lionengine.Media;
import com.b3dgs.lionengine.Origin;
import com.b3dgs.lionengine.Updatable;
import com.b3dgs.lionengine.core.Medias;
import com.b3dgs.lionengine.drawable.Drawable;
import com.b3dgs.lionengine.drawable.SpriteAnimated;
import com.b3dgs.lionengine.game.Services;
import com.b3dgs.lionengine.game.layer.Layerable;
import com.b3dgs.lionengine.game.layer.LayerableModel;
import com.b3dgs.lionengine.game.object.ObjectGame;
import com.b3dgs.lionengine.game.object.SetupSurface;
import com.b3dgs.lionengine.game.object.feature.displayable.DisplayableModel;
import com.b3dgs.lionengine.game.object.feature.extractable.Extractor;
import com.b3dgs.lionengine.game.object.feature.extractable.ExtractorChecker;
import com.b3dgs.lionengine.game.object.feature.extractable.ExtractorListener;
import com.b3dgs.lionengine.game.object.feature.extractable.ExtractorModel;
import com.b3dgs.lionengine.game.object.feature.transformable.Transformable;
import com.b3dgs.lionengine.game.object.feature.transformable.TransformableModel;
import com.b3dgs.lionengine.game.pathfinding.Pathfindable;
import com.b3dgs.lionengine.game.pathfinding.PathfindableModel;
import com.b3dgs.lionengine.game.tile.Tiled;
import com.b3dgs.lionengine.graphic.Viewer;
import com.b3dgs.lionengine.util.UtilMath;

/**
 * Peon entity implementation.
 */
class Peon extends ObjectGame implements Updatable, ExtractorChecker, ExtractorListener
{
    /** Media reference. */
    public static final Media MEDIA = Medias.create("Peon.xml");

    /** Transformable model. */
    private final Transformable transformable = addFeatureAndGet(new TransformableModel());
    /** Pathfindable model. */
    private final Pathfindable pathfindable;
    /** Extractor model. */
    private final Extractor extractor = addFeatureAndGet(new ExtractorModel());
    /** Layerable model. */
    private final Layerable layerable = addFeatureAndGet(new LayerableModel());
    /** Surface reference. */
    private final SpriteAnimated surface;
    /** Viewer reference. */
    private final Viewer viewer;
    /** Visible. */
    private boolean visible;

    /**
     * Create a peon.
     * 
     * @param setup The setup reference.
     * @param services The services reference.
     */
    public Peon(SetupSurface setup, Services services)
    {
        super(setup, services);

        pathfindable = addFeatureAndGet(new PathfindableModel(setup.getConfigurer()));

        viewer = services.get(Viewer.class);
        transformable.teleport(208, 160);
        layerable.setLayer(Integer.valueOf(2));

        surface = Drawable.loadSpriteAnimated(setup.getSurface(), 15, 9);
        surface.setOrigin(Origin.MIDDLE);
        surface.setFrameOffsets(-8, -8);
        visible = true;

        extractor.setExtractionPerSecond(1.0);
        extractor.setDropOffPerSecond(1.0);
        extractor.setCapacity(5);

        addFeature(new DisplayableModel(g ->
        {
            if (visible)
            {
                surface.render(g);
            }
        }));
    }

    @Override
    public void update(double extrp)
    {
        pathfindable.update(extrp);
        extractor.update(extrp);
        surface.setLocation(viewer, transformable);
    }

    @Override
    public boolean canExtract()
    {
        return UtilMath.getDistance(pathfindable.getInTileX(),
                                    pathfindable.getInTileY(),
                                    extractor.getResourceLocation().getInTileX(),
                                    extractor.getResourceLocation().getInTileY()) < 2;
    }

    @Override
    public boolean canCarry()
    {
        return true;
    }

    @Override
    public void notifyStartGoToRessources(Enum<?> type, Tiled resourceLocation)
    {
        pathfindable.setDestination(resourceLocation);
    }

    @Override
    public void notifyStartExtraction(Enum<?> type, Tiled resourceLocation)
    {
        System.out.println("Started !");
        visible = false;
    }

    @Override
    public void notifyExtracted(Enum<?> type, int currentQuantity)
    {
        System.out.println("Extracted ! " + currentQuantity);
    }

    @Override
    public void notifyStartCarry(Enum<?> type, int totalQuantity)
    {
        visible = true;
    }

    @Override
    public void notifyStartDropOff(Enum<?> type, int totalQuantity)
    {
        // Nothing to do
    }

    @Override
    public void notifyDroppedOff(Enum<?> type, int droppedQuantity)
    {
        System.out.println("done !" + droppedQuantity);
    }
}
