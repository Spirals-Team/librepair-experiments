package common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Table implements Iterable<Table.Row>
{
    private List<Row> rows = new ArrayList<>();
    
    @Override
    public Iterator<Row> iterator()
    {
        return rows.iterator();
    }
    
    public void add(Row row)
    {
        rows.add(row);
    }
    
    public Row get(int index)
    {
        return rows.get(index);
    }
    
    public static Table create(Row... rows)
    {
        Table table = new Table();
        
        for(Row row : rows)
        {
            table.add(row);
        }
        
        return table;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o == null )
        {
            return false;
        }
        if (!o.getClass().equals(getClass()))
        {
            return false;
        }
      
        Table table = (Table)o;
      
        if(rows.size() != table.rows.size())
        {
            return false;
        }
        
        for(int i = 0; i < rows.size(); i++)
        {
            if(!rows.get(i).equals(table.rows.get(i)))
            {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        
        for(Row row : rows)
        {
            stringBuilder.append(row.toString() + System.lineSeparator());
        }
        
        return stringBuilder.toString();
    }
    
    public static class Row implements Iterable<String>
    {
        private final List<String> cols = new ArrayList<>();
        
        @Override
        public Iterator<String> iterator()
        {
            return cols.iterator();
        }
        
        public void add(String col)
        {
            cols.add(col);
        }
        
        public String get(int index)
        {
            return cols.get(index);
        }
        
        public static Row create(String... cols)
        {
            Row row = new Row();
            
            for(String col : cols)
            {
                row.add(col);
            }
            
            return row;
        }
        
        @Override
        public boolean equals(Object o)
        {
            if (o == null )
            {
                return false;
            }
            if (!o.getClass().equals(getClass()))
            {
                return false;
            }
          
            Row row = (Row)o;
            
            if(cols.size() != row.cols.size())
            {
                return false;
            }
            
            for(int i = 0; i < cols.size(); i++)
            {
                if(!cols.get(i).equals(row.cols.get(i)))
                {
                    return false;
                }
            }
            
            return true;
        }
        
        @Override
        public String toString()
        {
            StringBuilder stringBuilder = new StringBuilder();
            List<String> row = new ArrayList<>();
            
            for(String col : cols)
            {
                row.add(col.toString());
            }
            
            return stringBuilder.append(String.join(StringCollection.General.SPACE, row)).toString();
        }
    }
}
