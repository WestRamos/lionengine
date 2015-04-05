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
package com.b3dgs.lionengine.editor.components;

import javax.annotation.PostConstruct;

import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.services.EMenuService;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import com.b3dgs.lionengine.editor.Activator;
import com.b3dgs.lionengine.editor.UtilEclipse;
import com.b3dgs.lionengine.game.map.MapTileGame;
import com.b3dgs.lionengine.game.object.Factory;

/**
 * Represents the component access explorer.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public class ComponentsPart
{
    /** ID. */
    public static final String ID = Activator.PLUGIN_ID + ".part.components";
    /** Menu ID. */
    public static final String MENU_ID = ComponentsPart.ID + ".menu";
    /** Map tile component. */
    private final Image ICON_FEATURE_MAP_TILE = UtilEclipse.getIcon("components", "map-tile.png");
    /** Factory component. */
    private final Image ICON_FEATURE_FACTORY = UtilEclipse.getIcon("components", "factory.png");

    /** Tree viewer. */
    Tree tree;

    /**
     * Create the composite.
     * 
     * @param parent The parent reference.
     * @param menuService The menu service reference.
     */
    @PostConstruct
    public void createComposite(Composite parent, EMenuService menuService)
    {
        final GridLayout layout = new GridLayout(1, false);
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        parent.setLayout(layout);

        tree = new Tree(parent, SWT.NONE);
        tree.setLayoutData(new GridData(GridData.FILL_BOTH));
        tree.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent)
            {
                final Object data = tree.getSelection()[0];
                if (data instanceof TreeItem)
                {
                    final TreeItem item = (TreeItem) data;
                    ComponentsModel.INSTANCE.setComponent(item.getData());
                }
            }
        });
        tree.addMenuDetectListener(new MenuDetectListener()
        {
            @Override
            public void menuDetected(MenuDetectEvent menuDetectEvent)
            {
                tree.getMenu().setVisible(false);
                tree.update();
            }
        });
        menuService.registerContextMenu(tree, ComponentsPart.MENU_ID);
        addComponents();
    }

    /**
     * Add all supported components.
     */
    private void addComponents()
    {
        final TreeItem map = new TreeItem(tree, SWT.NONE);
        map.setText("Map");
        map.setImage(ICON_FEATURE_MAP_TILE);
        map.setData(MapTileGame.class);

        final TreeItem factory = new TreeItem(tree, SWT.NONE);
        factory.setText("Factory");
        factory.setImage(ICON_FEATURE_FACTORY);
        factory.setData(Factory.class);
    }

    /**
     * Set the focus.
     */
    @Focus
    public void setFocus()
    {
        tree.setFocus();
    }
}