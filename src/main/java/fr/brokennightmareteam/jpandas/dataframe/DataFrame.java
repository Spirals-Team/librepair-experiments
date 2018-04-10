package fr.brokennightmareteam.jpandas.dataframe;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.brokennightmareteam.jpandas.utils.Consts;

public class DataFrame {
	
	List<List<Comparable<?>>> data;
	Map<String, Integer> indexColumn;
	List<String> types;
	List<String> names;
	int maxColumnSize;

	public DataFrame(){
		data = new ArrayList<List<Comparable<?>>>();
		indexColumn = new HashMap<String, Integer>();
		types = new ArrayList<String>();
		names = new ArrayList<String>();
		maxColumnSize = 0;
	}
	
	public DataFrame(List<String> columnNames, List<List<Comparable<?>>> columns){
		data = new ArrayList<List<Comparable<?>>>();
		indexColumn = new HashMap<String, Integer>();
		types = new ArrayList<String>();
		names = columnNames;
		maxColumnSize = 0;
		for(int i=0; i<columns.size(); i++){
			if(names.size() <= i)
				throw new IllegalArgumentException("Nombre de colonnes incorrect");
			indexColumn.put(names.get(i), i);
			data.add(columns.get(i));
			if(data.get(i).size() > maxColumnSize)
				maxColumnSize = data.get(i).size();
			String type = columns.get(i).get(0).getClass().getName();
			types.add(type);
			data.get(i).forEach(c -> {
				if(!c.getClass().getName().equals(type))
					throw new IllegalArgumentException("Types incorrect");
			});
		}
		if (data.size() != names.size())
			throw new IllegalArgumentException("Nombre de colonnes incorrect");
	}

	public DataFrame(List<String> columnNames, Comparable<?>[] ... columns){
		this(columnNames, Arrays.stream(columns).map(Arrays::asList).collect(Collectors.toList()));
	}
	
	public DataFrame(String csvFile) throws IOException, IllegalArgumentException{
		this(new File(csvFile));
	}
	
