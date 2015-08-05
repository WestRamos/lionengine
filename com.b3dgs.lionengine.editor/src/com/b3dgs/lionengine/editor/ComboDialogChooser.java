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
package com.b3dgs.lionengine.editor;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.b3dgs.lionengine.editor.dialog.AbstractDialog;
import com.b3dgs.lionengine.editor.dialog.Messages;
import com.b3dgs.lionengine.editor.utility.UtilButton;
import com.b3dgs.lionengine.editor.utility.UtilIcon;
import com.b3dgs.lionengine.editor.utility.UtilSwt;

/**
 * Represents a combo dialog chooser.
 * 
 * @author Pierre-Alexandre (contact@b3dgs.com)
 */
public class ComboDialogChooser extends Dialog
{
    /** Icon. */
    private static final Image ICON = UtilIcon.get("dialog", "browse.png");

    /** Choice. */
    String choice;

    /**
     * Create the chooser.
     * 
     * @param parent The parent reference.
     */
    public ComboDialogChooser(Shell parent)
    {
        super(parent, SWT.APPLICATION_MODAL);
    }

    /**
     * Open the dialog.
     * 
     * @param items The choice items.
     * @return The selection.
     */
    public String open(String[] items)
    {
        final Shell parent = getParent();
        final Shell shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        shell.setText(Messages.ComboDialogChooser_Title);
        shell.setImage(ICON);

        final GridLayout dialogLayout = new GridLayout(1, false);
        dialogLayout.marginHeight = 0;
        dialogLayout.marginWidth = 0;
        dialogLayout.verticalSpacing = 0;
        shell.setLayout(dialogLayout);

        final Composite area = new Composite(shell, SWT.NONE);
        area.setLayout(new GridLayout(1, false));
        final Combo combo = new Combo(area, SWT.READ_ONLY | SWT.SIMPLE);
        combo.setItems(items);
        if (items.length > 0)
        {
            combo.setText(items[0]);
        }

        final Button finish = UtilButton.create(area, Messages.AbstractDialog_Finish, AbstractDialog.ICON_OK);
        finish.addSelectionListener(new SelectionAdapter()
        {
            @Override
            public void widgetSelected(SelectionEvent selectionEvent)
            {
                choice = combo.getText();
                shell.dispose();
            }
        });

        shell.pack();
        UtilSwt.center(shell);
        shell.open();
        final Display display = parent.getDisplay();
        while (!shell.isDisposed())
        {
            if (!display.readAndDispatch())
            {
                display.sleep();
            }
        }
        return choice;
    }

}