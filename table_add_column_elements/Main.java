// Copyright (C) 2021 Jarmo Hurri

// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.

// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <https://www.gnu.org/licenses/>.

package table_add_column_elements;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame
{
  public Main ()
  {
    super ("Adding elements to the column of a table");

    // application data: list of lists
    dataList = new ArrayList<> ();
    dataList.add (new ArrayList<> (Arrays.asList ("A", "B")));
    dataList.add (new ArrayList<> (Arrays.asList ("C")));

    // each list is shown as a column in the table
    tableModel = new AbstractTableModel ()
      {
        public int getColumnCount () { return dataList.size (); }

        // table row count equals length of longest list item
        public int getRowCount ()
        {
          int max = 0;
          for (List<String> list : dataList)
            max = Math.max (max, list.size ());
          return max;
        }
        
        public Object getValueAt (int row, int column)
        {
          List<String> list = dataList.get (column);
          if (row < list.size ())
            return list.get (row);
          else
            return null;
        }
        public String getColumnName (int column)
        {
          return "List " + (column + 1);
        }
      };

    // create table
    table = new JTable (tableModel);
    table.setRowSelectionAllowed (false);
    table.setColumnSelectionAllowed (true); // allow column selection only

    // include a scroll pane to table to show column names
    add (new JScrollPane (table)); 

    // menu bar with one item for adding data to a column
    JMenuBar menuBar = new JMenuBar ();
    JMenu columnMenu = new JMenu ("Column");
    JMenuItem addItem = new JMenuItem ("Add item");
    addItem.addActionListener ((ActionEvent e) -> addData ());
    columnMenu.add (addItem);
    menuBar.add (columnMenu);
    setJMenuBar (menuBar);

    pack ();
    setVisible (true);
  }

  private void addData ()
  {
    int column = table.getSelectedColumn (); // returns -1 if none selected
    if (column >= 0)
    {
      // select data list corresponding to column
      List<String> list = dataList.get (column); 

      list.add ("" + (list.size () + 1)); // add string to data list
      tableModel.fireTableDataChanged (); // tell table to redraw
    }
  }

  private JTable table;
  private AbstractTableModel tableModel;
  private List<List<String>> dataList; // data is list of lists of strings
}