	public DataFrame(File file) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(file));;
		data = new ArrayList<List<Comparable<?>>>();
		indexColumn = new HashMap<String, Integer>();
		types = new ArrayList<String>();
		names = new ArrayList<String>();
		maxColumnSize = 0;
		String line = br.readLine();
		String[] tokens;
		int nbColumns = 0;
		if(line != null && line.startsWith("\"") && line.endsWith("\"")){
			tokens = line.substring(1, line.length()-1).split("\",\"");
			nbColumns = tokens.length;
			for(int i=0; i<nbColumns; i++){
				names.add(tokens[i]);
				indexColumn.put(tokens[i], i);
			}
		} else {
			br.close();
			throw new IllegalArgumentException("Format de fichier incorrect");
		}
		line = br.readLine();
		if(line != null && line.startsWith("\"") && line.endsWith("\"") && (tokens = line.substring(1, line.length()-1).split("\",\"")).length == nbColumns){
			for(int i=0; i<nbColumns; i++){
				String token = tokens[i];
				Comparable<?> item = token;
				try{
					item = Integer.parseInt(token);
				} catch (NumberFormatException e){
					try{
						item = Double.parseDouble(token);
					} catch (NumberFormatException e2){
						if(token.equalsIgnoreCase("true") || token.equalsIgnoreCase("false"))
							item = Boolean.parseBoolean(token);
					}
				}
				types.add(item.getClass().getName());
				List<Comparable<?>> column = new ArrayList<Comparable<?>>();
				column.add(item);
				data.add(column);
			}
			maxColumnSize++;
		} else {
			br.close();
			throw new IllegalArgumentException("Format de fichier incorrect");
		}

		while ((line = br.readLine()) != null) {
			if(line.startsWith("\"") && line.endsWith("\"") && (tokens = line.substring(1, line.length()-1).split("\",\"")).length == nbColumns){
				for(int i=0; i<nbColumns; i++){
					if(!tokens[i].isEmpty()){
						switch (types.get(i)){
						case Consts.IntegerName:
						case Consts.intName:
							try{
								data.get(i).add(Integer.parseInt(tokens[i]));
							} catch (NumberFormatException e){
								br.close();
								throw new IllegalArgumentException("Types incorrect expected Integer");
							}
							break;
						case Consts.doubleName:
						case Consts.DoubleName:
							try{
								data.get(i).add(Double.parseDouble(tokens[i]));
							} catch (NumberFormatException e){
								br.close();
								throw new IllegalArgumentException("Types incorrect expected Double");
							}
							break;
						case Consts.BooleanName:
						case Consts.booleanName:
							String tokenTmp = tokens[i];
							if(tokenTmp.equalsIgnoreCase("true") || tokenTmp.equalsIgnoreCase("false")){
								data.get(i).add(Boolean.parseBoolean(tokenTmp));
							} else {
								br.close();
								throw new IllegalArgumentException("Types incorrect expected Boolean");
							}
							break;
						case Consts.StringName:
							data.get(i).add(tokens[i]);
						}
					}
				}
			} else {
				br.close();
				throw new IllegalArgumentException("Format de fichier incorrect");
			}
			maxColumnSize++;
        }
		br.close();
	}

	public String subprint(int indexBegin, int indexEnd, List<String> columnNames){
		List<Integer> maxSizeByColumn = new ArrayList<Integer>();
		for(int j=0; j<columnNames.size(); j++){
			String columnName = columnNames.get(j);
			List<Comparable<?>> column = data.get(indexColumn.get(columnName));
			if(column != null){
				Integer tmpMaxSize = columnName.length();
				for(int i=indexBegin; i<indexEnd; i++){
					Comparable<?> item = column.get(i);
					if (item.toString().length() > tmpMaxSize)
						tmpMaxSize = item.toString().length();
				}
				maxSizeByColumn.add(tmpMaxSize);
			}
		}
		
		String toPrint = "    ";
		for(int i=0; i<Integer.valueOf(indexEnd).toString().length(); i++){
			toPrint += " ";
		}
		toPrint += "||";

		for(int i=0; i<columnNames.size(); i++){
			String columnName = columnNames.get(i);
			if (indexColumn.get(columnName) != null){
				toPrint += " " + columnName;
				for (int j=0; j<maxSizeByColumn.get(i) - columnName.length(); j++)
					toPrint += " ";
				toPrint += " ||";
			}
		}
		
		toPrint += "\n";
		for(Integer i=indexBegin; i<indexEnd; i++){
			toPrint += "|| " + i;
			for(int k=0; k<(Integer.valueOf(indexEnd)).toString().length() - i.toString().length(); k++){
				toPrint += " ";
			}
			toPrint += " ||";
			for(int j=0; j<columnNames.size(); j++){
				String columnName = columnNames.get(j);
				if(indexColumn.get(columnName) != null){
					List<Comparable<?>> column = data.get(indexColumn.get(columnName));
					String item = i<column.size() ? column.get(i).toString() : "";
					toPrint += " " + item;
					for(int k=0; k<(maxSizeByColumn.get(j) - item.length()); k++){
						toPrint += " ";
					}
					toPrint += " ||";
				}
			}
			toPrint += "\n";
		}
		toPrint += "\n";
		return toPrint;
	}
	
	public String print(){
		return subprint(0, maxColumnSize, names);
	}
	
	public String printFirstLine(int nbLine){
		return subprint(0, nbLine, names);
	}
	
	public String printLastLine(int nbLine){
		return subprint(maxColumnSize - nbLine, maxColumnSize, names);
	}
	
	public String printColumns(List<String> columns){
		return subprint(0, maxColumnSize, columns);
	}
	
	public String printFirstLineOnColumnList(int nbLine, List<String> columns){
		return subprint(0, nbLine, columns);
	}
	
	public String printLastLineOnColumnList(int nbLine, List<String> columns){
		return subprint(maxColumnSize - nbLine, maxColumnSize, columns);
	}
	
	public boolean isEmpty() {
		return data == null || data.isEmpty();
	}
	
	public DataFrame subDataFrame(int indexBegin, int indexEnd, String[] columnNames){
		List<List<Comparable<?>>> subData = new ArrayList<List<Comparable<?>>>();
		for(String columnName : columnNames){
			if(names.contains(columnName))
				subData.add(new ArrayList<Comparable<?>>(data.get(indexColumn.get(columnName))));
		}
		for(List<Comparable<?>> column : subData){
			column.removeAll(column.subList(0, indexBegin < column.size() ? indexBegin : column.size()));
			column.removeAll(column.subList(indexEnd-1 < column.size() ? indexEnd-1 : column.size(), column.size()));
		}
		return new DataFrame(Arrays.asList(columnNames), subData);
	}
	
	public DataFrame subDataFrameFromLine(int indexBegin, int indexEnd){
		return subDataFrame(indexBegin, indexEnd, (String[])names.toArray());
	}
	
	public DataFrame subDataFrameFromColumn(String[] columnNames){
		return subDataFrame(0, maxColumnSize+1, columnNames);
	}
	
	public double avgCol(String columnName){
		List<Comparable<?>> column = data.get(indexColumn.get(columnName));
		String type = types.get(indexColumn.get(columnName));
		double avg = 0;
		for(Comparable<?> item : column){
			switch (type){
			case Consts.booleanName :
			case Consts.BooleanName :
				avg += ((Boolean)item).compareTo(Boolean.FALSE);
				break;
			case Consts.DoubleName :
			case Consts.doubleName :
				avg += (Double)item;
				break;
			case Consts.IntegerName :
			case Consts.intName :
				avg += (Integer)item;
				break;
			case Consts.StringName :
				avg += ((String)item).length();
				break;
			}
		}
		return avg/column.size();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Comparable minCol(String columnName){
		List<Comparable<?>> column = data.get(indexColumn.get(columnName));
		Comparable minItem = column.get(0);
		for(Comparable item : column){
			if(item.compareTo(minItem)<0){
				minItem = item;
			}
		}
		return minItem;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Comparable maxCol(String columnName){
		List<Comparable<?>> column = data.get(indexColumn.get(columnName));
		Comparable maxItem = column.get(0);
		for(Comparable item : column){
			if(item.compareTo(maxItem)>0){
				maxItem = item;
			}
		}
		return maxItem;
	}
	
}
