/*
 * Copyright (C) 2013-2019 Byron 3D Games Studio (www.b3dgs.com) Pierre-Alexandre (contact@b3dgs.com)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
package com.b3dgs.lionengine.game.feature.tile;

import com.b3dgs.lionengine.Check;
import com.b3dgs.lionengine.LionEngineException;
import com.b3dgs.lionengine.Media;
import com.b3dgs.lionengine.game.Feature;
import com.b3dgs.lionengine.game.feature.Features;

/**
 * Tile base implementation.
 */
public class TileGame implements Tile
{
    /** Features provider. */
    private final Features features = new Features();
    /** Horizontal in tile location. */
    private final int tx;
    /** Vertical in tile location. */
    private final int ty;
    /** Tile width. */
    private final int width;
    /** Tile height. */
    private final int height;
    /** Horizontal location on map. */
    private final double x;
    /** Vertical location on map. */
    private final double y;
    /** Tile number. */
    private int number;
    /** Key. */
    private Integer key;
    /** Sheet id. */
    private int sheetId;
    /** Sheet id key. */
    private Integer sheetKey;

    /**
     * Create a tile.
     * 
     * @param number The tile number on sheet (must be positive or equal to 0).
     * @param tx The horizontal in tile location (must be positive or equal to 0).
     * @param ty The vertical in tile location (must be positive or equal to 0).
     * @param width The tile width (must be strictly positive).
     * @param height The tile height (must be strictly positive).
     * @throws LionEngineException If invalid arguments.
     */
    public TileGame(int number, int tx, int ty, int width, int height)
    {
        super();

        Check.superiorOrEqual(number, 0);
        Check.superiorOrEqual(tx, 0);
        Check.superiorOrEqual(ty, 0);
        Check.superiorStrict(width, 0);
        Check.superiorStrict(height, 0);

        this.number = number;
        this.tx = tx;
        this.ty = ty;
        this.width = width;
        this.height = height;

        key = Integer.valueOf(number);
        x = tx * width;
        y = ty * height;
    }

    /**
     * Set the tile number.
     * 
     * @param number The tile number on sheet (must be positive or equal to 0).
     * @throws LionEngineException If invalid argument.
     */
    public void set(int number)
    {
        Check.superiorOrEqual(number, 0);

        this.number = number;
        key = Integer.valueOf(number);
    }

    /**
     * Set the tile sheet id.
     * 
     * @param sheetId The tile sheet id (must be positive or equal to 0).
     * @throws LionEngineException If invalid argument.
     */
    public void setSheet(int sheetId)
    {
        Check.superiorOrEqual(sheetId, 0);

        this.sheetId = sheetId;
        sheetKey = Integer.valueOf(sheetId);
    }

    /*
     * Tile
     */

    @Override
    public int getNumber()
    {
        return number;
    }

    @Override
    public Integer getKey()
    {
        return key;
    }

    @Override
    public int getSheet()
    {
        return sheetId;
    }

    @Override
    public Integer getSheetKey()
    {
        return sheetKey;
    }

    @Override
    public int getInTileX()
    {
        return tx;
    }

    @Override
    public int getInTileY()
    {
        return ty;
    }

    @Override
    public int getInTileWidth()
    {
        return 1;
    }

    @Override
    public int getInTileHeight()
    {
        return 1;
    }

    @Override
    public int getWidth()
    {
        return width;
    }

    @Override
    public int getHeight()
    {
        return height;
    }

    @Override
    public double getX()
    {
        return x;
    }

    @Override
    public double getY()
    {
        return y;
    }

    /*
     * Featurable
     */

    /**
     * {@inheritDoc}
     * <p>
     * Does nothing by default.
     * </p>
     */
    @Override
    public void checkListener(Object listener)
    {
        // Nothing by default
    }

    @Override
    public final void addFeature(Feature feature)
    {
        feature.prepare(this);
        features.add(feature);
    }

    @Override
    public final <T extends Feature> T addFeatureAndGet(T feature)
    {
        addFeature(feature);
        return feature;
    }

    @Override
    public final <C extends Feature> C getFeature(Class<C> feature)
    {
        return features.get(feature);
    }

    @Override
    public final Iterable<Feature> getFeatures()
    {
        return features.getFeatures();
    }

    @Override
    public final Iterable<Class<? extends Feature>> getFeaturesType()
    {
        return features.getFeaturesType();
    }

    @Override
    public final boolean hasFeature(Class<? extends Feature> feature)
    {
        return features.contains(feature);
    }

    @Override
    public Media getMedia()
    {
        return null;
    }

    /*
     * Object
     */

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + number;
        result = prime * result + tx;
        result = prime * result + ty;
        result = prime * result + width;
        result = prime * result + height;
        return result;
    }

    @Override
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }
        if (object == null || object.getClass() != getClass())
        {
            return false;
        }
        final TileGame other = (TileGame) object;
        return number == other.number
               && tx == other.tx
               && ty == other.ty
               && width == other.width
               && height == other.height;
    }

    @Override
    public String toString()
    {
        return new StringBuilder().append("number = ")
                                  .append(number)
                                  .append(" | tx = ")
                                  .append(tx)
                                  .append(" | ty = ")
                                  .append(ty)
                                  .toString();
    }
}
