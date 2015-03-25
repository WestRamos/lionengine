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
package com.b3dgs.lionengine.example.game.attack;

import com.b3dgs.lionengine.Resolution;
import com.b3dgs.lionengine.core.Core;
import com.b3dgs.lionengine.core.Graphic;
import com.b3dgs.lionengine.core.Loader;
import com.b3dgs.lionengine.core.Sequence;
import com.b3dgs.lionengine.core.awt.Engine;
import com.b3dgs.lionengine.core.awt.Keyboard;
import com.b3dgs.lionengine.core.awt.Mouse;
import com.b3dgs.lionengine.game.Camera;
import com.b3dgs.lionengine.game.map.MapTile;
import com.b3dgs.lionengine.game.map.MapTileGame;
import com.b3dgs.lionengine.game.map.MapTilePath;
import com.b3dgs.lionengine.game.map.MapTilePathModel;
import com.b3dgs.lionengine.game.object.ComponentRenderer;
import com.b3dgs.lionengine.game.object.ComponentUpdater;
import com.b3dgs.lionengine.game.object.Factory;
import com.b3dgs.lionengine.game.object.Handler;
import com.b3dgs.lionengine.game.trait.pathfindable.Pathfindable;
import com.b3dgs.lionengine.game.trait.transformable.Transformable;

/**
 * Game loop designed to handle our little world.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 * @see com.b3dgs.lionengine.example.core.minimal
 */
class Scene
        extends Sequence
{
    /** Native resolution. */
    private static final Resolution NATIVE = new Resolution(320, 240, 60);

    /** Camera reference. */
    private final Camera camera = new Camera();
    /** Handler reference. */
    private final Handler handler = new Handler();
    /** Map reference. */
    private final MapTile map = new MapTileGame(camera, 16, 16);
    /** Map path. */
    private final MapTilePath mapPath = new MapTilePathModel(map);
    /** Keyboard reference. */
    private final Keyboard keyboard;
    /** Mouse reference. */
    private final Mouse mouse;

    /**
     * Constructor.
     * 
     * @param loader The loader reference.
     */
    public Scene(Loader loader)
    {
        super(loader, Scene.NATIVE);
        keyboard = getInputDevice(Keyboard.class);
        mouse = getInputDevice(Mouse.class);
        setSystemCursorVisible(false);
    }

    @Override
    protected void load()
    {
        map.addFeature(mapPath);
        map.create(Core.MEDIA.create("level.png"), Core.MEDIA.create("sheets.xml"), Core.MEDIA.create("groups.xml"));
        mapPath.loadPathfinding(Core.MEDIA.create("pathfinding.xml"));

        camera.setView(0, 0, getWidth(), getHeight());
        camera.setLimits(map);
        camera.setLocation(0, 0);

        final Factory factory = new Factory();
        factory.addService(camera);
        factory.addService(map);

        handler.addUpdatable(new ComponentUpdater());
        handler.addRenderable(new ComponentRenderer());

        final Grunt grunt1 = factory.create(Grunt.MEDIA);
        handler.add(grunt1);
        final Grunt grunt2 = factory.create(Grunt.MEDIA);
        handler.add(grunt2);

        final Pathfindable pathfindable = grunt2.getTrait(Pathfindable.class);
        pathfindable.setLocation(4, 10);

        grunt1.attack(grunt2.getTrait(Transformable.class));
    }

    @Override
    public void update(double extrp)
    {
        mouse.update(extrp);
        handler.update(extrp);
        if (keyboard.isPressedOnce(Keyboard.ESCAPE))
        {
            end();
        }
    }

    @Override
    public void render(Graphic g)
    {
        map.render(g);
        handler.render(g);
    }

    @Override
    protected void onTerminate(boolean hasNextSequence)
    {
        Engine.terminate();
    }
}