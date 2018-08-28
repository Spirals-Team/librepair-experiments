package java_programs;


public class DEPTH_FIRST_SEARCH {








	public boolean depth_first_search(java_programs.Node startnode, java_programs.Node goalnode) {
		java.util.Set<java_programs.Node> nodesvisited = new java.util.HashSet<>();
		class Search {
			boolean search(java_programs.Node node) {
				if (nodesvisited.contains(node)) {
					return false;
				}else 					if (node == goalnode) {
						return true;
					}else {
						for (java_programs.Node successornodes : node.getSuccessors()) {
							if (search(successornodes)) { 								return true;}
						}
					}
				return false;
			}
		}

		Search s = new Search();
		return s.search(startnode);
	}}