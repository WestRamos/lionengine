/*
 * Copyright (C) 2013 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
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
package com.b3dgs.lionengine.example.game.projectile;

import com.b3dgs.lionengine.Graphic;
import com.b3dgs.lionengine.Resolution;
import com.b3dgs.lionengine.core.Key;
import com.b3dgs.lionengine.core.Loader;
import com.b3dgs.lionengine.core.Sequence;
import com.b3dgs.lionengine.core.UtilityMath;
import com.b3dgs.lionengine.game.CameraGame;

/**
 * Scene implementation.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 * @see com.b3dgs.lionengine.example.minimal
 */
final class Scene
        extends Sequence
{
    /** Camera. */
    private final CameraGame camera;
    /** Factory launcher. */
    private final FactoryLauncher factoryLauncher;
    /** Factory projectile. */
    private final FactoryProjectile factoryProjectile;
    /** Handler entity. */
    private final HandlerEntity handlerEntity;
    /** Handler projectile. */
    private final HandlerProjectile handlerProjectile;
    /** Launcher. */
    private final Launcher canon1;
    /** Launcher. */
    private final Launcher canon2;
    /** Entity 1. */
    private final Entity entity1;
    /** Entity 2. */
    private final Entity entity2;
    /** Location entity 1. */
    private double location;

    /**
     * Constructor.
     * 
     * @param loader The loader reference.
     */
    Scene(Loader loader)
    {
        super(loader, new Resolution(320, 240, 60));
        camera = new CameraGame();
        factoryProjectile = new FactoryProjectile();
        handlerEntity = new HandlerEntity(camera);
        handlerProjectile = new HandlerProjectile(camera, handlerEntity);
        factoryLauncher = new FactoryLauncher(factoryProjectile, handlerProjectile);
        canon1 = factoryLauncher.create(LauncherType.PULSE_CANNON);
        canon2 = factoryLauncher.create(LauncherType.PULSE_CANNON);
        entity1 = new Entity();
        entity2 = new Entity();
    }

    /*
     * Sequence
     */

    @Override
    protected void load()
    {
        factoryProjectile.load();
        camera.setView(0, 0, width, height);
        canon1.setOwner(entity1);
        canon2.setOwner(entity2);
        handlerEntity.add(entity1);
        handlerEntity.add(entity2);
    }

    @Override
    protected void update(double extrp)
    {
        if (keyboard.isPressed(Key.ESCAPE))
        {
            end();
        }

        location += 1.0;

        entity1.teleport(100 + UtilityMath.cos(location * 1.5) * 70, 180 + UtilityMath.sin(location * 2) * 40);
        entity2.teleport(100 + UtilityMath.cos(location) * 90, 60 + UtilityMath.sin(location * 1.3) * 30);

        canon1.launch(entity2);
        canon2.launch();

        entity1.update(extrp);
        entity2.update(extrp);
        handlerEntity.update(extrp);
        handlerProjectile.update(extrp);
    }

    @Override
    protected void render(Graphic g)
    {
        g.clear(source);
        handlerEntity.render(g);
        handlerProjectile.render(g);
    }
}
